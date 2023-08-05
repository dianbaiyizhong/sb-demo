package com.nntk.sb.awesome.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.cache.Cache;

@Component
public class EhcacheConfig {

    @Autowired
    private CacheManager cacheManager;

    public Cache getCacheManager() {
        return cacheManager.getCache("diskCache");
    }
}
