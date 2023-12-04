package com.gestion.fibrolaser.configuracion;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ImgConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Agrega una ubicación base para servir archivos estáticos (imágenes en este caso)
        registry.addResourceHandler("/opt/**")
                .addResourceLocations("file:/opt/");

//        registry.addResourceHandler("/uploads/**")
//                .addResourceLocations("classpath:/static/uploads/");
    }
}