package br.com.rafaellins.reunioes;

import br.com.rafaellins.reunioes.util.PostgreSQLConfig;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Testcontainers
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {Application.class, PostgreSQLConfig.class}, webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
public class IntegrationTest {
    @LocalServerPort
    protected int serverPort = 0;

    @BeforeEach
    public void beforeEach() {
        RestAssured.baseURI = "http://localhost:" + serverPort;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}
