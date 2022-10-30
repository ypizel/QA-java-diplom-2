package order;

import config.Config;
import io.restassured.specification.RequestSpecification;


import static io.restassured.RestAssured.given;

public class BaseOrder {
    protected RequestSpecification getSpec(){
        return given().log().all()
                .header("Content-type", "application/json")
                .baseUri(Config.BASE_USER_URL);
    }
}
