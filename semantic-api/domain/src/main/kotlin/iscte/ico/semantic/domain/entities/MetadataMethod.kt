package iscte.ico.semantic.domain.entities

import iscte.ico.semantic.domain.entities.interfaces.Entity
import java.io.Serializable

data class MetadataMethod(val name: String, val args: Any) : Entity , Serializable {

    private val serialVersionUID : Long = 1L;

    override fun toString(): String =
        "{" +
        "\"name\"=\"$name\"," +
        "\"value\"=$args\"" +
        "}"
}
