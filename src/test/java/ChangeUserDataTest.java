import io.qameta.allure.junit4.DisplayName;
import jdk.jfr.Description;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;


@RunWith(Parameterized.class)
public class ChangeUserDataTest extends BaseApiTest {

    protected User fieldThatHasToBeChanged;
    protected int statusCode;

    public ChangeUserDataTest(User fieldThatHasToBeChanged, int statusCode) {
        this.fieldThatHasToBeChanged = fieldThatHasToBeChanged;
        this.statusCode = statusCode;
    }

    @Parameterized.Parameters(name = "Тестовые данные: {index}")
    public static Object[][] setData() {
        return new Object[][]{
                // тот же имейл
                {new User("Horohoro@yandex.ru", "1234", "Yo"), 403},
                // изменение имейла
                {new User("Asakura1@yandex.ru", "1234", "Yo"), 200},
                // изменение пароля
                {new User("Asakura@yandex.ru", "12345", "Yo"), 200},
                // изменение имени
                {new User("Asakura1@yandex.ru", "12345", "Yoshimaru"), 200},
        };
    }

    @Before
    public void setUser() {
        createUser(user);
        createUser(existingUser);
    }

    @Test
    @DisplayName("Изменение данных пользователя с авторизацией")
    @Description("Проверка, что при POST-запросе по ручке /api/auth/user и передаче валидных и невалидных данных возвращается код 200 для валидных и 403 для невалидных")
    public void changeUserInfoWithAuth() {
        String token = getAccessToken(user);
        given()
                .auth().oauth2(token)
                .header("Content-type", "application/json")
                .body(fieldThatHasToBeChanged)
                .patch(PATCH_USER_INFO)
                .then().statusCode(statusCode);
        deleteUser(fieldThatHasToBeChanged);


    }

    @Test
    @DisplayName("Изменение данных пользователя без авторизации")
    @Description("Проверка, что при POST-запросе по ручке /api/auth/user без авторизации возравщяется код 401")
    public void changeUserInfoWithoutAuth() {
        given()
                .auth().oauth2("")
                .header("Content-type", "application/json")
                .body(fieldThatHasToBeChanged)
                .patch(PATCH_USER_INFO)
                .then().statusCode(401).and().assertThat().body("success", equalTo(false));
        deleteUser(user);
    }

}