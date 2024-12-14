package cn.garden.entity;

import cn.garden.entity.enums.EntityTypeValueEnum;
import cn.garden.entity.enums.LanguageEnum;
import cn.garden.entity.reader.enums.EntityReaderEnum;
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
