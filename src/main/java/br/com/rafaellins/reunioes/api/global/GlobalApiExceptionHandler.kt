//package br.com.rafaellins.reunioes.api.global
//
//import ai.responde.backend.questionnaries.model.response.ApiError
//import ai.responde.backend.questionnaries.model.response.ValidationApiError
//import ai.responde.backend.questionnaries.model.response.ValidationApiErrorDescription
//import ai.responde.backend.questionnaries.util.logger
//import com.fasterxml.jackson.databind.exc.MismatchedInputException
//import org.slf4j.Logger
//import org.springframework.http.HttpHeaders
//import org.springframework.http.HttpStatus
//import org.springframework.http.HttpStatus.BAD_REQUEST
//import org.springframework.http.ResponseEntity
//import org.springframework.http.converter.HttpMessageNotReadableException
//import org.springframework.web.bind.MethodArgumentNotValidException
//import org.springframework.web.bind.annotation.ExceptionHandler
//import org.springframework.web.bind.annotation.RestControllerAdvice
//import org.springframework.web.context.request.WebRequest
//import org.springframework.web.servlet.NoHandlerFoundException
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
//
///**
// * Intercepts exceptions thrown by API invocations and turns them in [ApiError] responses.
// */
//@RestControllerAdvice
//class GlobalApiExceptionHandler : ResponseEntityExceptionHandler() {
//    companion object {
//        val log: Logger = logger()
//    }
//
//    override fun handleExceptionInternal(
//        ex: Exception,
//        body: Any?,
//        headers: HttpHeaders,
//        status: HttpStatus,
//        request: WebRequest
//    ): ResponseEntity<Any> =
//        if (ex is NoHandlerFoundException) {
//            ApiError.of(status)
//        } else {
//            ApiError.of(ex, status)
//        }.let { ResponseEntity.status(it.status).body(it) }
//
//    override fun handleHttpMessageNotReadable(
//        ex: HttpMessageNotReadableException,
//        headers: HttpHeaders,
//        status: HttpStatus,
//        request: WebRequest
//    ): ResponseEntity<Any> =
//        log.warn("process=error_handling, status=http_message_not_readable, message={}", ex.localizedMessage)
//            .let {
//                val cause = ex.cause
//                val payload = when (cause is MismatchedInputException) {
//                    true -> ValidationApiError(
//                        status = BAD_REQUEST.value(),
//                        code = cause.targetType?.simpleName ?: BAD_REQUEST.name,
//                        message = cause.originalMessage?.takeIf { it.isNotBlank() } ?: BAD_REQUEST.reasonPhrase,
//                        validationErrors = listOf(ValidationApiErrorDescription.of(cause))
//                    )
//                    false -> ApiError.of(cause ?: ex, BAD_REQUEST)
//                }
//
//                return ResponseEntity
//                    .status(BAD_REQUEST)
//                    .body(payload)
//            }
//
//    override fun handleMethodArgumentNotValid(
//        ex: MethodArgumentNotValidException,
//        headers: HttpHeaders,
//        status: HttpStatus,
//        request: WebRequest
//    ): ResponseEntity<Any> =
//        log.warn("process=error_handling, status=method_argument_not_valid")
//            .let {
//                val errors = ex.bindingResult.allErrors.map {
//                    ValidationApiErrorDescription.of(it)
//                }.sortedBy { it.field }
//
//                return ResponseEntity
//                    .status(BAD_REQUEST)
//                    .body(ValidationApiError.of(errors))
//            }
//
//    @ExceptionHandler(Throwable::class)
//    fun handleThrowable(throwable: Throwable): ResponseEntity<ApiError> =
//        log.error("process=error_handling, status=unexpected_error", throwable)
//            .let { ApiError.of(throwable) }
//            .let { ResponseEntity.status(it.status).body(it) }
//}
