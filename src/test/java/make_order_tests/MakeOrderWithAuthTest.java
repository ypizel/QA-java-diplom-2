package make_order_tests;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import order.Ingredients;
import order.OrderClient;
import org.junit.After;
import org.junit.Test;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.*;

public class MakeOrderWithAuthTest extends BaseMakeOrderTest {

    @Test
    @DisplayName("Создание заказа c авторизацией")
    @Description("Проверка что при POST-запросе по ручке /api/orders возвращается код 200 и в BODY ответа \"success\":\"true\"")
    public void makeOrderWithAuth() {
        String token = userClient.createAndReturnToken(user);
        ValidatableResponse response = orderClient.makeOrder(token, ingredients);

        int actualStatusCode = response.extract().statusCode();
        boolean isSucceed = response.extract().body().path("success");

        assertEquals(SC_OK, actualStatusCode);
        assertTrue(isSucceed);
    }

    @Step("Очистка тестовых данных")
    @After
    public void clear() {
        userClient.delete(user);
    }
}