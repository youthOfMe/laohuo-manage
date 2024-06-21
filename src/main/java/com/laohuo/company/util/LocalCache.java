package com.laohuo.company.util;

import lombok.Data;

import java.util.HashMap;

/**
 * 本地缓存数据
 */
@Data
public class LocalCache {

    private static LocalCache localCache;

    private LocalCache() {

    }

    public static LocalCache getInstance() {
        if (localCache == null) localCache = new LocalCache();
        return localCache;
    }

    private HashMap<String, Object> cacheMap = new HashMap<>();
}
