package cn.garden.template;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TemplateEngineFactory {

    @Test
    public void getThymeleafTemplateEngine() {
        assertThrows(RuntimeException.class, () ->
                cn.garden.template.factory.TemplateEngineFactory.create("thymeleaf"));
    }
}
