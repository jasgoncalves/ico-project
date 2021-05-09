package iscte.ico.semantic.domain.entities

import iscte.ico.semantic.domain.entities.interfaces.Entity
import java.io.Serializable

data class SwrlRelationalOperator(
        val id: String ,
        val label: String,
        val description: String
        ) : Entity, Serializable
