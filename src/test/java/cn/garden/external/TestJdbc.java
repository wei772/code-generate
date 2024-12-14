package cn.garden.external;

import cn.garden.config.DatabaseConfig;
import cn.garden.util.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author liwei
 */
public class TestJdbc {

    private static Connection getConnection() {
        try {
            if (StringUtils.contains(DatabaseConfig.getMysqlUrl(), ":mysql")) {
                Class.forName("com.mysql.cj.jdbc.Driver");
            }
            return DriverManager.getConnection(
                    DatabaseConfig.getMysqlUrl()
                    , DatabaseConfig.getMysqlUser()
                    , DatabaseConfig.getMysqlPassword());
        } catch (SQLException e) {
            throw ExceptionUtil.createDefaultException("链接失败", e);
        } catch (ClassNotFoundException e) {
            throw ExceptionUtil.createDefaultException("找不到驱动", e);
        }
    }

    @Test
    public void connectionDataBase() {
        Connection connection = getConnection();
        assertNotNull(connection);
    }

    /**
     * 通过jdbc获取数据库中的表结构
     */
    @Test
    public void getMetaData() {
        Connection connection = getConnection();
        try {

            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(
                    null, "%", "user", new String[]{"TABLE"});

            ResultSet primaryKeyResultSet = metaData.getPrimaryKeys(null, null, "user");
            List<String> primaryKeys = new ArrayList<>();
            while (primaryKeyResultSet.next()) {
                String primaryKeyColumnName = primaryKeyResultSet.getString("COLUMN_NAME");
                primaryKeys.add(primaryKeyColumnName);
            }

            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                assertEquals("user", tableName);
                ResultSet columns = metaData.getColumns(
                        null, "%", tableName, "id");
                columns.next();
                assertEquals("id", columns.getString("COLUMN_NAME"));
                assertEquals("BIGINT", columns.getString("TYPE_NAME"));
                assertTrue(primaryKeys.contains("id"));

            }


        } catch (SQLException e) {
            throw ExceptionUtil.createDefaultException("获取元数据失败", e);
        }

    }
}
