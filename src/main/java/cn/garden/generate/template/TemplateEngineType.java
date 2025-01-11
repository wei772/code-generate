package cn.garden.generate.template;

import cn.garden.generate.util.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * 模板引擎类型枚举
 *
 * @author liwei
 */
public enum TemplateEngineType {

    VELOCITY("velocity", ".vtl"),
    ;

    private final String name;

    private final String fileExtension;

    TemplateEngineType(String name, String fileExtension) {
        this.name = name;
        this.fileExtension = fileExtension;
    }

    public static TemplateEngineType of(String name) {
        for (TemplateEngineType value : values()) {
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
