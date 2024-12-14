package cn.garden.template.implementation;

import cn.garden.template.Template;
import org.apache.velocity.VelocityContext;

import java.io.StringWriter;
import java.util.Map;

/**
 * Velocity模板类封装
 *
 * @author liwei
 */
public class VelocityTemplate extends Template {

    private final org.apache.velocity.Template template;

    public VelocityTemplate(org.apache.velocity.Template template) {
        this.template = template;
    }

    @Override
    public void render(Map<String, Object> templateProperties, StringWriter stringWriter) {
        VelocityContext velocityContext = new VelocityContext();
        for (Map.Entry<String, Object> stringObjectEntry : templateProperties.entrySet()) {
            velocityContext.put(stringObjectEntry.getKey(), stringObjectEntry.getValue());
        }
        template.merge(velocityContext, stringWriter);
    }
}
