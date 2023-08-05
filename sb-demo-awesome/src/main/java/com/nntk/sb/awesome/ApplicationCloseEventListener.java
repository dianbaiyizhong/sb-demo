package com.nntk.sb.awesome;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

@Component
public class ApplicationCloseEventListener implements ApplicationListener<ContextClosedEvent> {
//    @Autowired
//    CacheManager cacheManager;

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println("===");
//        cacheManager.shutdown();
    }
}
