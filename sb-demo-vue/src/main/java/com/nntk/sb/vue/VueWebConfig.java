package com.nntk.sb.vue;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@Configuration
public class VueWebConfig implements WebMvcConfigurer {
    /**
     * 映射的静态资源路径
     * file:./static/ 路径是相对于 user.dir 路径，jar 包同级目录下的 static
     */
    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {"file:./static/", "classpath:/META-INF/resources/",
            "classpath:/resources/", "classpath:/static/", "classpath:/public/"};

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 添加静态资源缓存
        CacheControl cacheControl = CacheControl.maxAge(5, TimeUnit.HOURS).cachePublic();
        registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS).setCacheControl(cacheControl);
    }


}
