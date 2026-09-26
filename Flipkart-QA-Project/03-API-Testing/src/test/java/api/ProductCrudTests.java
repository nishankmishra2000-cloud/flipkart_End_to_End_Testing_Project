package api;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * DummyJSON simulates writes: POST/PUT/DELETE return a well-formed response
 * reflecting the request, but nothing is persisted server-side. Tests here
 * validate the contract (status code, echoed fields, response shape) rather
 * than asserting the change is durable across a subsequent GET.
 */
public class ProductCrudTests extends BaseApiTest {

    @Test(description = "POST /products/add creates a product and echoes submitted fields")
    public void addProductReturnsCreatedResource() {
        String body = "{ \"title\": \"Flipkart QA Test Product\", \"price\": 999 }";

        given().spec(requestSpec)
                .contentType(ContentType.JSON)
                .body(body)
                .when().post("/products/add")
                .then()
                .statusCode(201)
                .body("title", equalTo("Flipkart QA Test Product"))
                .body("id", notNullValue());
    }

    @Test(description = "PUT /products/{id} updates a product and returns the updated fields")
    public void updateProductReturnsUpdatedFields() {
        String body = "{ \"title\": \"Updated Title\" }";

        given().spec(requestSpec)
                .contentType(ContentType.JSON)
                .pathParam("id", 1)
                .body(body)
                .when().put("/products/{id}")
                .then()
                .statusCode(200)
                .body("title", equalTo("Updated Title"))
                .body("id", equalTo(1));
    }

    @Test(description = "PATCH /products/{id} partially updates a product and returns the updated field")
    public void patchProductReturnsUpdatedField() {
        String body = "{ \"price\": 1299 }";

        given().spec(requestSpec)
                .contentType(ContentType.JSON)
                .pathParam("id", 1)
                .body(body)
                .when().patch("/products/{id}")
                .then()
                .statusCode(200)
                .body("price", equalTo(1299))
                .body("id", equalTo(1));
    }

    @Test(description = "DELETE /products/{id} marks the product as deleted")
    public void deleteProductReturnsDeletedFlag() {
        given().spec(requestSpec)
                .pathParam("id", 1)
                .when().delete("/products/{id}")
                .then()
                .statusCode(200)
                .body("isDeleted", equalTo(true))
                .body("id", equalTo(1));
    }
}
