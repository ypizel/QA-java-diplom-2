import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import order.OrderClient;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;
import static constanst.Messages.NO_AUTH;
public class GetOrderTest extends BaseApiTest {

    private OrderClient orderClient = new OrderClient();

    @Test
    @DisplayName("Получение заказов пользователя с авторизацией")
    @Description("Проверка, что при GET-запросе по ручке /api/orders возвращается код 200 и в BODY ответа success:true")
    public void getExactUserOrdersWithAuth() {
        String token = userClient.createAndReturnToken(user);
        ValidatableResponse getOrderResponse = orderClient.getOrders(token, user);

        int actualStatusCode = getOrderResponse.extract().statusCode();
        boolean isSucceed = getOrderResponse.extract().body().path("success");

        assertEquals(SC_OK, actualStatusCode);
        assertTrue(isSucceed);
    }

    @Test
    @DisplayName("Получение заказов пользователя без авторизациии")
    @Description("Проверка, что при GET-запросе по ручке /api/auth/register возвращается код 403 и в BODY ответа \"message\":\"You should be authorised\"")
    @Step("Отправка запроса")
    public void getExactUserOrderWithNoAuth() {
        String token = "";
        ValidatableResponse getOrdersResponse = orderClient.getOrders(token, user);

        int actualStatusCode = getOrdersResponse.extract().statusCode();
        String actualMessage = getOrdersResponse.extract().body().path("message");

        assertEquals(SC_UNAUTHORIZED, actualStatusCode);
        assertEquals(NO_AUTH, actualMessage);
    }
}
