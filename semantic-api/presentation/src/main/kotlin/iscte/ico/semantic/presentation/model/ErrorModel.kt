package iscte.ico.semantic.presentation.model

import java.io.Serializable

data class ErrorModel(
    val statusCode: Int,
    val statusDescription: String,
    val description: String,
    var currentTime : Long? = System.currentTimeMillis()) : Serializable