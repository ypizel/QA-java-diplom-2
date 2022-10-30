package user;

import config.Config;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;


import static io.restassured.RestAssured.*;

public class UserClient extends BaseUserClient{
    public final String POST_CREATE_USER = "/api/auth/register";
    public final String POST_LOGIN_USER = "/api/auth/login";
    public final String PATCH_USER_INFO = "/api/auth/user";


    @Step("Получаем ответ на попытку изменить данные пользователя без авторизации")
    public ValidatableResponse getChangeDataWithNoAuthResponse(User user) {
        return getSpec()
                .auth().oauth2("")
                .body(user)
                .when()
                .patch(PATCH_USER_INFO)
                .then().log().all();
    }

    @Step("Получаем ответ на попытку изменить данные пользователя с авторизацией")
    public ValidatableResponse getChangeDataWithAuthResponse(String token, User newField) {
        return getSpec()
                .auth().oauth2(token)
                .body(newField)
                .when()
                .patch(PATCH_USER_INFO)
                .then().log().all();
    }


    @Step("Регистрируем пользователя и получаем accessToken")
    public String createAndReturnToken(User user) {
        String accessToken = getSpec()
                .body(user)
                .when()
                .post(POST_CREATE_USER)
                .then().log().all()
                .statusCode(200)
                .extract()
                .body()
                .path("accessToken");
        return accessToken.replaceAll("Bearer ", "");
    }
    @Step("Регистрируем пользователя и получаем accessToken")
    public ValidatableResponse createAndReturnResponse(User user) {
        return getSpec()
                .body(user)
                .when()
                .post(POST_CREATE_USER)
                .then();
    }

    @Step("Получаем ответ на попытку авторизоваться")
    public ValidatableResponse login(User user) {
        return getSpec()
                .body(user)
                .when()
                .post(POST_LOGIN_USER)
                .then().log().all();
    }

    @Step("Получаем ответ на попытку удалить пользователя")
    public ValidatableResponse delete(User user) {
        String token = getAccessToken(user);
        return getSpec()
                .auth().oauth2(token)
                .body(user)
                .delete(PATCH_USER_INFO)
                .then().log().all();
    }

    public String getAccessToken(User user) {
        String accessToken = login(user).extract().body().path("accessToken");
        return accessToken.replace("Bearer ", "");
    }
}
