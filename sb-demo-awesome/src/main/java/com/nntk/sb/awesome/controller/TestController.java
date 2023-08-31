package com.nntk.sb.awesome.controller;

import cn.hutool.extra.spring.SpringUtil;
import com.nntk.sb.awesome.config.EhcacheConfig;
import com.nntk.sb.awesome.service.DynamicDataSourceService;
import com.nntk.sb.awesome.service.EhcacheService;
import org.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private EhcacheService ehcacheService;

    @Autowired
    private EhcacheConfig ehcacheConfig;


    @Autowired
    private DynamicDataSourceService dynamicDataSourceService;


    @RequestMapping("/ehcache")
    public String ehcache() {

        ehcacheConfig.getCacheManager().put("0", "自定义缓存");
        for (int i = 0; i < 5000; i++) {
            String value = ehcacheService.getValue("" + i);
            System.out.println(value);
        }
        return "success";

    }


    @RequestMapping("/addDynamicDs")
    public String addDynamicDs() {


        dynamicDataSourceService.addDs();

        return "success";

    }


    @RequestMapping("/testDynamicDs")
    public String testDynamicDs() {


        dynamicDataSourceService.test();

        return "success";

    }

}
