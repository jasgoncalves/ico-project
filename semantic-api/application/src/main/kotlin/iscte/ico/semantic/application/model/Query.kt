package iscte.ico.semantic.application.model

import java.util.*

data class Query(
    val id: UUID,
    val name: String,
    val queryParameters : Any
)
