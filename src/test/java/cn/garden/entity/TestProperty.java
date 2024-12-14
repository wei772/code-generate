package cn.garden.entity;

import cn.garden.entity.enums.EntityTypeValueEnum;
import cn.garden.entity.enums.LanguageEnum;
import org.junit.jupiter.api.Test;

import java.sql.JDBCType;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 属性测试类
 *
 * @author liwei
 */
public class TestProperty {

    @Test
    public void createProperty() {
        Property idProperty = new Property();

        assertNotEquals(idProperty, null);
    }

    @Test
    public void getPropertyName() {
        Property codeProperty = new Property();
        codeProperty.setSourceName("code");

        assertEquals(codeProperty.getCamelName(), "code");
        assertEquals(codeProperty.getPascalName(), "Code");
    }

    @Test
    public void getPropertyNameWithUnderline() {
        Property mobilePhoneProperty = new Property();
        mobilePhoneProperty.setSourceName("mobile_phone");

        assertEquals(mobilePhoneProperty.getCamelName(), "mobilePhone");
        assertEquals(mobilePhoneProperty.getPascalName(), "MobilePhone");
    }

    @Test
    public void getNullPropertyName() {
        Property idProperty = new Property();
        idProperty.setSourceName(null);

        assertNull(idProperty.getCamelName());
        assertNull(idProperty.getPascalName());
    }

    @Test
    public void getPropertyJDBCType() {
        Property idProperty = new Property();
        idProperty.setSourceName("id");
        idProperty.setType(EntityTypeValueEnum.LONG.getName());

        assertEquals(JDBCType.BIGINT, idProperty.getJdbcType());
    }

    @Test
    public void setJdbcType() {
        Property idProperty = new Property();
        idProperty.setSourceName("id");
        idProperty.setJdbcType(JDBCType.BIGINT);

        assertEquals(EntityTypeValueEnum.LONG.getName(), idProperty.getType());
    }

    @Test
    public void setPrimaryKey() {
        Property idProperty = new Property();
        idProperty.setSourceName("id");
        idProperty.setJdbcType(JDBCType.BIGINT);
        idProperty.setPrimaryKey(true);

        assertEquals(true, idProperty.getPrimaryKey());
    }

    @Test
    public void setTargetLanguage() {
        Property idProperty = new Property();
        idProperty.setSourceName("id");
        idProperty.setTargetLanguage(LanguageEnum.JAVA);

        assertEquals(LanguageEnum.JAVA, idProperty.getTargetLanguage());
    }

    @Test
    public void getLocalDateTimeTypeImportString() {
        Property idProperty = new Property();
        idProperty.setTargetLanguage(LanguageEnum.JAVA);
        idProperty.setSourceName("time");
        idProperty.setType(EntityTypeValueEnum.LOCAL_DATE_TIME.getName());

        assertEquals("import java.time.LocalDateTime;", idProperty.getImportString());
    }

    @Test
    public void getLocalDateTimeTypeImportStringSequentialDependence() {
        Property idProperty = new Property();
        idProperty.setSourceName("time");
        idProperty.setType(EntityTypeValueEnum.LOCAL_DATE_TIME.getName());
        idProperty.setTargetLanguage(LanguageEnum.JAVA);

        assertEquals("import java.time.LocalDateTime;", idProperty.getImportString());
    }

}
