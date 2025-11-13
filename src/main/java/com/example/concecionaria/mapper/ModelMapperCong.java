package com.example.concecionaria.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperCong {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
