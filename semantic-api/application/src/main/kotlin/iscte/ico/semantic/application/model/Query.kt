package iscte.ico.semantic.application.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import lombok.Data
import java.util.*

@Data
class Query{

    val id: UUID
    val name: String
    val queryParameters : Array<QueryParameters>

    @JsonCreator
    constructor(
        @JsonProperty("id") id: UUID,
        @JsonProperty("name") name: String,
        @JsonProperty("query_parameters") queryParameters : Array<QueryParameters>
    ){
        this.id = id
        this.name = name
        this.queryParameters = queryParameters
    }
}
