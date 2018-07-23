package com.richard.service.utils;

import java.util.UUID;

public class RandomUtil {

    /**
     * 获取UUID
     * @return
     */
    public static String getUUID(){
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-","");
    }

}
