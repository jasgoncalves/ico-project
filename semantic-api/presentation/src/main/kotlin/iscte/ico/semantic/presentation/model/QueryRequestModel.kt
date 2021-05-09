package iscte.ico.semantic.presentation.model

import com.fasterxml.jackson.annotation.JsonProperty

class QueryRequestModel(@JsonProperty("query_parameters") val queryParameters : List<QueryParamatersModel>)
