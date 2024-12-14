package cn.garden.entity.enums;

import cn.garden.util.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;


/**
 * 实体类型枚举值
 */
public enum EntityTypeValueEnum {

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

    EntityTypeValueEnum(String name) {

        this.name = name;
    }

    public static EntityTypeValueEnum getEnum(String name) {
        for (EntityTypeValueEnum value : values()) {
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
