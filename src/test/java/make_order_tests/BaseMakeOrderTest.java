package make_order_tests;

import io.qameta.allure.Step;
import order.Ingredients;
import order.OrderClient;
import org.junit.Before;
import user.User;
import user.UserClient;

public class BaseMakeOrderTest {
    protected User user;
    protected Ingredients ingredients;
    protected UserClient userClient;
    protected OrderClient orderClient;

    @Before
    @Step("Инициализация user, ingredients, userClient и orderClient")
    public void setUser() {
        user = User.getUser();
        ingredients = Ingredients.makeIngredients();
        userClient = new UserClient();
        orderClient = new OrderClient();
    }
}
