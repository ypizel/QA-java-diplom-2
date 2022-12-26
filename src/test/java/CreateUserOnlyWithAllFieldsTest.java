import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import user.User;
import user.UserClient;

import static constanst.Messages.NOT_ENOUGH_DATA;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CreateUserOnlyWithAllFieldsTest {

    private User user;
    private UserClient userClient;

    public CreateUserOnlyWithAllFieldsTest(User user) {
        this.user = user;
    }
    @Before
    @Step("Инициализация userClient")
    public  void setUser() {
        userClient = new UserClient();
    }

    @Parameterized.Parameters(name = "Тестовые данные: {index}")
    @Step("Подготовка тестовых данных")
    public static Object[][] getData() {
        return new Object[][]{
                // не имейла
                {User.getWithoutEmail()},
                // нет пароля
                {User.getWithoutEmail()},
                // нет имени
                {User.getWithoutName()}
        };
    }

    @Test
    @DisplayName("Создание без одного из обязательных полей")
    @Description("Проверка что при POST-запросе по ручке /api/auth/register возвращается код 403 и в BODY ответа message: Email, password and name are required fields")
    public void createUserOnlyWithAllFields() {
         ValidatableResponse  response =  userClient.createAndReturnResponse(user);

         int actualStatusCode = response.extract().statusCode();
         String actualMessage = response.extract().body().path("message");

         assertEquals(NOT_ENOUGH_DATA, actualMessage);
         assertEquals("Incorrect status code", SC_FORBIDDEN, actualStatusCode);
    }
}
