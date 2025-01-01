package cn.garden.generate.util;

import cn.garden.generate.entity.Entity;
import cn.garden.generate.util.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author liwei
 */
public class TestJsonUtil {

    @Test
    public void errorToObject() {
        assertThrows(RuntimeException.class, () -> JsonUtil.toObject("{", Entity.class));
    }

    @Test
    public void errorToObjectWithTypeReference() {
        assertThrows(RuntimeException.class, () -> JsonUtil.toObject("""
                {
                    "sourceName": "user",
                    "description": "用户"}""", new TypeReference<List<Entity>>() {
        }));
    }
}
