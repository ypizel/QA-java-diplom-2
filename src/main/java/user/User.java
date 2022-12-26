package user;

public class User {

    private String email;
    private String password;
    private String name;

    public User(){}
    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static User getUser(){
    return new User(
            "Asakura@yandex.ru",
            "1234",
            "Yo");
    }

    public static User getDifferentEmail(){
        return new User("asakura1@yandex.ru",
                "1234",
                "Yo");
    }
    public static User getDifferentPassword(){
        return new User("asakura@yandex.ru",
                "12345",
                "Yo");
    }
    public static User getDifferentName(){
        return new User("asakura@yandex.ru",
                "1234",
                "Yoshimaru");
    }

    public static User getExistingUser(){
        return new User("Horohoro@yandex.ru",
                "1234",
                "Trey");
    }
    public static User getWithoutEmail() {
        return new User("", "1234", "Saitama");
    }

    public static User getWithoutPassword() {
        return new User("onepunch@yandex.ru", "", "Saitama");
    }
    public static User getWithoutName() {
        return new User("onepunch@yandex.ru", "1234", "");
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
