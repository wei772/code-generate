package cn.garden.entity;

import cn.garden.entity.enums.EntityTypeValueEnum;
import cn.garden.entity.enums.LanguageEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 实体测试类
 *
 * @author liwei
 */
public class TestEntity {

    @Test
    public void createEntity() {
        Entity userGroupEntity = new Entity();

        assertNotEquals(userGroupEntity, null);
    }


    @Test
    public void getEntityNameWithUnderline() {
        Entity userGroupEntity = new Entity();
        userGroupEntity.setSourceName("user_group");

        assertEquals(userGroupEntity.getCamelName(), "userGroup");
        assertEquals(userGroupEntity.getPascalName(), "UserGroup");
    }

    @Test
    public void getEntityName() {
        Entity userGroupEntity = new Entity();
        userGroupEntity.setSourceName("user");

        assertEquals(userGroupEntity.getCamelName(), "user");
        assertEquals(userGroupEntity.getPascalName(), "User");
    }

    @Test
    public void getNullEntityName() {
        Entity userGroupEntity = new Entity();
        userGroupEntity.setSourceName(null);

        assertNull(userGroupEntity.getCamelName());
        assertNull(userGroupEntity.getPascalName());
    }


    @Test
    public void addProperty() {
        Entity userGroupEntity = new Entity();
        userGroupEntity.setSourceName("user_group");

        Property codeProperty = new Property();
        String code = "code";
        codeProperty.setSourceName(code);

        userGroupEntity.addProperty(codeProperty);
        assertNotNull(userGroupEntity.getProperty(code));
        assertEquals(userGroupEntity.getProperty(code), codeProperty);
    }


    @Test
    public void hasProperty() {
        Entity userGroupEntity = new Entity();
        userGroupEntity.setSourceName("user_group");

        Property codeProperty = new Property();
        String code = "code";
        codeProperty.setSourceName(code);

        userGroupEntity.addProperty(codeProperty);

        assertTrue(userGroupEntity.hasProperty("code"));

    }

    @Test
    public void addNullProperty() {
        Entity userGroupEntity = new Entity();
        String code = "code";
        userGroupEntity.addProperty(null);

        assertNull(userGroupEntity.getProperty(code));
        assertNull(userGroupEntity.getProperty(code));
    }


    @Test
    public void typeImport() {
        Entity userGroupEntity = new Entity("user_group");

        Property timeProperty = new Property();
        timeProperty.setSourceName("time");
        timeProperty.setType(EntityTypeValueEnum.LOCAL_DATE_TIME.getName());
        userGroupEntity.addProperty(timeProperty);

        Property createTimeProperty = new Property();
        createTimeProperty.setSourceName("create_time");
        createTimeProperty.setType(EntityTypeValueEnum.LOCAL_DATE_TIME.getName());
        userGroupEntity.addProperty(createTimeProperty);
        userGroupEntity.setTargetLanguage(LanguageEnum.JAVA);

        assertEquals(LanguageEnum.JAVA, userGroupEntity.getTargetLanguage());
        assertEquals(1, CollectionUtils.size(userGroupEntity.getImportStrings()));
        assertEquals("import java.time.LocalDateTime;", userGroupEntity.getImportStrings().get(0));
    }

}
