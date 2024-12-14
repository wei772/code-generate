package cn.garden.template;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author liwei
 */
public class TestTemplateRepository {


    @Test
    public void notConfigTemplateFilesRepository() {
        TemplateRepository repo = new TemplateRepository("testRepository");

        assertEquals(1, CollectionUtils.size(repo.getTemplateFiles()));
        assertEquals("templateRepository/testRepository/entity.vtl",
                repo.getTemplateFiles().get(0));
    }

    @Test
    public void createEntityDemoRepository() {
        TemplateRepository repo = new TemplateRepository("entityDemo");

        assertEquals("entityDemo", repo.getCode());
        assertEquals("velocity", repo.getConfig().getEngineName());
        assertEquals("entity", repo.getConfig().getGenerateType());
        assertEquals(1, CollectionUtils.size(repo.getTemplateFiles()));
        assertEquals("templateRepository/entityDemo/entity.vtl",
                repo.getTemplateFiles().get(0));
    }

    @Test
    public void readNotExistTemplateRepository() {
        assertThrows(RuntimeException.class,
                () -> new TemplateRepository("entityTestNotExist"));
    }

    @Test
    public void readNullTemplateRepository() {
        assertThrows(RuntimeException.class,
                () -> new TemplateRepository(null));
    }
}
