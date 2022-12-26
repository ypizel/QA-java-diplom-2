package make_order_tests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.junit.After;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static constanst.Messages.NO_INGREDIENTS;
import static org.junit.Assert.assertEquals;


public class MakeOrderWithNoIngredients extends BaseMakeOrderTest {

    @Test
    @DisplayName("Создание заказа без ингридиентов")
    @Description("Проверка что при POST-запросе по ручке /api/orders возвращается код 200 и в BODY ответа \"success\":\"true\"")
    public void makeOrderWithNoIngredients() {
        String token = userClient.createAndReturnToken(user);
        ValidatableResponse response = orderClient.makeOrderWithNoIngredients(token);

        int actualStatusCode = response.extract().statusCode();
        String actualMessage = response.extract().body().path("message");

        assertEquals("Status code is incorrect",SC_BAD_REQUEST, actualStatusCode);
        assertEquals(NO_INGREDIENTS, actualMessage);
    }
    @After
    public void clear() {
        userClient.delete(user);
    }
}
