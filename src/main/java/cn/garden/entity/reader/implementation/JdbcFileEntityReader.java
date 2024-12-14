package cn.garden.entity.reader.implementation;

import cn.garden.entity.Entity;
import cn.garden.entity.Property;
import cn.garden.entity.reader.EntityReader;
import cn.garden.util.ExceptionUtil;
import cn.garden.util.JdbcUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用Jdbc从数据库读取实体列表
 *
 * @author liwei
 */
public class JdbcFileEntityReader extends EntityReader {

    public static final String COLUMN_NAME = "COLUMN_NAME";
    public static final String TYPE_NAME = "TYPE_NAME";
    public static final String REMARKS = "REMARKS";
    public static final String TABLE = "TABLE";
    public static final String TABLE_NAME = "TABLE_NAME";
    public static final String COMMON_COLUMN_NAME_PATTERN = "%";

    private final String url;
    private final String user;
    private final String password;

    public JdbcFileEntityReader(String url, String user, String password) {
        if (StringUtils.isEmpty(url)) {
            throw ExceptionUtil.createDefaultException("JdbcFileEntityReader的url不能为空");
        }
        if (StringUtils.isEmpty(user)) {
            throw ExceptionUtil.createDefaultException("JdbcFileEntityReader的user不能为空");
        }
        if (StringUtils.isEmpty(password)) {
            throw ExceptionUtil.createDefaultException("JdbcFileEntityReader的password不能为空");
        }

        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public List<Entity> getEntities(List<String> names) {
        Connection connection = JdbcUtil.getConnection(url, user, password);
        return getEntities(connection, names);
    }

    private List<Entity> getEntities(Connection connection, List<String> names) {
        try {
            String catalog = connection.getCatalog();
            String schema = connection.getSchema();
            DatabaseMetaData metaData = connection.getMetaData();

            List<Entity> entities = new ArrayList<>();
            if (CollectionUtils.isEmpty(names)) {
                ResultSet tables = metaData.getTables(
                        catalog, schema, null, new String[]{TABLE});
                while (tables.next()) {
                    Entity entity = new Entity();
                    entity.setSourceName(tables.getString(TABLE_NAME));
                    entity.setDescription(tables.getString(REMARKS));

                    entities.add(entity);
                }
            } else {
                for (String name : names) {
                    Entity entity = new Entity();
                    entity.setSourceName(name);
                    ResultSet tables = metaData.getTables(
                            catalog, schema, name, new String[]{TABLE});

                    if (tables.next()) {
                        entity.setDescription(tables.getString(REMARKS));
                        entities.add(entity);
                    } else {
                        throw ExceptionUtil.createDefaultException("找不到表" + name);
                    }
                }
            }

            for (Entity entity : entities) {
                loadProperties(connection, entity);
            }

            return entities;
        } catch (SQLException e) {
            throw ExceptionUtil.createDefaultException("解析数据库表级元数据失败", e);
        }
    }


    private void loadProperties(Connection connection, Entity entity) {
        try {
            String catalog = connection.getCatalog();
            String schema = connection.getSchema();

            DatabaseMetaData metaData = connection.getMetaData();

            ResultSet primaryKeyResultSet = metaData.getPrimaryKeys(null, null, entity.getSourceName());
            List<String> primaryKeys = new ArrayList<>();
            while (primaryKeyResultSet.next()) {
                String primaryKeyColumnName = primaryKeyResultSet.getString(COLUMN_NAME);
                primaryKeys.add(primaryKeyColumnName);
            }

            ResultSet columns = metaData.getColumns(
                    catalog, schema, entity.getSourceName(), COMMON_COLUMN_NAME_PATTERN);
            while (columns.next()) {
                Property property = new Property();

                property.setSourceName(columns.getString(COLUMN_NAME));
                property.setJdbcType(JdbcUtil.convertDataBaseTypeToJdbcType(columns.getString(TYPE_NAME)));
                property.setPrimaryKey(primaryKeys.contains(columns.getString(COLUMN_NAME)));
                property.setDescription(columns.getString(REMARKS));

                entity.addProperty(property);
            }

        } catch (SQLException e) {
            throw ExceptionUtil.createDefaultException("解析数据库列级元数据失败", e);
        }
    }


}
