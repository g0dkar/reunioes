package br.com.rafaellins.reunioes.api.global;

import br.com.rafaellins.reunioes.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

class GlobalApiExceptionHandlerIntegrationTest extends IntegrationTest {

    @Test
    void testNotFound() {
        given()
            .contentType(JSON)
            .get("/__not_found")
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value())
            .body("status", equalTo(404))
            .body("message", equalTo("Not Found"))
            .body("code", equalTo("NOT_FOUND"))
            .body("timestamp", notNullValue());
    }
}
