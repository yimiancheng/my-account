package com.yimian.util;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

@Slf4j
public class JsonUtils {
    private static ObjectMapper mapper = new ObjectMapper();

    public static <T> T fronJson(String json, Class<T> clazz) throws Exception {
        return mapper.readValue(json, clazz);
    }

    /**
     * 对象转化为json
     */
    public static String toJson(Object obj) throws Exception {
        return mapper.writeValueAsString(obj);
    }

    public static <T> List<T> fromJson(String json, Class<T> clazz) throws Exception {
        JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, clazz);
        List<T> listEntity = mapper.readValue(json, javaType);
        return listEntity;
    }
}
