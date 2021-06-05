package iscte.ico.semantic.presentation

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = arrayOf("iscte.ico.semantic"))
class PresentationApplication

    fun main(args: Array<String>) {

        runApplication<PresentationApplication>(*args)

}

