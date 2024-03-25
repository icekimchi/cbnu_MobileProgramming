package kr.easw.lesson02;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


@SpringBootApplication
public class Lesson02Example  {
    public static void main(String[] args) {
        new SpringApplicationBuilder(Lesson02Example.class)
                .registerShutdownHook(true)
                .run(args);
    }
}
