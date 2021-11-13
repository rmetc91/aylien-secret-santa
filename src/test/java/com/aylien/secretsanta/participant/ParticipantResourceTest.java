package com.aylien.secretsanta.participant;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

@QuarkusTest
public class ParticipantResourceTest {

    @Test
    public void testListEndpoint() {
        given()
                .when().get("/participants")
                .then()
                .statusCode(200)
                .body("$", hasSize(0));
    }
}