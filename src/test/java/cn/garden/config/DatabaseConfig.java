package cn.garden.config;

import cn.garden.util.ExceptionUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author liwei
 */
public class DatabaseConfig {

    private static final String mysqlUrl;

    private static final String mysqlUser;

    private static final String mysqlPassword;

    static {
        InputStream inputStream = DatabaseConfig.class.getClassLoader()
                .getResourceAsStream("test.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            throw ExceptionUtil.createDefaultException("加载配置文件 test.properties 失败", e);
        }

        mysqlUrl = properties.getProperty("mysql.url");
        mysqlUser = properties.getProperty("mysql.user");
        mysqlPassword = properties.getProperty("mysql.password");
    }

    public static String getMysqlUrl() {
        return mysqlUrl;
    }

    public static String getMysqlUser() {
        return mysqlUser;
    }

    public static String getMysqlPassword() {
        return mysqlPassword;
    }

}
