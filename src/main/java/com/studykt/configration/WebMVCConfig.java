package com.studykt.configration;

import com.studykt.interceptor.AdminLoginInterceptor;
import com.studykt.interceptor.WxLoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedMethods("*")
                .maxAge(1800)
                .allowedOrigins("*");
    }

    /**
     * 添加静态资源地址
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/")
                .addResourceLocations("file:D:/Static/studykt/");
    }

    /**
     * 添加拦截器
     * addPathPatterns：注册要拦截的页面url
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(createWxLoginInterceptor())
                .addPathPatterns("/user/**")
                .addPathPatterns("/userCourseHistory/getUserCourseHistory")
                .addPathPatterns("/favorCourse/addFavorCourse", "/favorCourse/removeFavorCourse", "/favorCourse/allFavored")
                .addPathPatterns("/courseComment/addComment", "/courseComment/deleteComment")
                .addPathPatterns("/problem/getWrong", "/problem/getTestResult", "/problem/deleteTestResult",
                        "/problem/addTestResult", "/problem/addWrong");
        /*registry.addInterceptor(createAdminLoginInterceptor())
                .addPathPatterns("/admin/**");*/

    }

    @Bean
    public WxLoginInterceptor createWxLoginInterceptor() {
        return new WxLoginInterceptor();
    }

    @Bean
    public AdminLoginInterceptor createAdminLoginInterceptor() {
        return new AdminLoginInterceptor();
    }
}
