package iscte.ico.semantic.application.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import lombok.Data

@Data
class QueryParameters {

    val entityType: QueryEntityType
    val entity: String
    val name: String
    val isOrderedBy: Boolean
    val isColumnShowed: Boolean
    val args: List<String>

        @JsonCreator
        constructor(
                @JsonProperty("entityType") entityType: QueryEntityType ,
                @JsonProperty("entity") entity: String ,
                @JsonProperty("name") name: String ,
                @JsonProperty("orderedBy") isOrderedBy: Boolean ,
                @JsonProperty("columnShowed") isColumnShowed: Boolean ,
                @JsonProperty("args") args: List<String>
        )
        {
                this.entityType = entityType
                this.entity = entity
                this.name = name
                this.isOrderedBy = isOrderedBy
                this.isColumnShowed = isColumnShowed
                this.args = args
        }
}