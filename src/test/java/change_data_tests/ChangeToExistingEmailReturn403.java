package change_data_tests;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserClient;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.junit.Assert.assertEquals;
import static org.apache.http.HttpStatus.SC_OK;


public class ChangeToExistingEmailReturn403 {
    protected UserClient userClient;
    protected User user;
    protected User existingUser;


    @Before
    @Step("Инициализация user и userClient")
    public void setUp() {
        user = User.getUser();
        existingUser = User.getExistingUser();
        userClient = new UserClient();
    }

    @Test
    @DisplayName("Проверка, что пароль успешно меняется")
    @Description("В ходе теста проверяется что при PATCH-запросе на ручку /api/auth/user при изменении возвращается 200ОК")
    public void changeEmailTest() {
        userClient.createAndReturnToken(existingUser);
        String token = userClient.createAndReturnToken(user);

        ValidatableResponse changePasswordResponse = userClient.getChangeDataWithAuthResponse(token, existingUser);
        int actualStatusCode = changePasswordResponse.extract().statusCode();

        assertEquals(SC_FORBIDDEN, actualStatusCode);
    }

    @After
    @Step("Удаление тестовых данных")
    public void clear() {
        userClient.delete(User.getExistingUser());
        userClient.delete(User.getUser());
    }
}

