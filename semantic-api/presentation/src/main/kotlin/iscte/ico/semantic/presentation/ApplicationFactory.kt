package iscte.ico.semantic.presentation

import iscte.ico.semantic.application.OwlServiceImpl
import iscte.ico.semantic.application.interfaces.OntologyService
import iscte.ico.semantic.application.interfaces.OwlService
import iscte.ico.semantic.application.interfaces.SQWRLService
import iscte.ico.semantic.infrastructure.services.OntologyServiceImpl
import iscte.ico.semantic.infrastructure.services.SQWRLServiceImpl
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApplicationFactory {

    @Bean
    fun ontologyService() : OntologyService =
            OntologyServiceImpl(logger())

    @Bean
    fun sqwrlService() : SQWRLService =
            SQWRLServiceImpl(logger(), ontologyService()).run()

    @Bean
    fun logger() : Logger =
            LoggerFactory.getLogger(ApplicationFactory::class.java)

    @Bean
    fun owlService() : OwlService =
            OwlServiceImpl(sqwrlService(), ontologyService())
}