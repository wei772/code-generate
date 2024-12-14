package cn.garden.template;

import org.apache.commons.collections4.MapUtils;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 模板引擎抽象基类
 *
 * @author liwei
 */
public abstract class TemplateEngine {

    private final Map<String, Object> properties = new HashMap<>();
    private final Map<String, Template> templateMap = new HashMap<>();

    public Object getProperty(String key) {
        if (MapUtils.isEmpty(properties)) {
            return null;
        }
        return properties.get(key);
    }

    /**
     * 初始化模板引擎
     */
    public void init() {
        init(new HashMap<>());
    }

    /**
     * 初始化模板引擎
     */
    public void init(Map<String, Object> properties) {
        if (MapUtils.isNotEmpty(properties)) {
            this.properties.putAll(properties);
        }
        initCore(properties);
    }

    /**
     * 渲染模板
     */
    public void render(String name, Map<String, Object> templateProperties, StringWriter stringWriter) {
        assert stringWriter != null : "stringWriter 不能为空";
        if (MapUtils.isEmpty(templateProperties)) {
            templateProperties = new HashMap<>();
        }
        Template template = templateMap.get(name);
        if (Objects.isNull(template)) {
            template = createNewTemplate(name);
            templateMap.put(name, template);
        }

        template.render(templateProperties, stringWriter);
    }

    protected abstract void initCore(Map<String, Object> properties);

    protected abstract Template createNewTemplate(String name);
}
