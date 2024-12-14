package cn.garden.generate.enums;

import cn.garden.entity.Entity;
import cn.garden.util.Tags;
import cn.garden.util.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 实体代码生成模板属性
 *
 * @author liwei
 */
public enum EntityTemplatePropertyEnum {

    /**
     * @see String #类型
     */
    BASE_PACKAGE("basePackage"),

    /**
     * @see Tags # 标签类
     */
    TAGS("tags"),

    /**
     * @see String #类型 代码作者
     */
    AUTHOR("author"),

    /**
     * @see Entity #类型
     */
    ENTITY("entity"),

    /**
     * @see List<String> #类型
     */
    IMPORT_STRINGS("importStrings"),

    /**
     * @see String #类型，Entity的属性
     */
    DESCRIPTION("description"),

    /**
     * @see String #类型，Entity的属性
     */
    SOURCE_NAME("sourceName"),

    /**
     * @see String #类型，Entity的属性
     */
    PASCAL_NAME("pascalName"),

    /**
     * @see String #类型，Entity的属性
     */
    CAMEL_NAME("camelName"),

    /**
     * @see List <Property> #类型，Entity的属性
     */
    PROPERTIES("properties"),
    ;

    private final String name;

    EntityTemplatePropertyEnum(String name) {
        this.name = name;
    }

    public static EntityTemplatePropertyEnum getEnum(String name) {
        for (EntityTemplatePropertyEnum value : values()) {
            if (StringUtils.equals(name, value.getName())) {
                return value;
            }
        }
        throw ExceptionUtil.createDefaultException("找不到EntityTemplatePropertyEnum枚举" + name);
    }

    public String getName() {
        return name;
    }
}
