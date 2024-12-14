package cn.garden.generate;

import cn.garden.config.DatabaseConfig;
import cn.garden.config.FileConfig;
import cn.garden.generate.enums.GeneratePropertyEnum;
import cn.garden.util.FileUtil;
import cn.garden.util.ResourceUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author liwei
 */
public class TestCodeGenerate {


    @Test
    public void createJsonFileCodeGenerate() {
        CodeGenerateContext codeGenerateContext = new CodeGenerateContext();
        codeGenerateContext.setTemplateRepositoryCode("entityDemo");
        codeGenerateContext.put(GeneratePropertyEnum.ENTITY_READER.getName(), "jsonFile");
        codeGenerateContext.put(GeneratePropertyEnum.JSON_FILE.getName(), "user.json");

        CodeGenerate codeGenerate = new CodeGenerate(codeGenerateContext);

        assertNotNull(codeGenerate.getCodeGenerateWork());
        assertNotNull(codeGenerateContext.getTemplateRepository());
    }

    @Test
    public void createJdbcCodeGenerate() {
        CodeGenerateContext codeGenerateContext = new CodeGenerateContext();
        codeGenerateContext.setTemplateRepositoryCode("entityDemo");
        codeGenerateContext.put(GeneratePropertyEnum.ENTITY_READER.getName(), "jdbc");
        codeGenerateContext.put(GeneratePropertyEnum.URL.getName(), DatabaseConfig.getMysqlUrl());
        codeGenerateContext.put(GeneratePropertyEnum.USER.getName(), DatabaseConfig.getMysqlUser());
        codeGenerateContext.put(GeneratePropertyEnum.PASSWORD.getName(), DatabaseConfig.getMysqlPassword());

        CodeGenerate codeGenerate = new CodeGenerate(codeGenerateContext);
        assertNotNull(codeGenerate.getCodeGenerateWork());
        assertNotNull(codeGenerateContext.getTemplateRepository());
    }

    @Test
    public void checkJdbcCodeGenerateFiles() {
        CodeGenerate codeGenerate = createJdbcCodeGenerateAndRun();

        assertTrue(CollectionUtils.isNotEmpty(codeGenerate.getOutputFiles()));
        assertEquals(1, CollectionUtils.size(codeGenerate.getOutputFiles()));
    }


    @Test
    public void checkCodeGenerateFiles() {
        CodeGenerate codeGenerate = createJsonFileCodeGenerateAndRun();

        assertTrue(CollectionUtils.isNotEmpty(codeGenerate.getOutputFiles()));
        assertEquals(1, CollectionUtils.size(codeGenerate.getOutputFiles()));
    }

    @Test
    public void checkCodeGenerateOutput() {
        CodeGenerate codeGenerate = createJsonFileCodeGenerateAndRun();

        assertTrue(StringUtils.contains(codeGenerate.getOutputFiles().get(0), "generateResult"));
    }

    @Test
    public void checkCodeGenerateFolderParam() {
        CodeGenerate codeGenerate = createJsonFileCodeGenerateAndRun();

        assertTrue(StringUtils.contains(
                codeGenerate.getOutputFiles().get(0)
                , "demo"));
    }

    @Test
    public void checkCodeGenerateFileParam() {
        CodeGenerate codeGenerate = createJsonFileCodeGenerateAndRun();

        assertTrue(StringUtils.contains(
                codeGenerate.getOutputFiles().get(0)
                , ".java"));
    }

    @Test
    public void checkCodeGenerateBasePackage() {
        CodeGenerate codeGenerate = createJsonFileCodeGenerateAndRun();

        assertTrue(StringUtils.contains(
                FileUtil.readFileToString(codeGenerate.getOutputFiles().get(0))
                , "garden"));
    }

    @Test
    public void checkCodeGenerateAuthor() {
        CodeGenerate codeGenerate = createJsonFileCodeGenerateAndRun();

        assertTrue(StringUtils.contains(
                FileUtil.readFileToString(codeGenerate.getOutputFiles().get(0))
                , "liwei"));
    }

    @Test
    public void checkCodeGenerateTag() {
        CodeGenerate codeGenerate = createJsonFileCodeGenerateAndRun();

        assertTrue(StringUtils.contains(
                FileUtil.readFileToString(codeGenerate.getOutputFiles().get(0))
                , "包含mysqlTag"));
    }

    @Test
    public void checkCodeGenerateImportString() {
        CodeGenerate codeGenerate = createJsonFileCodeGenerateAndRun();

        assertTrue(StringUtils.contains(
                FileUtil.readFileToString(codeGenerate.getOutputFiles().get(0))
                , "import java.time.LocalDateTime;"));
    }


    @Test
    public void checkCodeGenerateHasPropertyMethod() {
        CodeGenerate codeGenerate = createJsonFileCodeGenerateAndRun();

        assertTrue(StringUtils.contains(
                FileUtil.readFileToString(codeGenerate.getOutputFiles().get(0))
                , "该类包含Id字段"));
    }

    private CodeGenerate createJsonFileCodeGenerateAndRun() {
        CodeGenerateContext codeGenerateContext = new CodeGenerateContext();
        codeGenerateContext.setTemplateRepositoryCode("entityDemo");
        codeGenerateContext.setTargetNames(List.of("user"));
        codeGenerateContext.setOutput(getGenerateResultFolder());
        codeGenerateContext.setBasePackage("cn.garden");
        codeGenerateContext.setAuthor("liwei");
        codeGenerateContext.setTags(List.of("mysql"));

        codeGenerateContext.put(GeneratePropertyEnum.JSON_FILE.getName(), getUserDefinition());
        codeGenerateContext.put(GeneratePropertyEnum.ENTITY_READER.getName(), "jsonFile");

        CodeGenerate codeGenerate = new CodeGenerate(codeGenerateContext);
        codeGenerate.run();

        return codeGenerate;
    }

    private CodeGenerate createJdbcCodeGenerateAndRun() {
        CodeGenerateContext codeGenerateContext = new CodeGenerateContext();
        codeGenerateContext.setTemplateRepositoryCode("entityDemo");
        codeGenerateContext.setTargetNames(List.of("user"));
        codeGenerateContext.setOutput(getGenerateResultFolder());
        codeGenerateContext.setBasePackage("cn.garden.jdbc");
        codeGenerateContext.put(GeneratePropertyEnum.ENTITY_READER.getName(), "jdbc");
        codeGenerateContext.put(GeneratePropertyEnum.URL.getName(), DatabaseConfig.getMysqlUrl());
        codeGenerateContext.put(GeneratePropertyEnum.USER.getName(), DatabaseConfig.getMysqlUser());
        codeGenerateContext.put(GeneratePropertyEnum.PASSWORD.getName(), DatabaseConfig.getMysqlPassword());

        CodeGenerate codeGenerate = new CodeGenerate(codeGenerateContext);
        codeGenerate.run();
        return codeGenerate;
    }


    private String getUserDefinition() {
        String userDefinition = ResourceUtil.convertResourceToFullPath("entityGenerate/userDefinition.json");
        return Paths.get(userDefinition).toString();
    }

    private String getGenerateResultFolder() {
        return FileConfig.getGenerateResultFolder();
    }
}
