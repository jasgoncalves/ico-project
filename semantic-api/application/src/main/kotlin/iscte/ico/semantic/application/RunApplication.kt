package iscte.ico.semantic.application

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication(scanBasePackages = [
    "iscte.ico.*" ]
)
@ComponentScan("iscte.ico.semantic")
open class RunApplication

    fun main(args: Array<String>) {

        runApplication<RunApplication>(*args)
}

