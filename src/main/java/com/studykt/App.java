package com.studykt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(scanBasePackages = {"com.studykt", "org.n3r.idworker"})
@MapperScan(basePackages = {"com.studykt.mapper"})
public class App {
    public static void main( String[] args) {
        SpringApplication.run(App.class, args);
    }
}
