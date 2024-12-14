package cn.garden.template.implementation;

import cn.garden.template.Template;
import cn.garden.template.TemplateEngine;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.util.Map;

/**
 * Velocity模板引擎封装
 *
 * @author liwei
 */
public class VelocityTemplateEngine extends TemplateEngine {
    private VelocityEngine velocityEngine;

    @Override
    protected void initCore(Map<String, Object> properties) {

        properties.put(RuntimeConstants.RESOURCE_LOADERS, "class");
        properties.put("resource.loader.class.class", ClasspathResourceLoader.class.getName());

        velocityEngine = new VelocityEngine();
        for (Map.Entry<String, Object> stringObjectEntry : properties.entrySet()) {
            velocityEngine.setProperty(stringObjectEntry.getKey(), stringObjectEntry.getValue());
        }
        velocityEngine.init();
    }

    @Override
    protected Template createNewTemplate(String name) {
        return new VelocityTemplate(velocityEngine.getTemplate(name));
    }


}
