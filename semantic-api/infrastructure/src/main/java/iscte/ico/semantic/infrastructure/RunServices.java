package iscte.ico.semantic.infrastructure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "iscte.ico.semantic")
public class RunServices {

    public static void main(String[] args) {
        SpringApplication.run(RunServices.class, args);
    }
}
