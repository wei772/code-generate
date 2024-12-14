package cn.garden.template;

import cn.garden.entity.enums.LanguageEnum;
import cn.garden.generate.enums.CodeGenerateWorkEnum;
import cn.garden.template.enums.TemplateEngineEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * 模板仓库配置
 *
 * @author liwei
 */
public class TemplateRepositoryConfig {

    private String engineName = TemplateEngineEnum.VELOCITY.getName();

    private String generateType = CodeGenerateWorkEnum.ENTITY.getName();

    private String targetLanguage = LanguageEnum.JAVA.getName();

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
