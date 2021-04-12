package iscte.ico.semantic.domain.entities

import iscte.ico.semantic.domain.entities.interfaces.Entity
import java.io.Serializable

data class Problem(
       val name : String ,
       val objectPurpose : String ,
       val constructors : ArrayList<MetadataConstructor>? = ArrayList() ,
       val methods : ArrayList<MetadataMethod>? = ArrayList()
) : Entity , Serializable {

    override fun toString(): String =
        "{" +
        "\"name\"=\"$name\"," +
        "\"constructors\"=$constructors\"," +
        "\"methods\"=\"$methods\", " +
        "\"objectivePurpose\"=\"$objectPurpose\"" +
        "}"

}