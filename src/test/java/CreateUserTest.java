import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserClient;

import static constanst.Messages.DUPLICATE;
import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;


public class CreateUserTest {

    protected User user;
    protected UserClient userClient;

    @Before
    @Step("Инициализация user и userClient")
    public void setUser() {
        user = User.getUser();
        userClient = new UserClient();
    }

    @Test
    @DisplayName("Создание пользователя")
    @Description("Проверка что при POST-запросе по ручке /api/auth/register возвращается код 201 и в BODY ответа \"success\":\"true\"")
    @Step("Отправка запроса")
    public void createUniqueUser() {
        ValidatableResponse response = userClient.createAndReturnResponse(user);

        int actualStatusCode = response.extract().statusCode();
        boolean isSuccess = response.extract().body().path("success");

        assertEquals(SC_OK, actualStatusCode);
        assertTrue(isSuccess);
    }

    @Test
    @DisplayName("Создание пользователя, который уже есть в системе")
    @Description("Проверка, что при POST-запросе по ручке /api/auth/register возвращается код 403 и в BODY ответа success:false")
    public void createDuplicateUser() {
        userClient.createAndReturnResponse(user);
        ValidatableResponse duplicateUserResponse = userClient.createAndReturnResponse(user);

        int actualStatusCode = duplicateUserResponse.extract().statusCode();
        String actualMessage = duplicateUserResponse.extract().body().path("message");

        assertEquals("Incorrect status code", SC_FORBIDDEN, actualStatusCode);
        assertEquals(DUPLICATE, actualMessage);
    }
}




