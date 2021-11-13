package com.aylien.secretsanta.participant;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ParticipantResourceTest {

    @Test
    @Order(1)
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
    @Order(3)
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

    @Test
    @Order(4)
    public void testBulkRegister() {
        given()
                .body("""
                        [
                            {"email": "test1@company.com"},
                            {"email": "test2@company.com"},
                            {"email": "test3@company.com"}
                        ]""")
                .contentType(ContentType.JSON)
                .post("/participants/bulk")
                .then()
                .statusCode(200)
                .body("$.size()", is(3),
                        "email", containsInAnyOrder("test1@company.com", "test2@company.com", "test3@company.com"));
    }
}