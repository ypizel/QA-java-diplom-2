
import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Before;
import user.User;
import user.UserClient;


public class BaseApiTest {
    protected User user;
    protected UserClient userClient;

    @Before
    @Step("Инициализация user и userClient")
    public void setUser() {
        user = User.getUser();
        userClient = new UserClient();
    }

    @After
    public void clear() {
        userClient.delete(user);
    }
}