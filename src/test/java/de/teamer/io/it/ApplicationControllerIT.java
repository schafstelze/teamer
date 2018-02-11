package de.teamer.io.it;

import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

public class ApplicationControllerIT extends BaseIT{

    @Test
    public void get_root_path_should_return_hello_world() {
        assertThat(
                given().contentType(ContentType.TEXT).when()
                        .get("/")
                        .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .asString(),
                is("Hello World!"));
    }

}
