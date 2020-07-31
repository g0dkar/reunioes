package br.com.rafaellins.reunioes.api.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
public class ApiError {

    /**
     * Error HTTP Code
     */
    int status;

    /**
     * Error Message
     */
    String message;

    /**
     * A code for this error (to be easily interpreted by software)
     */
    String code;

    /**
     * When this error occurred
     */
    final OffsetDateTime timestamp = OffsetDateTime.now();

    /**
     * Extra data to send with the response (e.g. validation errors)
     */
    List<ApiErrorValidationDescription> validationErrors;

    public static ApiError of(Throwable exception, HttpStatus status) {
        return of(exception, status, null);
    }

    public static ApiError of(Throwable exception, HttpStatus status, String message) {
        if (status == null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        if (message == null) {
            message = exception.getMessage() != null ? exception.getMessage() : exception.getClass().getSimpleName();
        }

        HttpStatus actualStatus;

        try {
            ResponseStatus responseCodeAnnotation = exception.getClass().getAnnotation(ResponseStatus.class);

            if (responseCodeAnnotation != null) {
                actualStatus = responseCodeAnnotation.code() != HttpStatus.INTERNAL_SERVER_ERROR ?
                    responseCodeAnnotation.code() : responseCodeAnnotation.value();
            } else {
                if (ResponseStatusException.class.isAssignableFrom(exception.getClass())) {
                    actualStatus = ((ResponseStatusException) exception).getStatus();
                } else {
                    actualStatus = status;
                }
            }
        } catch (Exception e) {
            actualStatus = status;
        }

        return ApiError.builder()
            .status(actualStatus.value())
            .message(message)
            .code(exception.getMessage() == null ? actualStatus.name() : exception.getClass().getSimpleName())
            .build();
    }

    /*
    /**
     * Builds a [ValidationApiErrorDescription] of a [ObjectError].
     * /
    fun of(error: ObjectError): ValidationApiErrorDescription =
                ValidationApiErrorDescription(
                    field = when (error is FieldError) {
                    true -> error.field
                    false -> buildField(error)
                },
                type = error.code,
                message = error.defaultMessage
            )

    /**
     * Builds a [ValidationApiErrorDescription] of a [MismatchedInputException].
     * /
    fun of(inputException:MismatchedInputException): ValidationApiErrorDescription =
    ValidationApiErrorDescription(
        inputException.path.joinToString("") {
        if (it.fieldName != null) {
            ".${it.fieldName}"
        } else {
            "[${it.index}]"
        }
    }.substring(1),
    inputException.targetType?.simpleName,
    inputException.originalMessage ?: ""
        )

    /**
     * Builds a field name of an [ObjectError].
     * /
    private fun buildField(objectError: ObjectError): String {
        val code = objectError.code
        val codes = objectError.codes

        return if (code != null) {
            codes?.get(2)?.substring(code.length + 1)
                ?: codes?.get(0)?.substring(code.length + 1)
                ?: ""
        } else {
            codes?.get(0) ?: ""
        }
    }
     */
}
