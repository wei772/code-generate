package cn.garden.generate;

import cn.garden.generate.entity.Entity;
import cn.garden.generate.util.Tags;
import cn.garden.generate.util.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 实体代码生成模板属性
 *
 * @author liwei
 */
public enum EntityTemplateProperty {

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

    EntityTemplateProperty(String name) {
        this.name = name;
    }

    public static EntityTemplateProperty of(String name) {
        for (EntityTemplateProperty value : values()) {
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
