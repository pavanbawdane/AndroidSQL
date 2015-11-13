package androidsql.pavan.com.androidsql;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity  {

    Button btn_register;
    EditText et_name, et_age, et_username, et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        et_name = (EditText) findViewById(R.id.et_name);
        et_age = (EditText) findViewById(R.id.et_age);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_register = (Button) findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = et_name.getText().toString();
                int age = Integer.parseInt(et_age.getText().toString());
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();
                Validations validations = new Validations();
                if(validations.validEmail(username)){
                    User registeredUser = new User(name, age, username, password);
                    registerUser(registeredUser);
                }else {
                    Toast.makeText(RegisterActivity.this,
                            "ERROR IN VALID EMAIL", Toast.LENGTH_LONG).show();

                }
            }
        });
    }

   /* @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                String name = et_name.getText().toString();
                int age = Integer.parseInt(et_age.getText().toString());
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();
                User registeredUser = new User(name, age, username, password);
                registerUser(registeredUser);
                System.out.println("Register btn called");
                ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Loading");
                break;
        }
    }*/

    private void registerUser(User registeredUser) {

        ServerRequest serverRequest = new ServerRequest(this);
        serverRequest.storeUserDataInBackground(registeredUser, new GetUserCallback() {
            @Override
            public void done(User returnedUser) {
                Toast.makeText(RegisterActivity.this, "Registered!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }
}