package com.richard.service.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mysql.jdbc.StringUtils;

/**
 * depend on jackson
 * @author Diamond
 */
public class CustomerJsonSerializer {


    ObjectMapper mapper = new ObjectMapper();
    JacksonJsonFilter jacksonFilter = new JacksonJsonFilter();

    /**
     * @param clazz target type
     * @param include include fields
     * @param filter filter fields
     */
    public void filter(Class<?> clazz, String include, String filter) {
        if (clazz == null) return;
        if (!StringUtils.isNullOrEmpty(include)) {
            jacksonFilter.include(clazz, include.split(","));
        }
        if (!StringUtils.isNullOrEmpty(filter)) {
            jacksonFilter.filter(clazz, filter.split(","));
        }
        //空字段，不参与序列化
//        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.addMixIn(clazz, jacksonFilter.getClass());
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    public String toJson(Object object) throws JsonProcessingException {
        mapper.setFilterProvider(jacksonFilter);
        return mapper.writeValueAsString(object);
    }
    public void filter(JSON json) {
        this.filter(json.type(), json.include(), json.filter());
    }
}
