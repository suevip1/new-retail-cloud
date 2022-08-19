package com.zhihao.newretail.api.file.config;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileFeignConfig {

    @Bean
    public Encoder encoder() {
        return new SpringFormEncoder();
    }

}
