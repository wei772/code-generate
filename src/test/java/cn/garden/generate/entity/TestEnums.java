package cn.garden.generate.entity;

import cn.garden.generate.entity.reader.EntityReaderType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestEnums {

    @Test
    public void createErrorEntityTypeValueEnum() {
        assertThrows(RuntimeException.class, () -> EntityTypeValue.of("test"));
    }

    @Test
    public void createErrorLanguageEnum() {
        assertThrows(RuntimeException.class, () -> LanguageType.of("test"));
    }

    @Test
    public void createErrorEntityReaderEnum() {
        assertThrows(RuntimeException.class, () -> EntityReaderType.of("test"));
    }
}
