import io.qameta.allure.junit4.DisplayName;
import jdk.jfr.Description;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
public class LoginTest extends BaseApiTest {

    @Test
    @DisplayName("Регистрация")
    @Description("Проверка что при POST-запросе по ручке /api/auth/login возвращается код 200 и в BODY ответа \"success\":\"true\"")
    public void loginWithExistingUser(){
        createUser(user);
        loginUser(user).then()
                .statusCode(200)
                .and()
                .assertThat()
                .body("success", equalTo(true));
        deleteUser(user);
    }

}
