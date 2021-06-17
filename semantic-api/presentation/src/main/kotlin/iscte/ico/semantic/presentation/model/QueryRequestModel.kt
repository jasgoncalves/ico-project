package iscte.ico.semantic.presentation.model

import com.fasterxml.jackson.annotation.JsonProperty

class QueryRequestModel(
    val name : String,
    @JsonProperty("query_parameters")
    val queryParameters : List<QueryParamatersModel>)
