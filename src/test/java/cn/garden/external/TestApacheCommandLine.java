package cn.garden.external;  //library包专门用于测试外部功能

import cn.garden.util.ExceptionUtil;
import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 官方使用文档 <a href="https://commons.apache.org/proper/commons-cli/">...</a>
 * <p>
 *
 * @author liwei
 */
public class TestApacheCommandLine {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestApacheCommandLine.class);


    /**
     * shortName 最好是一个字符，Command Line容易错误
     */
    @Test
    public void getHasArgThrows() {
        //Unrecognized option: -e=jsonFile
        assertThrows(RuntimeException.class, () ->
        {
            Options options = new Options();
            Option option = Option.builder("e").longOpt("entityReader")
                    .desc("use SIZE-byte blocks")
                    .hasArg(false)
                    .build();
            options.addOption(option);

            String[] args = new String[]{"-e=jsonFile"};

            try {

                // create the command line parser
                CommandLineParser parser = new DefaultParser();

                // parse the command line arguments
                CommandLine line = parser.parse(options, args);
                assertTrue(line.hasOption("e"));
                // print the value of block-size
                assertEquals("jsonFile", line.getOptionValue("e"));

            } catch (Exception exp) {
                handleException(exp);
            }
        });
    }

    /**
     * hasArg=false 没有参数值
     */
    @Test
    public void hasArgNullValue() {
        Options options = new Options();
        Option option = Option.builder("e").longOpt("entityReader")
                .desc("use SIZE-byte blocks")
                .hasArg(false)
                .build();
        options.addOption(option);

        String[] args = new String[]{"-e", "jsonFile"};
        try {

            // create the command line parser
            CommandLineParser parser = new DefaultParser();

            // parse the command line arguments
            CommandLine line = parser.parse(options, args);
            assertTrue(line.hasOption("e"));
            assertNull(line.getOptionValue("e"));

        } catch (Exception exp) {
            handleException(exp);
        }
    }


    /**
     * 测试JavaProperty
     */
    @Test
    public void getJavaPropertyValue() {
        Options options = new Options();
        Option javaOption = Option.builder("D")
                .hasArgs()
                .valueSeparator('=')
                .build();

        options.addOption(javaOption);

        String[] args = new String[]{"-Dgenerate.package=cn"};
        try {

            // create the command line parser
            CommandLineParser parser = new DefaultParser();

            // parse the command line arguments
            CommandLine line = parser.parse(options, args);
            // assertTrue(line.hasOption("generate.package"));
            Properties optionProperties = line.getOptionProperties("D");
            assertEquals("cn", optionProperties.getProperty("generate.package"));

        } catch (Exception exp) {
            handleException(exp);
        }
    }

    /**
     * shortName 最好是一个字符，Command Line容易错误
     */
    @Test
    public void doubleCharOptionValueThrows() {

        assertThrows(RuntimeException.class, () ->
        {
            Options options = new Options();

            options.addOption(Option.builder("er").longOpt("entityReader")
                    .desc("use SIZE-byte blocks")
                    .hasArg()
                    .build());

            // --er=jsonFile 也不行
            String[] args = new String[]{"-er=jsonFile"};

            try {

                // create the command line parser
                CommandLineParser parser = new DefaultParser();

                // parse the command line arguments
                CommandLine line = parser.parse(options, args);
                assertTrue(line.hasOption("er"));
                // print the value of block-size
                //assertEquals("jsonFile", line.getOptionValue("er"));

            } catch (ParseException exp) {
                handleException(exp);
            }
        });
        // create the Options

    }


    @Test
    public void getOptionValue() {

        // 创建Options对象，用于定义命令行参数
        Options options = new Options();

        // 定义一个简单的参数 -v 或 --version，用来显示版本信息
        Option version = new Option("v", "version", true, "显示版本信息");

        // hasArg参数理解错了，浪费了好多时间
        version.setRequired(true);
        options.addOption(version);

        // 创建命令行解析器
        CommandLineParser parser = new DefaultParser();
        try {
            String[] args = new String[]{"--version=v1"};
            // 解析命令行参数
            CommandLine commandLine = parser.parse(options, args);

            assertTrue(commandLine.hasOption("version"));
            assertTrue(commandLine.hasOption("v"));
            assertEquals("v1", commandLine.getOptionValue("version"));
            assertEquals("v1", commandLine.getParsedOptionValue("version"));

        } catch (ParseException e) {
            LOGGER.info(e.getMessage());
            new HelpFormatter().printHelp("工具名称", options);
            throw ExceptionUtil.createDefaultException("创建失败");
        }

    }

    @Test
    public void getOptionCommaArrayValue() {

        // 创建Options对象，用于定义命令行参数
        Options options = new Options();

        // 定义一个简单的参数 -v 或 --version，用来显示版本信息
        Option version = new Option("e", "entityNames", true, "显示版本信息");

        // hasArg参数理解错了，浪费了好多时间
        version.setRequired(true);
        options.addOption(version);

        // 创建命令行解析器
        CommandLineParser parser = new DefaultParser();
        try {
            String[] args = new String[]{"--entityNames=user,userGroup", ""};
            // 解析命令行参数
            CommandLine commandLine = parser.parse(options, args);

            assertTrue(commandLine.hasOption("entityNames"));
            assertTrue(commandLine.hasOption("e"));
            String values = commandLine.getOptionValue("e");
            String[] split = StringUtils.split(values, ",");
            assertEquals("user", split[0]);
            assertEquals("userGroup", split[1]);

        } catch (ParseException e) {
            LOGGER.info(e.getMessage());
            new HelpFormatter().printHelp("工具名称", options);
            throw ExceptionUtil.createDefaultException("创建失败");
        }
    }

    @Test
    public void getOptionArrayValue() {

        // 创建Options对象，用于定义命令行参数
        Options options = new Options();

        // 定义一个简单的参数 -v 或 --version，用来显示版本信息
        Option version = new Option("e", "entityNames", true, "显示版本信息");

        // hasArg参数理解错了，浪费了好多时间
        version.setRequired(true);
        options.addOption(version);

        // 创建命令行解析器
        CommandLineParser parser = new DefaultParser();
        try {
            String[] args = new String[]{"--entityNames=user", "--entityNames=userGroup"};
            // 解析命令行参数
            CommandLine commandLine = parser.parse(options, args);

            assertTrue(commandLine.hasOption("entityNames"));
            assertTrue(commandLine.hasOption("e"));
            String[] values = commandLine.getOptionValues("e");
            assertEquals("user", values[0]);
            assertEquals("userGroup", values[1]);

        } catch (ParseException e) {
            LOGGER.info(e.getMessage());
            new HelpFormatter().printHelp("工具名称", options);
            throw ExceptionUtil.createDefaultException("创建失败");
        }
    }


    /**
     * Java Property 不支持多次设值获取数组
     */
    @Test
    public void getJavaOptionArrayValue() {

        // 创建Options对象，用于定义命令行参数
        Options options = new Options();

        Option javaOption = Option.builder("D")
                .hasArgs()
                .desc("支持Java -D<property=value> 配置")
                .valueSeparator('=')
                .build();

        options.addOption(javaOption);

        // 创建命令行解析器
        CommandLineParser parser = new DefaultParser();
        try {
            String[] args = new String[]{"-DentityNames=user", "-DentityNames=userGroup"};
            // 解析命令行参数
            CommandLine commandLine = parser.parse(options, args);
            Properties properties = commandLine.getOptionProperties("D");

            String names = (String) properties.get("entityNames");

            assertEquals("userGroup",names);
        } catch (ParseException e) {
            LOGGER.info(e.getMessage());
            new HelpFormatter().printHelp("工具名称", options);
            throw ExceptionUtil.createDefaultException("创建失败");
        }
    }

    private void handleException(Exception exp) {
        LOGGER.info("Unexpected exception:{}", exp.getMessage());
        throw ExceptionUtil.createDefaultException("创建失败", exp);
    }
}
