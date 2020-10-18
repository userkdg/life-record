//package cn.com.bluemoon.dap.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
///**
// * @author Jarod.Kong
// * @date 2020/8/18 11:09
// */
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedHeaders("*")
//                .allowedMethods("POST", "GET", "PUT", "DELETE")
//                .allowedOrigins("*")
//                .allowCredentials(true)
//                .maxAge(3600);
//    }
//}
