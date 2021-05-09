package iscte.ico.semantic.presentation.model

import com.fasterxml.jackson.annotation.JsonProperty
import iscte.ico.semantic.application.model.QueryEntityType

data class QueryParamatersModel(
        @JsonProperty("entity_type") val entityType: QueryEntityType ,
        @JsonProperty("entity") val entity: String ,
        @JsonProperty("name") val name: String ,
        @JsonProperty("is_ordered_by")  val isOrderedBy: Boolean,
        @JsonProperty("args") val args: List<String>)
