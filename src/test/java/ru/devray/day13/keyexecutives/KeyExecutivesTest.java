package ru.devray.day13.keyexecutives;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;


public class KeyExecutivesTest {
    final static String TOKEN = "38c38210d93df1dbc59ec6c5e5b02f3f";
    final static String URI = "https://financialmodelingprep.com/api/v3";
    final static String PATH = "/key-executives";
    final static String URL = URI+PATH+"/AAPL?apikey="+TOKEN;
    Response response;

    @BeforeEach
    void setUp(){
        response = RestAssured.get(URL);
        System.out.println(response.asString());
    }

    @Test
    @DisplayName("Проверяем что ссылка не пустая")
    void urlNotNullTest(){
        Assertions.assertNotNull(response,"Ссылка никуда не ведет");
    }
    @Test
    @DisplayName("Проверка статус кода 200")
    void statusCodeTest(){
        Assertions.assertEquals(200,response.statusCode(),"Неожиданный статус код");
    }

    @Test
    @DisplayName("Проверяем поля")
    void testSymbols(){
        given()
                .baseUri(URI)
                .basePath(PATH)
                .log().all()
                .queryParam("apikey", TOKEN)
                .when().get("AAPL")
                .then().log().ifError()
                .body("[0].title", Matchers.notNullValue())
                .body("[0].name", Matchers.notNullValue())
                .body("[0].currencyPay", Matchers.notNullValue())
                .body("[0].gender", Matchers.matchesRegex("male|female"));
    }
    }

