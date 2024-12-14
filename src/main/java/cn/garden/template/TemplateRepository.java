package cn.garden.template;

import cn.garden.template.enums.TemplateEngineEnum;
import cn.garden.util.ExceptionUtil;
import cn.garden.util.JsonUtil;
import cn.garden.util.ResourceUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 模板仓库
 *
 * @author liwei
 */
public class TemplateRepository {

    public final static String BASE_FOLDER = "templateRepository";
    public final static String CONFIG_JSON = "config.json";
    public final static String RESOURCE_SEPARATOR = "/";

    private final String code;
    private TemplateRepositoryConfig config;
    private List<String> templateFiles = new ArrayList<>();

    public TemplateRepository(String code) {
        if (StringUtils.isEmpty(code)) {
            throw ExceptionUtil.createDefaultException("模板仓库Code不能为空");
        }
        this.code = code;

        init();
    }

    void init() {
        String repoResourceName = BASE_FOLDER + RESOURCE_SEPARATOR + code;
        loadConfig(repoResourceName);
        loadTemplateFiles(repoResourceName);
    }

    private void loadTemplateFiles(String repoResourceName) {
        if (CollectionUtils.isNotEmpty(config.getTemplateFileNames())) {
            templateFiles = config.getTemplateFileNames()
                    .stream()
                    .map(m -> repoResourceName + RESOURCE_SEPARATOR + m)
                    .toList();
        } else {

            try {
                List<String> files = ResourceUtil.readAllResourceFileNames(repoResourceName);
                files = files
                        .stream()
                        .map(m -> m.substring(m.indexOf(BASE_FOLDER))) //只保留包内路径
                        .map(m -> StringUtils.replace(m, File.separator, RESOURCE_SEPARATOR))
                        .toList();
                TemplateEngineEnum templateEngineEnum = TemplateEngineEnum.getEnum(config.getEngineName());
                templateFiles = files.
                        stream().
                        filter(m -> m.endsWith(templateEngineEnum.getFileExtension())).
                        toList();
            } catch (IllegalArgumentException e) {
                throw ExceptionUtil.createDefaultException("打包成jar后,请使用config文件的templateFileNames进行配置", e);
            }

        }

        if (CollectionUtils.isEmpty(templateFiles)) {
            throw ExceptionUtil.createDefaultException("模板文件不能为空" + code);
        }
    }


    public TemplateRepositoryConfig getConfig() {
        return config;
    }

    public List<String> getTemplateFiles() {
        return templateFiles;
    }

    public String getCode() {
        return code;
    }


    private void loadConfig(String repoResourceName) {
        String configFile = repoResourceName + RESOURCE_SEPARATOR + CONFIG_JSON;
        String configString = ResourceUtil.getResourceToString(configFile);
        if (StringUtils.isNotEmpty(configString)) {
            config = JsonUtil.toObject(configString, TemplateRepositoryConfig.class);
        } else {
            throw ExceptionUtil.createDefaultException("模板仓库 " + code + "config.json必须配置");
        }
    }

}
