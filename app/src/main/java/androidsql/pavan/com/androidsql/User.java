package androidsql.pavan.com.androidsql;

/**
 * Created by Pavan on 10/10/2015.
 */
public class User {
    String name, username, password;
    int age;

    public User(String name, int age, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.age = age;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.age = -1;
        this.name = "";
    }
}
