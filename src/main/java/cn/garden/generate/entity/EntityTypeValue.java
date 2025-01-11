package cn.garden.generate.entity;

import cn.garden.generate.util.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;


/**
 * 实体类型枚举值
 */
public enum EntityTypeValue {

    STRING("String"),

    INTEGER("Integer"),

    DOUBLE("Double"),

    LONG("Long"),

    LOCAL_DATE_TIME("LocalDateTime"),

    LOCAL_DATE("LocalDate"),

    LOCAL_TIME("LocalTime"),

    BIG_DECIMAL("BigDecimal"),

    BOOLEAN("Boolean"),
    ;

    private final String name;

    EntityTypeValue(String name) {

        this.name = name;
    }

    public static EntityTypeValue of(String name) {
        for (EntityTypeValue value : values()) {
            if (StringUtils.equals(name, value.getName())) {
                return value;
            }
        }

        throw ExceptionUtil.createDefaultException("不支持类型" + name);
    }

    public String getName() {
        return name;
    }
}
