package cn.garden.entity.enums;

import cn.garden.util.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * 语言枚举
 */
public enum LanguageEnum {

    JAVA("Java", ".java"),

    ;

    private final String name;

    private final String fileExtension;

    LanguageEnum(String name, String fileExtension) {
        this.name = name;
        this.fileExtension = fileExtension;
    }

    public static LanguageEnum getEnum(String name) {
        for (LanguageEnum value : values()) {
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
