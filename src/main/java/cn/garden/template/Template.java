package cn.garden.template;

import java.io.StringWriter;
import java.util.Map;

/**
 * 模板抽象基类
 *
 * @author liwei
 */
public abstract class Template {

    public abstract void render(Map<String, Object> templateProperties, StringWriter stringWriter);

}
