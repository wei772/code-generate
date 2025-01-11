package cn.garden.generate.entity.reader.factory;

import cn.garden.generate.entity.reader.EntityReader;
import cn.garden.generate.entity.reader.EntityReaderType;
import cn.garden.generate.entity.reader.implementation.JdbcFileEntityReader;
import cn.garden.generate.entity.reader.implementation.JsonFileEntityReader;
import cn.garden.generate.GenerateProperty;

import java.util.Map;

/**
 * 实体读取器工厂方法
 *
 * @author liwei
 */
public class EntityReaderFactory {

    public static EntityReader create(String name, Map<String, Object> extendMap) {
        EntityReaderType entityReaderType = EntityReaderType.of(name);

        return switch (entityReaderType) {
            case JSON_FILE -> new JsonFileEntityReader(
                    (String) extendMap.get(GenerateProperty.JSON_FILE.getName())
            );
            case JDBC -> new JdbcFileEntityReader(
                    (String) extendMap.get(GenerateProperty.URL.getName()),
                    (String) extendMap.get(GenerateProperty.USER.getName()),
                    (String) extendMap.get(GenerateProperty.PASSWORD.getName())
            );
        };

    }
}
