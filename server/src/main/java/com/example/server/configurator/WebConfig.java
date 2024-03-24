package com.example.server.configurator;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    /**
     * Adds cors mapping to be used to test application on local machine.
     * @param registry
     */
    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET",
                                "POST",
                                "PUT",
                                "DELETE",
                                "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
