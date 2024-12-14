package cn.garden.external;

import cn.garden.util.ExceptionUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author liwei
 */
public class TestProperties {

    /**
     * 使用 this.getClass().getResourceAsStream() 读取的需要放到对应的包名
     */
    @Test
    public void getMysqlFile() {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("config/json.properties");

        assertNotNull(inputStream);
    }

    @Test
    public void loadProperties() {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("test.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            throw ExceptionUtil.createDefaultException("加载资源失败", e);
        }

        assertNotNull(properties.getProperty("mysql.url"));
        assertNotNull(properties.getProperty("mysql.user"));
        assertNotNull(properties.getProperty("mysql.password"));
    }

}
