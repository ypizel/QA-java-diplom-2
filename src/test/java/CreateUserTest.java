import io.qameta.allure.junit4.DisplayName;
import jdk.jfr.Description;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class CreateUserTest extends BaseApiTest {


    @Test
    @DisplayName("Создание пользователя")
    @Description("Проверка что при POST-запросе по ручке /api/auth/register возвращается код 201 и в BODY ответа \"success\":\"true\"")
    public void createUniqueUser(){
        createUser(user)
                .then()
                .statusCode(200)
                .and()
                .assertThat().body("success", equalTo(true));
        deleteUser(user);
    }

    @Test
    @DisplayName("Создание пользователя, который уже есть в системе")
    @Description("Проверка, что при POST-запросе по ручке /api/auth/register возвращается код 403 и в BODY ответа \"success\":\"false\"")
    public void createDuplicateUser(){
        createUser(user);
        createUser(user)
                .then()
                .statusCode(403)
                .and()
                .assertThat().body("success", equalTo(false))
                .and().assertThat().body("message", equalTo("User already exists"));
        deleteUser(user);
    }
}
