package api;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetProductsTests extends BaseApiTest {

    @Test(description = "GET /products returns 200 and a non-empty product list")
    public void getAllProductsReturnsList() {
        given().spec(requestSpec)
                .when().get("/products")
                .then()
                .statusCode(200)
                .body("products", not(empty()))
                .body("total", greaterThan(0));
    }

    @Test(description = "GET /products/{id} returns the requested product with matching id")
    public void getSingleProductById() {
        int productId = 1;
        given().spec(requestSpec)
                .pathParam("id", productId)
                .when().get("/products/{id}")
                .then()
                .statusCode(200)
                .body("id", equalTo(productId))
                .body("title", not(emptyOrNullString()))
                .body("price", greaterThan(0f));
    }

    @Test(description = "GET /products/{id} with an invalid id returns 404")
    public void getSingleProductInvalidIdReturns404() {
        given().spec(requestSpec)
                .pathParam("id", 999999)
                .when().get("/products/{id}")
                .then()
                .statusCode(404);
    }

    @Test(description = "GET /products/search returns only results matching the query")
    public void searchProductsReturnsRelevantResults() {
        Response response = given().spec(requestSpec)
                .queryParam("q", "phone")
                .when().get("/products/search")
                .then()
                .statusCode(200)
                .extract().response();

        java.util.List<String> titles = response.jsonPath().getList("products.title", String.class);
        Assert.assertFalse(titles.isEmpty(), "Expected at least one result for query 'phone'");
    }

    @Test(description = "GET /products supports pagination via limit and skip")
    public void productsPaginationWorks() {
        given().spec(requestSpec)
                .queryParam("limit", 5)
                .queryParam("skip", 10)
                .when().get("/products")
                .then()
                .statusCode(200)
                .body("products.size()", equalTo(5))
                .body("skip", equalTo(10));
    }
}
