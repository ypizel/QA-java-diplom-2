package order;

import groovy.transform.ToString;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import user.User;


public class OrderClient extends BaseOrder {

    public final String GET_ORDERS = "/api/orders";

    @Step("Получаем ответ на попытку получить список заказов")
    public ValidatableResponse getOrders(String token, User user) {
        return getSpec()
                .auth().oauth2(token)
                .body(user)
                .when()
                .get(GET_ORDERS)
                .then().log().all();
    }

    @Step("Получаем ответ на попытку создания заказа")
    public ValidatableResponse makeOrder(String token, Ingredients ingredients) {
        return getSpec()
                .auth().oauth2(token)
                .body(ingredients)
                .when()
                .post(GET_ORDERS)
                .then().log().all();
    }
    @Step("Получаем ответ на попытку создания заказа")
    public ValidatableResponse makeOrderWithNoIngredients(String token) {
        return getSpec()
                .auth().oauth2(token)
                .when()
                .post(GET_ORDERS)
                .then().log().all();
    }

}
