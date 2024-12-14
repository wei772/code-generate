package cn.garden;

import cn.garden.generate.CodeGenerate;
import cn.garden.generate.CodeGenerateContext;
import cn.garden.generate.enums.GeneratePropertyEnum;
import cn.garden.util.ExceptionUtil;
import cn.garden.util.PropertiesGroup;
import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 命令行程序入口
 *
 * @author liwei
 */
public class CommandLineApplication {

    private static final String JAVA_PROPERTY_OPTION = "D";

    private static final String CONFIG_OPTION = "c";

    private static final Options OPTIONS = createOptions();

    /**
     * 用来单元测试校验输出结果
     */
    private static List<String> OUTPUT_FILES = new ArrayList<>();


    public static void main(String[] args) {
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(OPTIONS, args);
            PropertiesGroup propertiesGroup = loadProperties(cmd);
            CodeGenerateContext codeGenerateContext = createCodeGenerateContext(propertiesGroup);
            CodeGenerate codeGenerate = new CodeGenerate(codeGenerateContext);
            codeGenerate.run();
            OUTPUT_FILES = codeGenerate.getOutputFiles();

        } catch (UnrecognizedOptionException e) {
            printHelp();
            throw ExceptionUtil.createDefaultException("运行代码生成失败，未识别的选项:" + e.getOption(), e);
        } catch (MissingOptionException e) {
            printHelp();
            throw ExceptionUtil.createDefaultException("运行代码生成失败，必填的选项:" + e.getMissingOptions(), e);
        } catch (Exception e) {
            printHelp();
            throw ExceptionUtil.createDefaultException("运行代码生成失败", e);
        }
    }

    public static List<String> getOutputFiles() {
        return OUTPUT_FILES;
    }

    private static Options createOptions() {
        Options options = new Options();
        Option javaOption = Option.builder(JAVA_PROPERTY_OPTION)
                .hasArgs()
                .desc("支持Java -D<property=value> 配置")
                .valueSeparator('=')
                .build();

        Option configOption = Option.builder(CONFIG_OPTION)
                .longOpt("config")
                .hasArgs()
                .desc("Java properties 配置文件")
                .valueSeparator('=')
                .build();

        options.addOption(javaOption);
        options.addOption(configOption);
        return options;
    }

    private static PropertiesGroup loadProperties(CommandLine cmd) {
        PropertiesGroup propertiesGroup = new PropertiesGroup();

        // 优先级最高
        Properties optionProperties = cmd.getOptionProperties(JAVA_PROPERTY_OPTION);
        propertiesGroup.addProperties(optionProperties);

        // 优先级低
        if (cmd.hasOption(CONFIG_OPTION)) {
            String configOptionValue = cmd.getOptionValue(CONFIG_OPTION);
            try (InputStream configInputStream = new FileInputStream(configOptionValue)) {
                Properties applicationProperties = new Properties();
                applicationProperties.load(configInputStream);
                propertiesGroup.addProperties(applicationProperties);
            } catch (FileNotFoundException e) {
                throw ExceptionUtil.createDefaultException("找不到配置文件", e);
            } catch (IOException e) {
                throw ExceptionUtil.createDefaultException("加载资源失败", e);
            }
        }

        if (propertiesGroup.isEmpty()) {
            printHelp();
            throw ExceptionUtil.createDefaultException("没有参数配置");
        }
        return propertiesGroup;
    }


    private static CodeGenerateContext createCodeGenerateContext(PropertiesGroup propertiesGroup) {
        CodeGenerateContext codeGenerateContext = new CodeGenerateContext();

        codeGenerateContext.setTemplateRepositoryCode(
                propertiesGroup.getProperty(GeneratePropertyEnum.TEMPLATE_REPOSITORY_CODE.getName())
        );
        codeGenerateContext.setOutput(
                propertiesGroup.getProperty(GeneratePropertyEnum.BASE_FOLDER.getName())
        );
        codeGenerateContext.setBasePackage(
                propertiesGroup.getProperty(GeneratePropertyEnum.BASE_PACKAGE.getName())
        );
        codeGenerateContext.setAuthor(
                propertiesGroup.getProperty(GeneratePropertyEnum.AUTHOR.getName())
        );

        codeGenerateContext.setTargetNames(
                getListValue(
                        propertiesGroup.getProperty(GeneratePropertyEnum.ENTITY_NAMES.getName())
                )
        );
        codeGenerateContext.setTags(
                getListValue(
                        propertiesGroup.getProperty(GeneratePropertyEnum.TAG.getName())
                )
        );

        putCodeGenerateContext(propertiesGroup, codeGenerateContext, GeneratePropertyEnum.ENTITY_READER);
        putCodeGenerateContext(propertiesGroup, codeGenerateContext, GeneratePropertyEnum.JSON_FILE);
        putCodeGenerateContext(propertiesGroup, codeGenerateContext, GeneratePropertyEnum.URL);
        putCodeGenerateContext(propertiesGroup, codeGenerateContext, GeneratePropertyEnum.PASSWORD);
        putCodeGenerateContext(propertiesGroup, codeGenerateContext, GeneratePropertyEnum.USER);

        return codeGenerateContext;
    }

    private static List<String> getListValue(String values) {
        if (StringUtils.isEmpty(values)) {
            return new ArrayList<>();
        } else {
            return List.of(StringUtils.split(
                    values
                    , ","));
        }
    }

    private static void putCodeGenerateContext(
            PropertiesGroup propertiesGroup
            , CodeGenerateContext codeGenerateContext
            , GeneratePropertyEnum GeneratePropertyEnum) {
        codeGenerateContext.put(
                GeneratePropertyEnum.getName()
                , propertiesGroup.getProperty(GeneratePropertyEnum.getName())
        );
    }


    private static void printHelp() {
        System.out.println();
        new HelpFormatter().printHelp("code-generate,详情解释参照GeneratePropertyEnum枚举", OPTIONS);

        System.out.println("Java Property 列表 ,使用的时候请加上-D前缀，范例-Dgenerate.author=liwei");
        for (GeneratePropertyEnum value : GeneratePropertyEnum.values()) {
            System.out.printf("%-28s  %s%n", value.getName(), value.getDescription());
        }
    }

}
