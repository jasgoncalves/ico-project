package iscte.ico.semantic.presentation

import iscte.ico.semantic.presentation.controllers.AlgorithmsController
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext

@SpringBootApplication
class PresentationApplication

fun main(args: Array<String>) {

    runApplication<PresentationApplication>(*args)

}
