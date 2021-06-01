package iscte.ico.semantic.presentation.model

import java.io.Serializable

data class Error(
    val statusCode: Int,
    val statusDescription: String,
    val description: String) : Serializable
