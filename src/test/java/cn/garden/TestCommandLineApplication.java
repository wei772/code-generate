package cn.garden;

import cn.garden.config.DatabaseConfig;
import cn.garden.config.FileConfig;
import cn.garden.util.ResourceUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * @author liwei
 */
public class TestCommandLineApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestCommandLineApplication.class);

    final String output = FileConfig.getGenerateResultFolder();


    @Test
    public void runJsonFileCommandLineApplication() {
        String userDefinition = ResourceUtil.convertResourceToFullPath("entityGenerate/userDefinition.json");
        String[] strings = new String[]{
                "-Dtemplate.repository=entityDemo"
                , "-Dgenerate.output=" + output
                , "-Dgenerate.basePackage=cn.cli"
                , "-Dgenerate.author=liwei"
                , "-Dgenerate.tags=mysql"
                , "-Dgenerate.tags=jdbc,mysql"
                , "-Dentity.reader=jsonFile"
                , "-Dentity.reader.json.file=jsonFile"
                , "-Dentity.reader.json.file=" + userDefinition
        };
        String joined = StringUtils.join(strings, "  ");
        LOGGER.debug(joined);
        CommandLineApplication.main(strings);

        assertEquals(2, CollectionUtils.size(CommandLineApplication.getOutputFiles()));
    }

    @Test
    public void runConfigJsonFileCommandLineApplication() {
        String config = ResourceUtil.convertResourceToFullPath("config/json.properties");
        String[] strings = new String[]{
                "-c=" + config
        };
        String joined = StringUtils.join(strings, "  ");
        LOGGER.debug(joined);
        CommandLineApplication.main(strings);

        assertEquals(1, CollectionUtils.size(CommandLineApplication.getOutputFiles()));
    }

    @Test
    public void runNotExistConfigJsonFileCommandLineApplication() {
        assertThrows(RuntimeException.class, () -> {
            String config = "config/jsonNotExist.properties";
            String[] strings = new String[]{
                    "-c=" + config
            };
            CommandLineApplication.main(strings);
        });
    }

    @Test
    public void runErrorConfigJsonFileCommandLineApplication() {
        assertThrows(RuntimeException.class, () -> {
            String config = ResourceUtil.convertResourceToFullPath("config/errorJson.properties");
            String[] strings = new String[]{
                    "-c=" + config
            };
            CommandLineApplication.main(strings);
        });
    }


    @Test
    public void runJdbcCommandLineApplication() {
        String[] strings = new String[]{
                "-Dtemplate.repository=entityDemo"
                , "-Dgenerate.output=" + output
                , "-Dgenerate.basePackage=cn.cli.jdbc"
                , "-Dentity.reader=jdbc"
                , "-Dentity.names=user"
                , "-Dentity.reader.jdbc.url=" + DatabaseConfig.getMysqlUrl()
                , "-Dentity.reader.jdbc.user=" + DatabaseConfig.getMysqlUser()
                , "-Dentity.reader.jdbc.password=" + DatabaseConfig.getMysqlPassword()

        };
        String joined = StringUtils.join(strings, "  ");
        LOGGER.debug(joined);
        CommandLineApplication.main(strings);

        assertEquals(1, CollectionUtils.size(CommandLineApplication.getOutputFiles()));
    }

    @Test
    public void runEmptyArgCommandLineApplication() {
        assertThrows(RuntimeException.class, () -> {
            String[] strings = new String[]{};
            CommandLineApplication.main(strings);
        });
    }

    @Test
    public void runUnrecognizedOptionCommandLineApplication() {
        assertThrows(RuntimeException.class, () -> {
            String[] strings = new String[]{"-rr=we"};
            CommandLineApplication.main(strings);
        });
    }
}
