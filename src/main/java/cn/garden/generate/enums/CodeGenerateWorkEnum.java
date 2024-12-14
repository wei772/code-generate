package cn.garden.generate.enums;

import cn.garden.util.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * 代码生成工作类枚举
 *
 * @author liwei
 */
public enum CodeGenerateWorkEnum {

    ENTITY("entity"),

    ;

    private final String name;

    CodeGenerateWorkEnum(String name) {
        this.name = name;
    }

    public static CodeGenerateWorkEnum getEnum(String name) {
        for (CodeGenerateWorkEnum value : values()) {
            if (StringUtils.equals(name, value.getName())) {
                return value;
            }
        }
        throw ExceptionUtil.createDefaultException("找不到CodeGenerateWork枚举" + name);
    }

    public String getName() {
        return name;
    }
}
