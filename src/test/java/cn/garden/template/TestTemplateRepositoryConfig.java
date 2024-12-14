package cn.garden.template;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author liwei
 */
public class TestTemplateRepositoryConfig {

    @Test
    public void createTemplateRepositoryConfig() {
        TemplateRepositoryConfig config = new TemplateRepositoryConfig();
        config.setTemplateFileNames(List.of("entity.vtl"));
        config.setEngineName("v");
        config.setGenerateType("g");
        config.setTargetLanguage("t");

        assertEquals("v", config.getEngineName());
        assertEquals("g", config.getGenerateType());
        assertEquals("t", config.getTargetLanguage());
        assertTrue(CollectionUtils.isEqualCollection(List.of("entity.vtl")
                , config.getTemplateFileNames()));
    }
}
