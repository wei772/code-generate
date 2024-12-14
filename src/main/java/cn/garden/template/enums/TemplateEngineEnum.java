package cn.garden.template.enums;

import cn.garden.util.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * 模板引擎类型枚举
 *
 * @author liwei
 */
public enum TemplateEngineEnum {

    VELOCITY("velocity", ".vtl"),
    ;

    private final String name;

    private final String fileExtension;

    TemplateEngineEnum(String name, String fileExtension) {
        this.name = name;
        this.fileExtension = fileExtension;
    }

    public static TemplateEngineEnum getEnum(String name) {
        for (TemplateEngineEnum value : values()) {
            if (StringUtils.equals(name, value.getName())) {
                return value;
            }
        }
        throw ExceptionUtil.createDefaultException("模板引擎类型枚举" + name);
    }

    public String getName() {
        return name;
    }

    public String getFileExtension() {
        return fileExtension;
    }
}
