package br.com.rafaellins.reunioes.api.global;

import br.com.rafaellins.reunioes.api.response.ApiError;
import br.com.rafaellins.reunioes.api.response.ApiErrorValidationDescription;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Log4j2
@RestControllerAdvice
public class GlobalApiExceptionHandler extends ResponseEntityExceptionHandler {
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
}
