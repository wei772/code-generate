package cn.garden.entity.reader;

import cn.garden.entity.Entity;

import java.util.List;

/**
 * 实体读取器
 *
 * @author liwei
 */
public abstract class EntityReader {

    public abstract List<Entity> getEntities(List<String> names);

}
