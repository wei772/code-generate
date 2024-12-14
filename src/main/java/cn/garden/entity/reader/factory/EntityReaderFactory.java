package cn.garden.entity.reader.factory;

import cn.garden.entity.reader.EntityReader;
import cn.garden.entity.reader.enums.EntityReaderEnum;
import cn.garden.entity.reader.implementation.JdbcFileEntityReader;
import cn.garden.entity.reader.implementation.JsonFileEntityReader;
import cn.garden.generate.enums.GeneratePropertyEnum;

import java.util.Map;

/**
 * 实体读取器工厂方法
 *
 * @author liwei
 */
public class EntityReaderFactory {

    public static EntityReader create(String name, Map<String, Object> extendMap) {
        EntityReaderEnum entityReaderEnum = EntityReaderEnum.getEnum(name);

        return switch (entityReaderEnum) {
            case JSON_FILE -> new JsonFileEntityReader(
                    (String) extendMap.get(GeneratePropertyEnum.JSON_FILE.getName())
            );
            case JDBC -> new JdbcFileEntityReader(
                    (String) extendMap.get(GeneratePropertyEnum.URL.getName()),
                    (String) extendMap.get(GeneratePropertyEnum.USER.getName()),
                    (String) extendMap.get(GeneratePropertyEnum.PASSWORD.getName())
            );
        };

    }
}
