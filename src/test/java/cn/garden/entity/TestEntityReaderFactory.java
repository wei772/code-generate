package cn.garden.entity;

import cn.garden.config.DatabaseConfig;
import cn.garden.entity.reader.EntityReader;
import cn.garden.entity.reader.factory.EntityReaderFactory;
import cn.garden.generate.enums.GeneratePropertyEnum;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestEntityReaderFactory {


    @Test
    public void createErrorEntityReaderFactory() {
        assertThrows(RuntimeException.class, () ->
                EntityReaderFactory.create("test", new HashMap<>()));
    }


    @Test
    public void createErrorJdbcEntityReader() {
        assertThrows(RuntimeException.class, () -> EntityReaderFactory.create("jdbc", new HashMap<>()));
    }


    @Test
    public void createJdbcEntityReader() {
        HashMap<String, Object> map = new HashMap<>();
        map.put(GeneratePropertyEnum.URL.getName(), DatabaseConfig.getMysqlUrl());
        map.put(GeneratePropertyEnum.USER.getName(), DatabaseConfig.getMysqlUrl());
        map.put(GeneratePropertyEnum.PASSWORD.getName(), DatabaseConfig.getMysqlPassword());

        EntityReader jdbc = EntityReaderFactory.create("jdbc", map);
        assertNotNull(jdbc);

    }
}
