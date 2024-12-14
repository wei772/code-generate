package cn.garden.util;

import cn.garden.config.DatabaseConfig;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author liwei
 */
public class TestJdbcUtil {

    /**
     * 因为没驱动，所以异常
     */
    @Test
    @Disabled
    public void getOracleConnection() {
        //设置超时时间
        //这些设置都没有效果
        System.setProperty("oracle.net.CONNECT_TIMEOUT", "50");
        DriverManager.setLoginTimeout(1);

        assertThrows(RuntimeException.class, () -> JdbcUtil.getConnection(
                "jdbc:oracle:thin:@HOST:1521:DB"
                , null
                , null
        ));
    }

    /**
     * 因为没驱动，所以异常
     */
    @Test
    public void setOracleDriver() {
        JdbcUtil.setDriver("jdbc:oracle:thin:@HOST:1521:DB");
    }

    /**
     * 因为没驱动，所以异常
     */
    @Test
    public void setMariadbDriver() {
        JdbcUtil.setDriver("jdbc:mariadb://localhost:3306/code_generate");
    }


    @Test
    public void getMysqlConnection() {
        Connection connection = JdbcUtil.getConnection(
                DatabaseConfig.getMysqlUrl()
                , DatabaseConfig.getMysqlUser()
                , DatabaseConfig.getMysqlPassword());

        assertNotNull(connection);
    }

    @Test
    public void getUserData() {
        try (Connection connection = JdbcUtil.getConnection(
                DatabaseConfig.getMysqlUrl()
                , DatabaseConfig.getMysqlUser()
                , DatabaseConfig.getMysqlPassword())) {
            try (Statement statement = connection.createStatement()) {
                statement.executeQuery("select * from user");
                try (ResultSet resultSet = statement.getResultSet()) {
                    if (resultSet.next()) {
                        String code = resultSet.getString("code");
                        assertEquals("wei", code);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void getUserDataWithParam() {
        try (Connection connection = JdbcUtil.getConnection(
                DatabaseConfig.getMysqlUrl()
                , DatabaseConfig.getMysqlUser()
                , DatabaseConfig.getMysqlPassword())) {
            try (PreparedStatement prepareStatement
                         = connection.prepareStatement("select * from user where id=?")) {
                prepareStatement.setLong(1, 1);
                try (ResultSet resultSet = prepareStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String code = resultSet.getString("code");
                        assertEquals("wei", code);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getMariadbConnection() {
        String url = DatabaseConfig.getMysqlUrl().replace("mysql", "mariadb");
        Connection connection = JdbcUtil.getConnection(
                url
                , DatabaseConfig.getMysqlUser()
                , DatabaseConfig.getMysqlPassword());

        assertNotNull(connection);
    }

    @Test
    public void getConnectionWithNullPassword() {
        assertThrows(RuntimeException.class, () -> JdbcUtil.getConnection(
                DatabaseConfig.getMysqlUrl()
                , DatabaseConfig.getMysqlUser()
                , null
        ));
    }

    /**
     * 为了单元测试覆盖率开放getJdbcType方法
     * 可能说明这个方法值得开放
     */
    @Test
    public void specialCharTypeToJdbcType() {
        JDBCType jdbcType = JdbcUtil.convertDataBaseTypeToJdbcType("TIMESTAMP(6)");

        assertEquals(JDBCType.TIMESTAMP, jdbcType);
    }

    @Test
    public void specialTypeToJdbcType() {
        JDBCType jdbcType = JdbcUtil.convertDataBaseTypeToJdbcType("VARCHAR2");

        assertEquals(JDBCType.VARCHAR, jdbcType);
    }

    @Test
    public void nullTypeToJdbcType() {
        assertThrows(RuntimeException.class, () ->
                JdbcUtil.convertDataBaseTypeToJdbcType(null));
    }
}
