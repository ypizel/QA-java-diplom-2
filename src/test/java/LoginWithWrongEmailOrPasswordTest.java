import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import user.User;

import static constanst.Messages.INCORRECT_DATA;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.junit.Assert.*;
@RunWith(Parameterized.class)
public class LoginWithWrongEmailOrPasswordTest extends BaseApiTest {

    private User userWithWrongField;
    public LoginWithWrongEmailOrPasswordTest(User userWithWrongField) {
        this.userWithWrongField = userWithWrongField;
    }

    @Parameterized.Parameters(name = "Тестовые данные: {index}")
    public static Object[][] getData() {
        return new Object[][]{
                {User.getDifferentEmail()},
                {User.getDifferentPassword()}
        };
    }

    @Test
    @DisplayName("Ргистрация с неверным паролем и имейлом")
    @Description("Проверка что при POST-запросе по ручке /api/auth/login c неверными данными возвращается код 401 и сообщение \"email or password are incorrect\"")
    public void loginWithExistingUser() {
        userClient.createAndReturnResponse(user);
        ValidatableResponse loginWithWringFieldResponse = userClient.login(userWithWrongField);

        int actualStatusCode = loginWithWringFieldResponse.extract().statusCode();
        String actualMessage = loginWithWringFieldResponse.extract().body().path("message");

        assertEquals("Status code is incorrect",SC_UNAUTHORIZED, actualStatusCode);
        assertEquals(INCORRECT_DATA, actualMessage);
    }
}
