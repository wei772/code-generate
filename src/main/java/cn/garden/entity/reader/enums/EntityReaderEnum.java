package cn.garden.entity.reader.enums;

import cn.garden.util.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * EntityReader类型枚举
 *
 * @author liwei
 */
public enum EntityReaderEnum {

    JSON_FILE("jsonFile"),

    JDBC("jdbc"),
    ;

    private final String name;

    EntityReaderEnum(String name) {
        this.name = name;
    }

    public static EntityReaderEnum getEnum(String name) {
        for (EntityReaderEnum value : values()) {
            if (StringUtils.equals(name, value.getName())) {
                return value;
            }
        }

        throw ExceptionUtil.createDefaultException("找不到EntityReader类型枚举" + name);
    }

    public String getName() {
        return name;
    }
}
