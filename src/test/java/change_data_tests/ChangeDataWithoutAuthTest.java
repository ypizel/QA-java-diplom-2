package change_data_tests;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import user.User;
import user.UserClient;

import static constanst.Messages.NO_AUTH;
import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ChangeDataWithoutAuthTest {

    private User fieldThatHasToBeChanged;
    private UserClient userClient;

    public ChangeDataWithoutAuthTest(User fieldThatHasToBeChanged) {
        this.fieldThatHasToBeChanged = fieldThatHasToBeChanged;
    }

    @Parameterized.Parameters(name = "Тестовые данные: {index}")
    @Step("Подготовка тестовых данных")
    public static Object[][] setData() {
        return new Object[][]{
                // тот же имейл
                {User.getExistingUser()},
                // изменение имейла
                {User.getDifferentEmail()},
                // изменение пароля
                {User.getDifferentPassword()},
                // изменение имени
                {User.getDifferentName()}
        };
    }
    @Before
    @Step("Инициализация existingUser и userClient")
    public  void setUser() {
        userClient = new UserClient();
    }

    @Test
    @DisplayName("Проверка, что данные пользователя без авторизации не меняется")
    public void changeDataWithNoAuthTest() {
        ValidatableResponse noAuthResponse = userClient.getChangeDataWithNoAuthResponse(fieldThatHasToBeChanged);

        int actualStatusCode = noAuthResponse.extract().statusCode();
        String actualMessage = noAuthResponse.extract().body().path("message");

        assertEquals("Status code is incorrect" ,SC_UNAUTHORIZED, actualStatusCode);
        assertEquals(NO_AUTH, actualMessage);
    }
}