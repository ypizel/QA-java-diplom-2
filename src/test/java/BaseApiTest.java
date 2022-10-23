import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;

import static io.restassured.RestAssured.*;

public class BaseApiTest {
    @Before
    public void SetUp(){
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
    }

    public static String POST_CREATE_USER = "/api/auth/register";
    public static String POST_LOGIN_USER = "/api/auth/login";
    public static String DELETE_USER = "/api/auth/user";
    public static String PATCH_USER_INFO = "/api/auth/user";
    public static String POST_LOGOUT = "/api/auth/logout";
    public static String GET_ORDERS = "/api/orders";


    protected User user = new User("Asakura@yandex.ru", "1234", "Yo");
    protected User existingUser = new User("Horohoro@yandex.ru", "1234", "Tray");

//    static Random random = new Random();
//    static String email = "AlbertEinstein" + random.nextInt(10_000_000) + "@yandex.ru";
//    User user = new User(email, "12345", "Albert Einstein");

    public Response createUser(User user){
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .post(POST_CREATE_USER);
    }

    public Response loginUser(User user){
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .post(POST_LOGIN_USER);
    }
    public void deleteUser(User user){
        String token = getAccessToken(user);
        given()
                .auth().oauth2(token)
                .header("Content-type", "application/json")
                .body(user)
                .delete(DELETE_USER);
    }

    public String getAccessToken(User user) {
        String accessToken = loginUser(user).then().extract().body().path("accessToken");
        return accessToken.replaceAll("Bearer ", "");
    }

}
