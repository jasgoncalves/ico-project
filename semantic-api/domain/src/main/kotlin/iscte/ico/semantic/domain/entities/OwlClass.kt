package iscte.ico.semantic.domain.entities

import iscte.ico.semantic.domain.entities.interfaces.Entity
import java.io.Serializable

data class OwlClass (
        val id: String ,
        val label: String,
        val uri: String,
        val description: String?
        ) : Entity , Serializable
