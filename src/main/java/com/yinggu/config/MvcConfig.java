package com.yinggu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //设置允许跨域的路径
        registry.addMapping("/**")
                //设置允许跨域请求的域名
                .allowedOrigins("*")
                .allowedHeaders("*")
//                .allowCredentials(true)//是否允许证书 不再默认开启
                //设置允许的方法
                .allowedMethods("GET", "POST", "PUT", "DELETE");
//                .maxAge(3600);//跨域允许时间
    }

    @Bean
    public BufferedImageHttpMessageConverter bufferedImageHttpMessageConverter(){
        return new BufferedImageHttpMessageConverter();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtIntercepter())
                .addPathPatterns("/**")
                .excludePathPatterns("/login")
                .excludePathPatterns("/register")
                .excludePathPatterns("/adminlogin")
                .excludePathPatterns("/get/*")
                .excludePathPatterns("/getImgProduct/*")
                .excludePathPatterns("/download1/*")
                .excludePathPatterns("/download2/*")
                .excludePathPatterns("/download3/*");
    }
}

