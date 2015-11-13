package androidsql.pavan.com.androidsql;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_login;
    EditText et_username, et_password;
    TextView tv_register_link;
    UserLocalDatabase userLocalDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        tv_register_link = (TextView) findViewById(R.id.tv_register_link);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        tv_register_link.setOnClickListener(this);

        userLocalDatabase = new UserLocalDatabase(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();
                User user = new User(username, password);
                authenticate(user);

                break;
            case R.id.tv_register_link:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
        }
    }

    private void authenticate(User user) {
        ServerRequest serverRequest = new ServerRequest(this);
        serverRequest.fetchUserDataInBackground(user, new GetUserCallback() {
            @Override
            public void done(User returnedUser) {
                if(returnedUser == null){
                    showErrorMessage();
                }
                else{
                    logUserIn(returnedUser);
                }
            }
        });
    }

    private void logUserIn(User returnedUser) {
        userLocalDatabase.storeUserData(returnedUser);
        userLocalDatabase.setUserLoggedIn(true);
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    private void showErrorMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setMessage("Incorrect user details");
        builder.setPositiveButton("Ok", null);
        builder.show();
    }

}
