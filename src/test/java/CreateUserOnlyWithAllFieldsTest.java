import io.qameta.allure.junit4.DisplayName;
import jdk.jfr.Description;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
public class CreateUserOnlyWithAllFieldsTest extends BaseApiTest {

    private final String json;
    private int statusCode = 403;
    private final String message = "Email, password and name are required fields";

    public CreateUserOnlyWithAllFieldsTest(String json) {
        this.json = json;


    }

    @Parameterized.Parameters(name = "Тестовые данные: {index}")
    public static Object[][] getData(){
        return new Object[][] {
                // не имейла
                {"{\"password\": \"1234\",\"firstName\": \"Yo\"}"},
                // нет пароля
                {"{\"login\": \"Lebowski\",\"firstName\": \"Yo\"}"},
                // нет имени
                {"{\"login\": \"Lebowski\",\"password\": \"1234\"}"}
        };
    }

    @Test
    @DisplayName("Создание без одного из обязательных полей")
    @Description("Проверка что при POST-запросе по ручке /api/auth/register возвращается код 403 и в BODY ответа \"message\":\"Email, password and name are required fields\"")
    public void createUserOnlyWithAllFields(){
        given()
                .header("Content-type", "application/json")
                .body(json)
                .post(POST_CREATE_USER)
                .then().statusCode(statusCode).and().assertThat().body("message",equalTo(message));
    }
}
