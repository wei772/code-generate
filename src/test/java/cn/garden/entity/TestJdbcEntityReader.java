package cn.garden.entity;

import cn.garden.config.DatabaseConfig;
import cn.garden.entity.reader.implementation.JdbcFileEntityReader;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author liwei
 */
public class TestJdbcEntityReader {


    @Test
    public void getJdbcEntityWithNames() {
        JdbcFileEntityReader jdbcFileEntityReader = new JdbcFileEntityReader(
                DatabaseConfig.getMysqlUrl(),
                DatabaseConfig.getMysqlUser(),
                DatabaseConfig.getMysqlPassword());
        List<Entity> entities = jdbcFileEntityReader.getEntities(List.of("user"));

        assertEquals(1, CollectionUtils.size(entities));
        assertEquals("user", entities.get(0).getSourceName());
        assertEquals(3, CollectionUtils.size(entities.get(0).getProperties()));
    }

    @Test
    @Disabled
    public void getOracleAllJdbcEntity() {
        JdbcFileEntityReader jdbcFileEntityReader = new JdbcFileEntityReader(
                "jdbc:oracle:thin:@HOST:1521:DB"
                , "USER"
                , "PASSWORD");
        List<Entity> entities = jdbcFileEntityReader.getEntities(null);

        assertTrue(CollectionUtils.isNotEmpty(entities));
        assertTrue(CollectionUtils.isNotEmpty(entities.get(0).getProperties()));
    }

    @Test
    @Disabled
    public void getMysqlAllJdbcEntity() {
        JdbcFileEntityReader jdbcFileEntityReader = new JdbcFileEntityReader(
                "jdbc:mysql://HOST:3306/DB"
                , "USER"
                , "PASSWORD"
        );
        List<Entity> entities = jdbcFileEntityReader.getEntities(null);

        assertTrue(CollectionUtils.isNotEmpty(entities));
    }

    @Test
    public void getAllJdbcEntity() {
        JdbcFileEntityReader jdbcFileEntityReader = new JdbcFileEntityReader(
                DatabaseConfig.getMysqlUrl(),
                DatabaseConfig.getMysqlUser(),
                DatabaseConfig.getMysqlPassword());
        List<Entity> entities = jdbcFileEntityReader.getEntities(null);

        assertEquals(2, CollectionUtils.size(entities));
    }

    @Test
    public void notExistTableJdbcEntity() {
        assertThrows(RuntimeException.class, () -> {
            JdbcFileEntityReader jdbcFileEntityReader = new JdbcFileEntityReader(
                    DatabaseConfig.getMysqlUrl(),
                    DatabaseConfig.getMysqlUser(),
                    DatabaseConfig.getMysqlPassword());
            jdbcFileEntityReader.getEntities(List.of("notExistTable"));
        });
    }


    @Test
    public void createNullUrlJdbcEntity() {
        assertThrows(RuntimeException.class, () ->
                new JdbcFileEntityReader(null, DatabaseConfig.getMysqlUser(), DatabaseConfig.getMysqlPassword()));
    }

    @Test
    public void createNullUserJdbcEntity() {
        assertThrows(RuntimeException.class, () ->
                new JdbcFileEntityReader(DatabaseConfig.getMysqlUrl(), null, DatabaseConfig.getMysqlPassword()));
    }

    @Test
    public void createNullPasswordJdbcEntity() {
        assertThrows(RuntimeException.class, () ->
                new JdbcFileEntityReader(DatabaseConfig.getMysqlUrl(), DatabaseConfig.getMysqlUser(), null));
    }


}
