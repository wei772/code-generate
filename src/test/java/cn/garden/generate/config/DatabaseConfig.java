package cn.garden.generate.config;

import cn.garden.generate.util.ExceptionUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author liwei
 */
public class DatabaseConfig {

    private static final Properties properties;

    static {
        InputStream inputStream = DatabaseConfig.class.getClassLoader()
                .getResourceAsStream("test.properties");
        properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            throw ExceptionUtil.createDefaultException("加载配置文件 test.properties 失败", e);
        }
    }

    public static String getMysqlUrl() {
        return properties.getProperty("mysql.url");
    }

    public static String getMysqlUser() {
        return properties.getProperty("mysql.user");
    }

    public static String getMysqlPassword() {
        return properties.getProperty("mysql.password");
    }

}
