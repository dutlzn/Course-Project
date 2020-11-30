package com.lzn.file.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public  class SpringMvcConfig extends WebMvcConfigurationSupport {

    @Value("${file.path}")
    private String FILE_PATH;

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/f/teacher/**")
                .addResourceLocations("file:" + FILE_PATH+ "/teacher/");

        super.addResourceHandlers(registry);
    }
}
