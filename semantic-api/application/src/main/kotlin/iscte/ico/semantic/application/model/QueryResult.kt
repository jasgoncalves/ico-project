package iscte.ico.semantic.application.model

import java.util.*

data class QueryResult(val result : List<Dictionary<String,String>>, val rows : Integer, val swrlquery : String )
