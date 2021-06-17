package iscte.ico.semantic.domain.entities

import iscte.ico.semantic.domain.entities.interfaces.Entity
import java.io.Serializable

class OwlObjectProperty(
        val id: String ,
        val label: String,
        val domainId: String?,
        val domainLabel: String?,
        val rangeId: String?,
        val uri: String,
        val description: String?
) : Entity , Serializable