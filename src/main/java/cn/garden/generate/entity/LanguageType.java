package cn.garden.generate.entity;

import cn.garden.generate.util.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * 语言枚举
 */
public enum LanguageType {

    JAVA("Java", ".java"),

    ;

    private final String name;

    private final String fileExtension;

    LanguageType(String name, String fileExtension) {
        this.name = name;
        this.fileExtension = fileExtension;
    }

    public static LanguageType of(String name) {
        for (LanguageType value : values()) {
            if (StringUtils.equals(name, value.getName())) {
                return value;
            }
        }

        throw ExceptionUtil.createDefaultException("不支持语言" + name);
    }

    public String getName() {
        return name;
    }

    public String getFileExtension() {
        return fileExtension;
    }
}
