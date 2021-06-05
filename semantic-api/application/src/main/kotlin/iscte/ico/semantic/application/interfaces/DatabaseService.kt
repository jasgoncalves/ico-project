package iscte.ico.semantic.application.interfaces

import iscte.ico.semantic.application.model.Query
import iscte.ico.semantic.application.model.QueryParameters
import java.util.*

interface DatabaseService {
    fun getQuery(queryID: UUID) : Query
    fun getQueries() : List<Query>
    fun createQuery(name: String, queryParameters: List<QueryParameters>)
}