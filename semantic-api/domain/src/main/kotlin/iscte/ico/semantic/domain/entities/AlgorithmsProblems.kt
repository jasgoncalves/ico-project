package iscte.ico.semantic.domain.entities

import iscte.ico.semantic.domain.entities.interfaces.Entity
import java.io.Serializable

data class AlgorithmsProblems(
    val algorithms : MutableList<Algorithm>,
    val problems : MutableList<Problem>
) : Entity , Serializable {

    private val serialVersionUID : Long = 1L;

    override fun toString(): String =
    "{" +
    "\"algorithms\"=\"$algorithms\"," +
    "\"problems\"=\"$problems\"" +
    "}"

}