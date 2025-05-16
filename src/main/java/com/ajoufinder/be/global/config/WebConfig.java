package com.ajoufinder.be.global.config;

import com.ajoufinder.be.global.converter.MultipartJackson2HttpMessageConverter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final MultipartJackson2HttpMessageConverter multipartJackson2HttpMessageConverter;

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(multipartJackson2HttpMessageConverter);
    }
}
