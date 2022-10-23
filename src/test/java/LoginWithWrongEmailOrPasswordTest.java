import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import jdk.jfr.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class LoginWithWrongEmailOrPasswordTest extends BaseApiTest{

    protected final User user2;
    private final int statusCode = 401;
    private final String message = "email or password are incorrect";

    public LoginWithWrongEmailOrPasswordTest(User user) {
        this.user2 = user;
    }

    @Parameterized.Parameters(name = "Тестовые данные: {index}")
    public static Object[][] getData(){
        return new Object[][]{
                {new User("Asakura1@yandex.ru","1234", "Albert Einstein")},
                {new User("Asakura@yandex.ru","12345", "Albert Einstein")}
        };
    }

    @Step("Создание пользователя")
    @Before
    public void setUser(){
        createUser(user);
    }

    @Test
    @DisplayName("Ргистрация с неверным паролем и имейлом")
    @Description("Проверка что при POST-запросе по ручке /api/auth/login c неверными данными возвращается код 401 и сообщение \"email or password are incorrect\"")
    @Step("Попытка регистрации с опечаткой в имейле и пароле")
    public void loginWithExistingUser(){
        loginUser(user2).then().statusCode(statusCode).and().assertThat().body("message", equalTo(message));
    }

    @Step("Удаление тестовых данных")
    @After
    public void clearTestData(){
        deleteUser(user);
    }
}
