package iscte.ico.semantic.presentation.helper

import iscte.ico.semantic.presentation.model.ErrorResponseModel
import iscte.ico.semantic.presentation.model.Error
import org.springframework.beans.TypeMismatchException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ErrorHandlerHelper: ResponseEntityExceptionHandler() {

    //region 400 - BadRequest

    override fun handleMethodArgumentNotValid(
            ex : MethodArgumentNotValidException , headers : HttpHeaders , status : HttpStatus , request : WebRequest
    ) : ResponseEntity<Any> =
            customizedErrorHandler(status , headers , ex)

    override fun handleBindException(
            ex : BindException , headers : HttpHeaders , status : HttpStatus , request : WebRequest
    ) : ResponseEntity<Any> =
            customizedErrorHandler(status, headers, ex)

    override fun handleTypeMismatch(
            ex : TypeMismatchException , headers : HttpHeaders , status : HttpStatus , request : WebRequest
    ) : ResponseEntity<Any> =
            customizedErrorHandler(status, headers, ex)

//    override fun handleMissingServletRequestPart(
//            ex : MissingServletRequestPartException , headers : HttpHeaders , status : HttpStatus , request : WebRequest
//    ) : ResponseEntity<Any> =
//            customizedErrorHandler(status, headers, ex)

//    override fun handleMissingServletRequestParameter(
//            ex : MissingServletRequestParameterException , headers : HttpHeaders , status : HttpStatus , request : WebRequest
//    ) : ResponseEntity<Any> =
//            customizedErrorHandler(status, headers, ex)

    //endregion

    //region 404 - NotFound

//    override fun handleNoHandlerFoundException(
//            ex : NoHandlerFoundException , headers : HttpHeaders , status : HttpStatus , request : WebRequest
//    ) : ResponseEntity<Any> =
//            customizedErrorHandler(status, headers, ex)


    //endregion

    //region 405 - MethodNotAllowed

//    override fun handleHttpRequestMethodNotSupported(
//            ex : HttpRequestMethodNotSupportedException , headers : HttpHeaders , status : HttpStatus , request : WebRequest
//    ) : ResponseEntity<Any> =
//            customizedErrorHandler(status, headers, ex)

    //endregion

    //region 415 - UnsupportedMediaType

//    override fun handleHttpMediaTypeNotSupported(
//            ex : HttpMediaTypeNotSupportedException , headers : HttpHeaders , status : HttpStatus , request : WebRequest
//    ) : ResponseEntity<Any> =
//            customizedErrorHandler(status, headers, ex)

    //endregion

    //region 500 - InternalErrorServer

    @ExceptionHandler(Exception::class)
    fun handleAll(e : Exception , request : WebRequest?) : ResponseEntity<*> {
        val httpStatus = HttpStatus.INTERNAL_SERVER_ERROR
        return customizedErrorHandler( httpStatus, HttpHeaders(), e)
    }

    //endregion

    private fun customizedErrorHandler(httpStatus : HttpStatus, httpHeaders: HttpHeaders, errorException : Exception) : ResponseEntity<Any> =
        ResponseEntity<Any>(
            ErrorResponseModel(
                Error(httpStatus.value(),
                    httpStatus.toString(),
                    errorException.localizedMessage.toString() )
            ) ,
            httpHeaders,
            httpStatus)
}