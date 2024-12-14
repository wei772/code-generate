package cn.garden.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;

/**
 * Json工具类
 */
public class JsonUtil {
    static JsonMapper MAPPER = new JsonMapper();

    public static <T> T toObject(String jsonString, TypeReference<T> typeReference) {
        try {
            return MAPPER.readValue(jsonString, typeReference);
        } catch (JsonProcessingException e) {
            throw ExceptionUtil.createDefaultException("json反序列化对象失败", e);
        }
    }

    public static <T> T toObject(String jsonString, Class<T> className) {
        try {
            return MAPPER.readValue(jsonString, className);
        } catch (JsonProcessingException e) {
            throw ExceptionUtil.createDefaultException("json反序列化对象失败", e);
        }
    }
}
