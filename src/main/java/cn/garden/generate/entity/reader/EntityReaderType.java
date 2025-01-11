package cn.garden.generate.entity.reader;

import cn.garden.generate.util.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * EntityReader类型枚举
 *
 * @author liwei
 */
public enum EntityReaderType {

    JSON_FILE("jsonFile"),

    JDBC("jdbc"),
    ;

    private final String name;

    EntityReaderType(String name) {
        this.name = name;
    }

    public static EntityReaderType of(String name) {
        for (EntityReaderType value : values()) {
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
