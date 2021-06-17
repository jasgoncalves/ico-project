package iscte.ico.semantic.infrastructure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("iscte.ico.semantic")
public class RunServices {

    public static void main(String[] args) {
        SpringApplication.run(RunServices.class, args);
    }
}
