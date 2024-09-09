package com.braulioneta.gestionHotelera.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;



@Configuration
public class CloudinaryConfig {
    @Bean
    Cloudinary cloudinary(){
        return new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dgxal0lua",
                "api_key", "535878741365659",
                "api_secret", "tCHJ2CAnStqjbMxBkYRceH51kD0"
        ));
    }
}