package com.quizit.api.global.exception

import com.quizit.api.global.dto.ErrorResponse
import com.quizit.core.global.exception.ServerException
import com.quizit.core.global.extension.getLogger
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@RestControllerAdvice(basePackages = ["com.quizit.api"])
class GlobalExceptionHandler {
    private companion object {
        const val INVALID_JSON_MESSAGE = "JSON 형식이 올바르지 않습니다."
        const val INTERNAL_SERVER_ERROR_CODE = "INTERNAL_SERVER_ERROR"
        const val INTERNAL_SERVER_ERROR_MESSAGE = "서버 오류가 발생했습니다."
        const val EXCEPTION_SUFFIX = "_EXCEPTION"
        val ERROR_CODE_REGEX = Regex("([a-z])([A-Z])")
    }

    private val logger = getLogger()

    @ExceptionHandler(ServerException::class)
    fun handle(exception: ServerException): ResponseEntity<ErrorResponse> {
        logger.warn { exception.message }

        return ResponseEntity
            .status(exception.status)
            .body(
                ErrorResponse(
                    code = exception.errorCode,
                    message = exception.message
                )
            )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handle(exception: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> =
        handle(
            InvalidRequestException(
                exception.bindingResult
                    .fieldErrors
                    .joinToString(", ") { "${it.field}: ${it.defaultMessage}" }
            )
        )

    @ExceptionHandler(ConstraintViolationException::class)
    fun handle(exception: ConstraintViolationException): ResponseEntity<ErrorResponse> =
        handle(
            InvalidRequestException(
                exception.constraintViolations
                    .joinToString(", ") { "${it.propertyPath.last()}: ${it.message}" }
            )
        )

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(): ResponseEntity<ErrorResponse> =
        handle(InvalidRequestException(INVALID_JSON_MESSAGE))

    @ExceptionHandler(
        HttpRequestMethodNotSupportedException::class,
        MethodArgumentTypeMismatchException::class
    )
    fun handleInvalidRequest(): ResponseEntity<ErrorResponse> = handle(InvalidRequestException())

    @ExceptionHandler(Exception::class)
    fun handle(exception: Exception): ResponseEntity<ErrorResponse> {
        logger.error(exception) { exception.message }

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ErrorResponse(
                    code = INTERNAL_SERVER_ERROR_CODE,
                    message = INTERNAL_SERVER_ERROR_MESSAGE
                )
            )
    }

    private val ServerException.errorCode
        get() =
            this::class.simpleName!!
                .replace(ERROR_CODE_REGEX, "$1_$2")
                .uppercase()
                .removeSuffix(EXCEPTION_SUFFIX)
}
