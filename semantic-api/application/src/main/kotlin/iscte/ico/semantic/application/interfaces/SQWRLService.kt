package iscte.ico.semantic.application.interfaces

import iscte.ico.semantic.application.model.QueryParameters
import iscte.ico.semantic.application.model.QueryResult
import iscte.ico.semantic.domain.entities.SwrlRelationalOperator
import java.io.IOException

interface SQWRLService {

    @Throws(Exception::class)
    fun query(queryParameters : List<QueryParameters>) : QueryResult

    @Throws(Exception::class)
    fun query(queryName : String ) : QueryResult

    @Throws(IOException::class)
    fun getRelationalOperator() : List<SwrlRelationalOperator>

    fun run()

}