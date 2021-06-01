package iscte.ico.semantic.application.interfaces

import iscte.ico.semantic.application.model.QueryParameters
import iscte.ico.semantic.application.model.QueryResult
import iscte.ico.semantic.domain.entities.*
import java.io.FileNotFoundException

interface OwlService {
    fun executeQuery(queryParameters : List<QueryParameters>) : QueryResult
    fun executeQuery(queryName : String) : QueryResult
    fun getRelationalOperator() : List<SwrlRelationalOperator>
    fun getClasses() : List<OwlClass>
    fun getIndividuals() : List<OwlIndividual>
    fun getDatatypeProperties() : List<OwlDatatypeProperty>
    fun getObjectProperties() : List<OwlObjectProperty>
}