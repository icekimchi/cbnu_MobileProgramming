package kr.easw.lesson05;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class Lesson05Example {
    public static void main(String[] args) {
        new SpringApplicationBuilder(Lesson05Example.class)
                .registerShutdownHook(true)
                .run(args);
    }
}
