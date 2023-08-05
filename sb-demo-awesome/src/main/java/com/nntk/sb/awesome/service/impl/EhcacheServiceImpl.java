package com.nntk.sb.awesome.service.impl;

import cn.hutool.core.date.DateUtil;
import com.nntk.sb.awesome.service.EhcacheService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@CacheConfig(cacheNames = "diskCache")
public class EhcacheServiceImpl implements EhcacheService {
    @Override
    @Cacheable(key = "#key", cacheNames = "diskCache")
    public String getValue(String key) {
        return DateUtil.formatDateTime(new Date());
    }
}
