package cn.garden.generate;

import cn.garden.template.TemplateRepository;
import cn.garden.util.ExceptionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 代码生成类上下文参数
 *
 * @author liwei
 */
public class CodeGenerateContext {

    private final Map<String, Object> extendMap = new HashMap<>();
    private String templateRepositoryCode;
    private List<String> targetNames;
    private String output;
    private String basePackage;
    private TemplateRepository templateRepository;

    private String author;

    private List<String> tags;

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void put(String key, Object value) {
        extendMap.put(key, value);
    }

    public Object get(String key) {
        Object object = extendMap.get(key);
        if (Objects.isNull(object)) {
            throw ExceptionUtil.createDefaultException("CodeGenerateContext找不到参数" + key);
        }
        return object;
    }

    public String getTemplateRepositoryCode() {
        return templateRepositoryCode;
    }

    public void setTemplateRepositoryCode(String templateRepositoryCode) {
        this.templateRepositoryCode = templateRepositoryCode;
    }

    public TemplateRepository getTemplateRepository() {
        return templateRepository;
    }

    public void setTemplateRepository(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    public Map<String, Object> getExtendMap() {
        return extendMap;
    }

    public List<String> getTargetNames() {
        return targetNames;
    }

    public void setTargetNames(List<String> targetNames) {
        this.targetNames = targetNames;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }


}
