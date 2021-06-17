package iscte.ico.semantic.application.interfaces

import iscte.ico.semantic.application.model.QueryParameters
import iscte.ico.semantic.application.model.QueryResult
import iscte.ico.semantic.application.model.SchedulingProblemRequest
import iscte.ico.semantic.domain.entities.*
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

interface OwlService {
    @Throws(Exception::class)
    fun executeQuery(queryParameters : List<QueryParameters>) : QueryResult
    @Throws(Exception::class)
    fun executeQuery(queryName : String) : QueryResult
    @Throws(IOException::class)
    fun getRelationalOperator() : List<SwrlRelationalOperator>
    fun getClasses() : List<OwlClass>
    fun getIndividuals() : List<OwlIndividual>
    fun getDatatypeProperties() : List<OwlDatatypeProperty>
    fun getObjectProperties() : List<OwlObjectProperty>
    @Throws(IOException::class)
    fun addSchedulingProblemIndividual(schedulingProblemRequest : SchedulingProblemRequest?) : ByteArray
}