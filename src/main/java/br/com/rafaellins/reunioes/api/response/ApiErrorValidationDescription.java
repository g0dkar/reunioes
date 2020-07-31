package br.com.rafaellins.reunioes.api.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiErrorValidationDescription {
    /**
     * Name of the field
     */
    String field;

    /**
     * Type of validation
     */
    String type;

    /**
     * Validation message
     */
    String message;
}
