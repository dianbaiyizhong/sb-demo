package com.nntk.sb;

import cn.hutool.crypto.digest.MD5;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nntk.sb.mp.generate.entity.TAiCache;
import com.nntk.sb.mp.generate.mapper.TAiCacheMapper;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
public class AiController {
    @Autowired
    private OpenAiChatModel model;


    @Autowired
    private TAiCacheMapper aiCacheMapper;


//    @PostMapping("/chat/completions")
//    public Object chatCompletion(@RequestBody Map<String, Object> requestBody) {
//
//
//        JSONObject request = JSON.parseObject(JSON.toJSONString(requestBody));
//        String content = request.getJSONArray("messages").getJSONObject(0).getString("content");
//
//        String chat = model.chat(content);
//
//        System.out.println(chat);
//        return chat;
//    }


    @Autowired
    private OpenAIService openAIService;


    @PostMapping("/chat/completions")
    public Object getCompletion(@RequestBody Map<String, Object> requestBody) {
        JSONObject request = JSON.parseObject(JSON.toJSONString(requestBody));
        OpenAIRequest openAIRequest = new OpenAIRequest();
        openAIRequest.setMessages(request.get("messages"));
        openAIRequest.setModel(request.getString("model"));


        TAiCache aiCache = aiCacheMapper.selectOne(new QueryWrapper<TAiCache>()
                .lambda()
                .eq(TAiCache::getPromptMd5, MD5.create().digestHex(JSON.toJSONString(request.get("messages"))))
        );
        if (aiCache != null) {
            return JSON.parseObject(aiCache.getContent());
        }
        String completion = openAIService.getCompletion(requestBody);
        aiCache = new TAiCache();
        aiCache.setPromptMd5(MD5.create().digestHex(JSON.toJSONString(request.get("messages"))));
        aiCache.setPrompt(JSON.toJSONString(request.get("messages")));
        aiCache.setContent(completion);
        aiCacheMapper.insert(aiCache);
        return JSON.parseObject(completion);
    }

}
