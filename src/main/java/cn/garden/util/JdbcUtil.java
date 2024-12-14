package cn.garden.util;

import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.JDBCType;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Jdbc工具类
 *
 * @author liwei
 */
public class JdbcUtil {
    private static final Map<String, JDBCType> JDBC_DATABASE_MAP = new HashMap<>();

    static {
        initJdbcDatabaseMap();
    }

    private static void initJdbcDatabaseMap() {
        //oracle
        JDBC_DATABASE_MAP.put("NVARCHAR2", JDBCType.VARCHAR);
        JDBC_DATABASE_MAP.put("VARCHAR2", JDBCType.VARCHAR);
        JDBC_DATABASE_MAP.put("NUMBER", JDBCType.DECIMAL);
        JDBC_DATABASE_MAP.put("DATETIME", JDBCType.TIMESTAMP);

        //mysql
        JDBC_DATABASE_MAP.put("BIT", JDBCType.BOOLEAN);
        JDBC_DATABASE_MAP.put("TINYINT", JDBCType.INTEGER);
        JDBC_DATABASE_MAP.put("INT", JDBCType.INTEGER);
        JDBC_DATABASE_MAP.put("INT UNSIGNED", JDBCType.INTEGER);
        JDBC_DATABASE_MAP.put("BIGINT UNSIGNED", JDBCType.BIGINT);
        JDBC_DATABASE_MAP.put("TEXT", JDBCType.LONGNVARCHAR);
    }

    /**
     * 处理 java.lang.IllegalArgumentException: No enum constant java.sql.JDBCType.NVARCHAR2
     * 使用已知的JDBCType替代未知的TYPE_NAME;
     * <p>
     */
    public static JDBCType convertDataBaseTypeToJdbcType(String databaseType) {
        if (StringUtils.isEmpty(databaseType)) {
            throw ExceptionUtil.createDefaultException("列类型为空");
        }

        //处理特殊类型，例如 TIMESTAMP(6)，
        int indexOf = StringUtils.indexOf(databaseType, "(");
        if (indexOf != -1) {
            databaseType = databaseType.substring(0, indexOf);
        }

        if (JDBC_DATABASE_MAP.containsKey(databaseType)) {
            return JDBC_DATABASE_MAP.get(databaseType);
        }
        return JDBCType.valueOf(databaseType);
    }

    public static Connection getConnection(String url, String user, String password) {
        try {
            setDriver(url);
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw ExceptionUtil.createDefaultException("链接数据库失败", e);
        }
    }

    public static void setDriver(String url) {
        try {
            if (StringUtils.contains(url, ":mysql")) {
                Class.forName("com.mysql.cj.jdbc.Driver");
            }
            // 考虑mariadb,如果兼容的话必须和mysql一样的连接方式，而不是:mariadb
            if (StringUtils.contains(url, ":mariadb")) {
                Class.forName("org.mariadb.jdbc.Driver");
            }
            if (StringUtils.contains(url, ":oracle")) {
                Class.forName("oracle.jdbc.OracleDriver");
            }
        } catch (ClassNotFoundException e) {
            throw ExceptionUtil.createDefaultException("找不到数据库驱动", e);
        }
    }
}
