package cn.garden.generate.entity.reader.implementation;

import cn.garden.generate.entity.Entity;
import cn.garden.generate.entity.reader.EntityReader;
import cn.garden.generate.util.ExceptionUtil;
import cn.garden.generate.util.FileUtil;
import cn.garden.generate.util.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 从json文件读取实体列表
 *
 * @author liwei
 */
public class JsonFileEntityReader extends EntityReader {

    private final String fileName;

    public JsonFileEntityReader(String fileName) {
        if (StringUtils.isEmpty(fileName)) {
            throw ExceptionUtil.createDefaultException("JsonFileEntityReader的fileName不能为空");
        }
        this.fileName = fileName;
    }

    public List<Entity> getEntities(List<String> names) {
        String jsonReader = FileUtil.readFileToString(fileName);
        List<Entity> allList = JsonUtil.toObject(jsonReader, new TypeReference<>() {});

        if (CollectionUtils.isEmpty(names)) {
            return allList;
        }
        return allList.stream().filter(m -> names.contains(m.getSourceName())).toList();
    }

}
