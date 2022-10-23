import io.qameta.allure.junit4.DisplayName;
import jdk.jfr.Description;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class GetOrderTest extends BaseApiTest{




    @Test
    @DisplayName("Получение заказов пользователя с авторизацией")
    @Description("Проверка, что при GET-запросе по ручке /api/orders возвращается код 200 и в BODY ответа \"success\":\"true\"")
    public void getExactUserOrdersWithAuth(){
        createUser(user);
        String token = getAccessToken(user);
        given()
                .auth().oauth2(token)
                .get(GET_ORDERS)
                .then().statusCode(200).and().assertThat().body("success", equalTo(true));
        deleteUser(user);
    }

    @Test
    @DisplayName("Получение заказов пользователя без авторизациии")
    @Description("Проверка, что при GET-запросе по ручке /api/auth/register возвращается код 403 и в BODY ответа \"message\":\"You should be authorised\"")
      public void getExactUserOrderWithNoAuth(){
        given()
                .get(GET_ORDERS)
                .then().statusCode(401).and().assertThat().body("message", equalTo("You should be authorised"));
    }
}
