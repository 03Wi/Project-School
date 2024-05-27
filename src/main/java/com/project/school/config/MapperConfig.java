package com.project.school.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    //Si es necesario mapear explicitamente un atributo al DTO

    @Bean("defaultMapper")
    public ModelMapper mapper(){
        return new ModelMapper();
    }
}
