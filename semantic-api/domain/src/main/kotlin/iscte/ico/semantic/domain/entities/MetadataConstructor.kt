package iscte.ico.semantic.domain.entities

import iscte.ico.semantic.domain.entities.interfaces.Entity
import java.io.Serializable

data class MetadataConstructor(val name: String, val args: Any) : Entity , Serializable {

    override fun toString(): String =
        "{" +
        "\"name\"=\"$name\"," +
        "\"value\"=$args\"" +
        "}"
}
