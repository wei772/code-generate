package cn.garden.util;

import cn.garden.entity.Entity;
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
