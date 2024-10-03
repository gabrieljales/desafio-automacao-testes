package api.jsonplaceholder;

import api.jsonplaceholder.models.Post;
import api.jsonplaceholder.models.PostUpdate;
import io.qameta.allure.Description;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import static org.hamcrest.Matchers.hasSize;

public class ApiTest extends BaseApiTestSetup {
    @Test
    @Description("Valida o código de status e o corpo da resposta do endpoint /posts")
    public void testGetPosts() {
        given()
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("postSchema.json")) // busca na pasta resources
                .body("$", hasSize(100)); // "$" é para buscar no json inteiro
    }

    @Test
    @Description("Valida o título do primeiro post")
    public void testFirstPostTitle() {
        given()
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .body("title", equalTo("sunt aut facere repellat provident occaecati excepturi optio reprehenderit"));
    }

    @Test
    @Description("Valida a criação de um post no endpoint /posts")
    public void testCreatePost() {
        Post newPost = new Post(1, "Novo Título", "Novo conteúdo do post");

        given()
                .contentType("application/json")
                .body(newPost)  // Serializa automaticamente para JSON
                .when()
                .post("/posts")
                .then()
                .statusCode(201)
                .body("title", equalTo("Novo Título"))
                .body("body", equalTo("Novo conteúdo do post"))
                .body("userId", equalTo(1));
    }

    @Test
    @Description("Valida a atualização de um post existente no endpoint /posts/id com o método PUT")
    public void testUpdatePostWithPut() {
        PostUpdate updatedPost = new PostUpdate("Título Atualizado", "Conteúdo atualizado do post");

        given()
                .contentType("application/json")
                .body(updatedPost)  // Serializa automaticamente para JSON
                .when()
                .put("/posts/1")
                .then()
                .statusCode(200)
                .body("title", equalTo("Título Atualizado"))
                .body("body", equalTo("Conteúdo atualizado do post"));
    }

    @Test
    @Description("Valida a atualização parcial de um post existente no endpoint /posts/id com o método PATCH")
    public void testUpdatePostWithPatch() {
        PostUpdate partialUpdate = new PostUpdate("Título Parcialmente Atualizado", null);

        given()
                .contentType("application/json")
                .body(partialUpdate)  // Serializa automaticamente para JSON
                .when()
                .patch("/posts/1")
                .then()
                .statusCode(200)
                .body("title", equalTo("Título Parcialmente Atualizado"));
    }

    @Test
    @Description("Valida a ação de apagar um post específico no endpoint /posts")
    public void testDeletePostById() {
        given()
                .when()
                .delete("/posts/15")
                .then()
                .statusCode(200);
    }

    @Test
    @Description("Valida a resposta de erro ao tentar obter um post que não existe")
    public void testNotFoundPost() {
        given()
                .when()
                .get("/posts/101")
                .then()
                .statusCode(404);
    }
}
