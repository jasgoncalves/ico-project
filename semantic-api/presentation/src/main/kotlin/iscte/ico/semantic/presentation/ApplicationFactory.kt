package iscte.ico.semantic.presentation

import iscte.ico.semantic.application.interfaces.OwlService
import iscte.ico.semantic.infrastructure.services.OwlServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApplicationFactory {

    @Bean
    fun owlService() : OwlService = OwlServiceImpl()

}