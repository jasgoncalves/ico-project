package iscte.ico.semantic.application.interfaces

import com.fasterxml.jackson.core.JsonProcessingException
import iscte.ico.semantic.application.model.Query
import iscte.ico.semantic.application.model.QueryParameters
import java.io.IOException
import java.util.*

interface DatabaseService {
    fun getQuery(queryID: UUID) : Query
    fun getQueries() : List<Query>
    @Throws(JsonProcessingException::class)
    fun createQuery(name: String, queryParameters: List<QueryParameters>)
    @Throws(JsonProcessingException::class)
    fun updateQuery(queryID: UUID, name: String, queryParameters: List<QueryParameters>)
    fun deleteQuery(queryID: UUID)
}