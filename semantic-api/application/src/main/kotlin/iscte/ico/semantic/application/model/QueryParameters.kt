package iscte.ico.semantic.application.model

data class QueryParameters (
        val entityType: QueryEntityType ,
        val entity: String ,
        val name: String ,
        val isOrderedBy: Boolean,
        val args: List<String>
        )