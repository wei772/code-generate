package cn.garden.generate;

import cn.garden.config.FileConfig;
import cn.garden.entity.Entity;
import cn.garden.entity.enums.LanguageEnum;
import cn.garden.entity.reader.implementation.JsonFileEntityReader;
import cn.garden.generate.implementation.EntityCodeGenerateWork;
import cn.garden.param.implementation.FileParam;
import cn.garden.param.implementation.FolderParam;
import cn.garden.template.TemplateEngine;
import cn.garden.template.enums.TemplateEngineEnum;
import cn.garden.template.factory.TemplateEngineFactory;
import cn.garden.util.FileUtil;
import cn.garden.util.ResourceUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author liwei
 */
public class TestEntityCodeGenerateWork{

    @Test
    public void useTemplateEngine() {
        TemplateEngine templateEngine = TemplateEngineFactory.create(TemplateEngineEnum.VELOCITY.getName());
        templateEngine.init();

        Map<String, Object> templateProperties = new HashMap<>();
        List<Entity> entities = readJsonUserDefinition(
                getUserDefinition(), new ArrayList<>());

        assertEquals(2, CollectionUtils.size(entities));
        String rootFolder = getGenerateResultFolder();
        String templateFile = "entityGenerate/entity.vtl";
        String basePackage = "cn.garden";


        for (Entity entity : entities) {
            templateProperties.put("basePackage", basePackage);
            templateProperties.put("cn/garden/entity", entity);
            templateProperties.put("description", entity.getDescription());
            templateProperties.put("sourceName", entity.getSourceName());
            templateProperties.put("pascalName", entity.getPascalName());
            templateProperties.put("camelName", entity.getCamelName());
            templateProperties.put("properties", entity.getProperties());

            StringWriter stringWriter = new StringWriter();
            templateEngine.render(templateFile, templateProperties, stringWriter);
            String result = stringWriter.toString();
            assertTrue(StringUtils.isNotEmpty(result));

            FolderParam folderParam = new FolderParam(result);
            FileParam fileParam = new FileParam(folderParam);

            assertTrue(StringUtils.isNotEmpty(fileParam.getNewContext()));

            Path path = Paths.get(rootFolder, folderParam.getPath(), fileParam.getFile());
            FileUtil.write(path.toString(), fileParam.getNewContext());


            String fileContent = FileUtil.readFileToString(path.toString());
            assertTrue(StringUtils.isNotEmpty(fileContent));

        }
    }


    @Test
    public void runEntityGenerateWork() {
        String rootFolder = getGenerateResultFolder();
        String basePackage = "cn.garden";

        BaseGenerateWorkContext entityGenContext = new BaseGenerateWorkContext();
        entityGenContext.setOutput(rootFolder);
        entityGenContext.setBasePackage(basePackage);
        entityGenContext.setTemplateFiles(
                Arrays.asList("entityGenerate/entity.vtl", "entityGenerate/dto.vtl"));
        entityGenContext.setEngineName(TemplateEngineEnum.VELOCITY.getName());
        entityGenContext.setTargetLanguage(LanguageEnum.JAVA);

        EntityCodeGenerateWork entityGen = new EntityCodeGenerateWork(entityGenContext,
                new JsonFileEntityReader(getUserDefinition()));

        entityGen.run();

        assertEquals(4, CollectionUtils.size(entityGen.getOutputFiles()));
        for (String resultFile : entityGen.getOutputFiles()) {
            String fileContent = FileUtil.readFileToString(resultFile);
            assertTrue(StringUtils.isNotEmpty(fileContent));
        }

    }

    private List<Entity> readJsonUserDefinition(String fileName, List<String> names) {
        JsonFileEntityReader jsonFileEntityReader = new JsonFileEntityReader(fileName);
        return jsonFileEntityReader.getEntities(names);
    }


    private String getUserDefinition() {
        String userDefinition = ResourceUtil.convertResourceToFullPath("entityGenerate/userDefinition.json");
        return Paths.get(userDefinition).toString();
    }

    private String getGenerateResultFolder() {
        return FileConfig.getGenerateResultFolder();
    }
}
