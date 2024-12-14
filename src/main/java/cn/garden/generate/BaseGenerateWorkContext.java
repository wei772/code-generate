package cn.garden.generate;

import cn.garden.entity.enums.LanguageEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 代码生成Work上下文变量基类
 *
 * @author liwei
 */
public class BaseGenerateWorkContext {

    private String basePackage = StringUtils.EMPTY;

    private String output = StringUtils.EMPTY;

    private List<String> templateFiles = new ArrayList<>();

    private String engineName = StringUtils.EMPTY;

    private List<String> targetNames = new ArrayList<>();

    private LanguageEnum targetLanguage;

    private String author;

    private List<String> tags;

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getAuthor() {
        if (StringUtils.isEmpty(author)) {
            return StringUtils.EMPTY;
        }
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<String> getTargetNames() {
        if (CollectionUtils.isEmpty(targetNames)) {
            return new ArrayList<>();
        }
        return targetNames;
    }

    public void setTargetNames(List<String> targetNames) {
        this.targetNames = targetNames;
    }

    public String getOutput() {
        if (StringUtils.isEmpty(output)) {
            return StringUtils.EMPTY;
        }
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getEngineName() {
        return engineName;
    }

    public void setEngineName(String engineName) {
        this.engineName = engineName;
    }

    public List<String> getTemplateFiles() {
        return templateFiles;
    }

    public void setTemplateFiles(List<String> templateFiles) {
        this.templateFiles = templateFiles;
    }

    public String getBasePackage() {
        if (StringUtils.isEmpty(basePackage)) {
            return StringUtils.EMPTY;
        }
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public LanguageEnum getTargetLanguage() {
        return targetLanguage;
    }

    public void setTargetLanguage(LanguageEnum targetLanguage) {
        this.targetLanguage = targetLanguage;
    }
}
