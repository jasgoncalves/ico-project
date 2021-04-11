package iscte.ico.semantic.presentation.model

import java.io.Serializable

data class ErrorModel(val error: Exception) : Serializable {

    val serialVersionUID: Long = 1L;
    val code: Int = error.hashCode()
    val description: String? = error.message

}