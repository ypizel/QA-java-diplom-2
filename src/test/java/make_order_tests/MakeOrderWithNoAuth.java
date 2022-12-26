package make_order_tests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.*;

public class MakeOrderWithNoAuth extends BaseMakeOrderTest {

    @Test
    @DisplayName("Создание заказа без авторизации")
    @Description("Проверка что при POST-запросе по ручке /api/orders возвращается код 200 и в BODY ответа \"success\":\"true\"")
    public void makeOrderWithNoAuth() {
        String token = "";
        ValidatableResponse response = orderClient.makeOrder(token, ingredients);

        int actualStatusCode = response.extract().statusCode();
        boolean isSucceed = response.extract().body().path("success");

        assertEquals(SC_OK, actualStatusCode);
        assertTrue(isSucceed);
    }
}

