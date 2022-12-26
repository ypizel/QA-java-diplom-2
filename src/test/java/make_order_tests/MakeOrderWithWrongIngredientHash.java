package make_order_tests;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import order.Ingredients;
import order.OrderClient;
import org.junit.Before;
import org.junit.Test;
import user.UserClient;

import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;
import static org.junit.Assert.assertEquals;

public class MakeOrderWithWrongIngredientHash {

    protected Ingredients ingredients;
    protected UserClient userClient;
    protected OrderClient orderClient;

    @Before
    @Step("Инициализация ingredients, userClient и orderClient")
    public void setUser() {
        ingredients = Ingredients.makeIngredientsWithWrongHash();
        userClient = new UserClient();
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Создание заказа без ингридиентов")
    @Description("Проверка что при POST-запросе по ручке /api/orders возвращается код 200 и в BODY ответа \"success\":\"true\"")
    public void makeOrderWithNoIngredients() {
        String token = "";
        ValidatableResponse response = orderClient.makeOrder(token, ingredients);

        int actualStatusCode = response.extract().statusCode();

        assertEquals("Status code is incorrect",SC_INTERNAL_SERVER_ERROR, actualStatusCode);
    }
}
