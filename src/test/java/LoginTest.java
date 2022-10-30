import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.*;

public class LoginTest extends BaseApiTest {


    @Test
    @DisplayName("Регистрация")
    @Description("Проверка что при POST-запросе по ручке /api/auth/login возвращается код 200 и в BODY ответа success:true")
    public void loginWithExistingUser() {
        userClient.createAndReturnResponse(user);
        ValidatableResponse loginResponse = userClient.login(user);

        int actualStatusCode = loginResponse.extract().statusCode();
        boolean isSucceed = loginResponse.extract().body().path("success");

        assertEquals(SC_OK, actualStatusCode);
        assertTrue(isSucceed);
    }
}
