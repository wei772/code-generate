package cn.garden.generate.template;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TemplateEngineFactory {

    @Test
    public void getThymeleafTemplateEngine() {
        assertThrows(RuntimeException.class, () ->
                cn.garden.generate.template.factory.TemplateEngineFactory.create("thymeleaf"));
    }
}
