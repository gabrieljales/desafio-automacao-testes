package api.jsonplaceholder;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.qameta.allure.restassured.AllureRestAssured;

import org.junit.jupiter.api.BeforeEach;

import java.util.List;

public class BaseApiTestSetup {
    @BeforeEach
    public void setup() {
        // URI base para os testes
        RestAssured.baseURI = "http://jsonplaceholder.typicode.com";

        // Verifica se os filtros já foram adicionados (Evitar relatório com valores duplicados à cada execução de cada teste)
        List<?> currentFilters = RestAssured.filters();
        if (currentFilters.isEmpty()) {
            RestAssured.baseURI = "http://jsonplaceholder.typicode.com";
            RestAssured.filters(/*new RequestLoggingFilter(), new ResponseLoggingFilter(),*/ new AllureRestAssured()); // Comentada configs para não poluir o terminal
        }
    }
}
