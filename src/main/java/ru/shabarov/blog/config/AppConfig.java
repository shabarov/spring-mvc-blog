package ru.shabarov.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
//@PropertySource("classpath:blog.properties")
public class AppConfig {

    @Bean
    public MyBean myBean() {
        return new MyBean("MyBean property");
    }

//    @Bean
//    public static PropertySourcesPlaceholderConfigurer propertyConfigIn() {
//        return new PropertySourcesPlaceholderConfigurer();
//    }
}
