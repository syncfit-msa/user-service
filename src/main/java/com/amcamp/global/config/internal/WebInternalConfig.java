package com.amcamp.global.config.internal;

import com.amcamp.global.security.InternalTokenFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebInternalConfig implements WebMvcConfigurer {

    private final InternalTokenFilter internalTokenFilter;

    public WebInternalConfig(InternalTokenFilter internalTokenFilter) {
        this.internalTokenFilter = internalTokenFilter;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(internalTokenFilter)
                .addPathPatterns("/internal/**");
    }

}
