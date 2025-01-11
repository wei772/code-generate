package cn.garden.generate.template;

import cn.garden.generate.entity.LanguageType;
import cn.garden.generate.CodeGenerateWorkType;

import java.util.ArrayList;
import java.util.List;

/**
 * 模板仓库配置
 *
 * @author liwei
 */
public class TemplateRepositoryConfig {

    private String engineName = TemplateEngineType.VELOCITY.getName();

    private String generateType = CodeGenerateWorkType.ENTITY.getName();

    private String targetLanguage = LanguageType.JAVA.getName();

    private List<String> templateFileNames = new ArrayList<>();

    public String getEngineName() {
        return engineName;
    }

    public void setEngineName(String engineName) {
        this.engineName = engineName;
    }

    public String getGenerateType() {
        return generateType;
    }

    public void setGenerateType(String generateType) {
        this.generateType = generateType;
    }

    public String getTargetLanguage() {
        return targetLanguage;
    }

    public void setTargetLanguage(String targetLanguage) {
        this.targetLanguage = targetLanguage;
    }

    public List<String> getTemplateFileNames() {
        return templateFileNames;
    }

    public void setTemplateFileNames(List<String> templateFileNames) {
        this.templateFileNames = templateFileNames;
    }
}
