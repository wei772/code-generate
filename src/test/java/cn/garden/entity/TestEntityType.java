package cn.garden.entity;

import cn.garden.entity.enums.LanguageEnum;
import org.junit.jupiter.api.Test;

import java.sql.JDBCType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestEntityType {

    @Test
    public void createType() {
        EntityType entityType = new EntityType("String");

        assertEquals("String", entityType.getValue());
    }

    @Test
    public void setTargetLanguage() {
        EntityType entityType = new EntityType("String");
        entityType.setTargetLanguage(LanguageEnum.JAVA);

        assertEquals(LanguageEnum.JAVA, entityType.getTargetLanguage());
    }

    @Test
    public void createNotSupportType() {
        assertThrows(RuntimeException.class, () -> new EntityType("StringTest"));
    }

    @Test
    public void mapTypeToJdbcType() {
        EntityType entityType = new EntityType("String");

        assertEquals(JDBCType.VARCHAR, entityType.getJdbcType());
    }

    @Test
    public void createTypeFromJdbcType() {
        EntityType entityType = new EntityType(JDBCType.VARCHAR);

        assertEquals("String", entityType.getValue());
    }


    @Test
    public void getStringTypeImportString() {
        EntityType string = new EntityType("String");
        string.setTargetLanguage(LanguageEnum.JAVA);

        assertEquals("", string.getImportString());
    }


    @Test
    public void createLocalDateTimeTypeFromJdbcType() {
        EntityType entityType = new EntityType(JDBCType.TIMESTAMP);

        assertEquals("LocalDateTime", entityType.getValue());
    }

    @Test
    public void getLocalDateTimeTypeImportString() {
        EntityType string = new EntityType("LocalDateTime");
        string.setTargetLanguage(LanguageEnum.JAVA);

        assertEquals("import java.time.LocalDateTime;", string.getImportString());
    }


}
