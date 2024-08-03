package com.scm.SCM.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Cloudinary cloudinary(){
        return new Cloudinary(
                ObjectUtils.asMap(
                        "cloud_name","donreymbr",
        "api_key","228643745167511",
        "api_secret","1NcGsjLKoFvQjiqr81tfD7wVDBU"
                )
        );
    }
}
