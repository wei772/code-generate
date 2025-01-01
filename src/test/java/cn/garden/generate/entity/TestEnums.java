package cn.garden.generate.entity;

import cn.garden.generate.entity.enums.EntityTypeValueEnum;
import cn.garden.generate.entity.enums.LanguageEnum;
import cn.garden.generate.entity.reader.enums.EntityReaderEnum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestEnums {

    @Test
    public void createErrorEntityTypeValueEnum() {
        assertThrows(RuntimeException.class, () -> EntityTypeValueEnum.getEnum("test"));
    }

    @Test
    public void createErrorLanguageEnum() {
        assertThrows(RuntimeException.class, () -> LanguageEnum.getEnum("test"));
    }

    @Test
    public void createErrorEntityReaderEnum() {
        assertThrows(RuntimeException.class, () -> EntityReaderEnum.getEnum("test"));
    }
}
