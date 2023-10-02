package com.nntk.sb.controller;

import cn.hutool.core.io.resource.ResourceUtil;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class ApiController {


    @RequestMapping(value = "/list/{id}", method = RequestMethod.GET)
    public Object list(@PathVariable("id") Integer id) {
        log.info("====id:{}", id);
        return JSON.parseObject(ResourceUtil.readStr("test.json", Charset.defaultCharset()));
    }


    @PostMapping("login")
    public Object login(@RequestBody Map<String, String> param) {
        log.info("=====login:{}", param);
        Map<String, Object> ret = new HashMap<>();
        ret.put("code", 0);
        ret.put("message", "success");
        return ret;
    }
}
