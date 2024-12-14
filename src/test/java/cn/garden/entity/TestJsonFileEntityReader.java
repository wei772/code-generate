package cn.garden.entity;

import cn.garden.entity.reader.implementation.JsonFileEntityReader;
import cn.garden.util.ResourceUtil;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestJsonFileEntityReader {

    @Test
    public void createReadJsonFileEntity() {
        List<Entity> entities = readJsonUserDefinition(
                getUserDefinition(),
                Arrays.asList("user", "user_group"));

        assertEquals(entities.size(), 2);
    }

    @Test
    public void createNullFileReadJsonFileEntity() {
        assertThrows(RuntimeException.class, () -> new JsonFileEntityReader(null));
    }

    private List<Entity> readJsonUserDefinition(String fileName, List<String> names) {
        JsonFileEntityReader jsonFileEntityReader = new JsonFileEntityReader(fileName);
        return jsonFileEntityReader.getEntities(names);
    }


    private String getUserDefinition() {
        String userDefinition = ResourceUtil.convertResourceToFullPath("entityGenerate/userDefinition.json");
        return Paths.get(userDefinition).toString();
    }


}
