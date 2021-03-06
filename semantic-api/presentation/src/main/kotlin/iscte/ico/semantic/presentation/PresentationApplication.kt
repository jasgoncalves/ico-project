package iscte.ico.semantic.presentation

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication(scanBasePackages = [
    "iscte.ico.*" ,
    "iscte.ico.semantic.application.*"]
)
@ComponentScan("iscte.ico.semantic")
class PresentationApplication

    fun main(args: Array<String>) {

        runApplication<PresentationApplication>(*args)

}
