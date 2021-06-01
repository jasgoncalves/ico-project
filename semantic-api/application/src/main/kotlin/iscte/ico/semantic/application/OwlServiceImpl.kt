package iscte.ico.semantic.application

import iscte.ico.semantic.application.interfaces.OntologyService
import iscte.ico.semantic.application.interfaces.OwlService
import iscte.ico.semantic.application.interfaces.SQWRLService
import iscte.ico.semantic.application.model.QueryParameters
import iscte.ico.semantic.application.model.QueryResult
import iscte.ico.semantic.domain.entities.*

class OwlServiceImpl(
        sqwrlService: SQWRLService,
        ontologyService : OntologyService)
    : OwlService{

    private var _sqwrlService: SQWRLService = sqwrlService
    private var _ontologyService : OntologyService = ontologyService

    override fun executeQuery(queryParameters : List<QueryParameters>) : QueryResult =
        _sqwrlService.query(queryParameters)

    override fun executeQuery(queryName : String) : QueryResult =
            _sqwrlService.query(queryName)

    override fun getRelationalOperator() : List<SwrlRelationalOperator> =
        _sqwrlService.getRelationalOperator()

    override fun getClasses() : List<OwlClass> =
        _ontologyService.getClasses()

    override fun getIndividuals() : List<OwlIndividual> =
        _ontologyService.getIndividuals()

    override fun getDatatypeProperties() : List<OwlDatatypeProperty> =
        _ontologyService.getDatatypeProperties()

    override fun getObjectProperties() : List<OwlObjectProperty> =
        _ontologyService.getObjectProperties()
}