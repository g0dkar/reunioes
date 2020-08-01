package br.com.rafaellins.reunioes.api.global;

import br.com.rafaellins.reunioes.api.response.ApiError;
import br.com.rafaellins.reunioes.api.response.ApiErrorValidationDescription;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Log4j2
@RestControllerAdvice
public class GlobalApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ApiError> handleThrowable(Throwable throwable) {
        log.error("process=error_handling, status=unexpected_error", throwable);

        ApiError payload = ApiError.of(throwable, INTERNAL_SERVER_ERROR);

        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(payload);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        ApiError payload;

        if (ex instanceof NoHandlerFoundException) {
            payload = ApiError.builder()
                .status(status.value())
                .message(status.getReasonPhrase())
                .code(status.name())
                .build();
        } else {
            payload = ApiError.of(ex, status);
        }

        return ResponseEntity.status(status).body(payload);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        log.warn("process=error_handling, status=http_message_not_readable, message={}", ex.getMessage());

        ApiError payload;

        Throwable cause = ex.getCause();

        if (cause instanceof MismatchedInputException) {
            MismatchedInputException mie = (MismatchedInputException) cause;

            payload = ApiError.builder()
                .status(BAD_REQUEST.value())
                .message(BAD_REQUEST.getReasonPhrase())
                .code(BAD_REQUEST.name())
                .validationErrors(List.of(ApiErrorValidationDescription.builder()
                    .field(mie.getPathReference())
                    .type(mie.getTargetType().getSimpleName())
                    .message(mie.getOriginalMessage())
                    .build()))
                .build();
        } else {
            payload = ApiError.of(cause != null ? cause : ex, BAD_REQUEST);
        }

        return ResponseEntity.badRequest().body(payload);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        log.warn("process=error_handling, status=method_argument_not_valid");

        List<ApiErrorValidationDescription> validationErrors = ex.getBindingResult().getAllErrors().stream()
            .map(error -> ApiErrorValidationDescription.builder()
                .field(buildField(error))
                .type(error.getCode())
                .message(error.getDefaultMessage())
                .build())
            .collect(toList());

        ApiError payload = ApiError.builder()
            .status(BAD_REQUEST.value())
            .message(BAD_REQUEST.getReasonPhrase())
            .code(BAD_REQUEST.name())
            .validationErrors(validationErrors)
            .build();

        return ResponseEntity.badRequest().body(payload);
    }

    /**
     * Gets the field from an {@link ObjectError}.
     */
    private String buildField(ObjectError error) {
        if (error instanceof FieldError) {
            return ((FieldError) error).getField();
        } else {
            String[] codes = error.getCodes();

            if (codes != null && codes.length > 0) {
                String code = codes[codes.length - 1];

                if (codes.length >= 3) {
                    return codes[2].substring(code.length() + 1);
                } else {
                    return codes[0].substring(code.length() + 1);
                }
            }
        }

        return null;
    }
}
