package iscte.ico.semantic.presentation.model

import java.io.Serializable

class ResponseModel<T>(val data :  T? , val error : ErrorModel? ):Serializable{

    val serialVersionUID: Long = 1L;

}
