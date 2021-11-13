package com.aylien.secretsanta.participant;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;

@QuarkusTest
public class ParticipantResourceTest {

    @Test
    @Order(3)
    public void testList() {
        given()
                .when().get("/participants")
                .then()
                .statusCode(200)
                .body("$.size()", is(0));
    }

    @Test
    @Order(2)
    public void testRegister() {
        given()
                .body("""
                        {
                            "email": "test@company.com"
                        }""")
                .contentType(ContentType.JSON)
                .post("/participants")
                .then()
                .statusCode(200)
                .body("$.size()", is(1),
                        "email", contains("test@company.com"),
                        "matchHistory", Matchers.hasSize(1));
    }

    @Test
    @Order(1)
    public void testDelete() {
        given()
                .body("""
                        {
                            "email": "test@company.com"
                        }""")
                .contentType(ContentType.JSON)
                .delete("/participants")
                .then()
                .statusCode(200)
                .body("$.size()", is(0));
    }
}