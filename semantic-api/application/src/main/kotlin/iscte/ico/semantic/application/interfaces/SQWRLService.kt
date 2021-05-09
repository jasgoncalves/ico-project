package iscte.ico.semantic.application.interfaces

import iscte.ico.semantic.application.model.QueryParameters
import iscte.ico.semantic.application.model.QueryResult
import iscte.ico.semantic.domain.entities.SwrlRelationalOperator
import java.io.FileNotFoundException

interface SQWRLService {

    @Throws(Exception::class)
    fun query(queryParameters : List<QueryParameters>) : QueryResult

    @Throws(FileNotFoundException::class)
    fun getRelationalOperator() : List<SwrlRelationalOperator>

}