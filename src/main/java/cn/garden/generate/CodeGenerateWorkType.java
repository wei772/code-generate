package cn.garden.generate;

import cn.garden.generate.util.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * 代码生成工作类枚举
 *
 * @author liwei
 */
public enum CodeGenerateWorkType {

    ENTITY("entity"),

    ;

    private final String name;

    CodeGenerateWorkType(String name) {
        this.name = name;
    }

    public static CodeGenerateWorkType of(String name) {
        for (CodeGenerateWorkType value : values()) {
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
