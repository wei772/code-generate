package cn.garden.template;

import cn.garden.template.factory.TemplateEngineFactory;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author liwei
 */
public class TestTemplate {

    private TemplateEngine getTemplateEngine() {
        return TemplateEngineFactory.create("velocity");
    }

    @Test
    public void initTemplateEngine() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("id", "test");
        TemplateEngine templateEngine = getTemplateEngine();
        templateEngine.init(properties);

        assertEquals(templateEngine.getProperty("id"), "test");
    }

    @Test
    public void renderTemplate() {
        TemplateEngine templateEngine = getTemplateEngine();
        Map<String, Object> properties = new HashMap<>();
        templateEngine.init(properties);

        String name = "template/velocity/helloworld.vtl";
        Map<String, Object> templateProperties = new HashMap<>();
        templateProperties.put("name", "World");
        StringWriter stringWriter = new StringWriter();
        templateEngine.render(name, templateProperties, stringWriter);

        assertEquals("Hello World", stringWriter.toString());
    }


}
