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

import static org.junit.Assert.assertEquals;
import static org.apache.http.HttpStatus.SC_OK;


public class ChangeNameTest {
    protected UserClient userClient;
    protected User user;


    @Before
    @Step("Инициализация user и userClient")
    public void setUp() {
        user = User.getUser();
        userClient = new UserClient();
    }

    @Test
    @DisplayName("Проверка, что имя успешно меняется")
    @Description("В ходе теста проверяется что при PATCH-запросе на ручку /api/auth/user при изменении возвращается 200ОК")
    public void changeEmailTest() {
        String token = userClient.createAndReturnToken(user);
        ValidatableResponse changeNameResponse = userClient.getChangeDataWithAuthResponse(token, User.getDifferentName());

        int actualStatusCode = changeNameResponse.extract().statusCode();

        assertEquals(SC_OK, actualStatusCode);
    }

    @After
    @Step("Удаление тестовых данных")
    public void clear() {
        userClient.delete(User.getDifferentName());
    }
}

