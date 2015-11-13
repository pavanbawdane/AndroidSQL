package androidsql.pavan.com.androidsql;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Pavan on 10/10/2015.
 */
public class UserLocalDatabase {
    public static final String SP_NAME = "userDetails";
    SharedPreferences sharedPreferences;

    public UserLocalDatabase(Context context) {
        sharedPreferences = context.getSharedPreferences(SP_NAME, 0);
    }

    public void storeUserData(User user){
        SharedPreferences.Editor spEditor = sharedPreferences.edit();
        spEditor.putString("name", user.name);
        spEditor.putInt("age", user.age);
        spEditor.putString("username", user.username);
        spEditor.putString("password", user.password);
        spEditor.commit();

    }

    public User getLoggedInUser(){
        String name = sharedPreferences.getString("name", "");
        int age = sharedPreferences.getInt("age", -1);
        String username = sharedPreferences.getString("username", "");
        String password = sharedPreferences.getString("password", "");

        User storedUser = new User(name, age, username, password);

        return storedUser;
    }

    public void setUserLoggedIn(boolean loggedIn){
        SharedPreferences.Editor spEditor = sharedPreferences.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.commit();
    }

    public boolean getUserLoggedIn(){
        if(sharedPreferences.getBoolean("loggedIn", false)){
            return true;
        }else {
            return false;
        }
    }
    public void clearUserData(){
        SharedPreferences.Editor spEditor = sharedPreferences.edit();
        spEditor.clear();
        spEditor.commit();
    }
}

