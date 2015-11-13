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


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button btn_logout, btn_view_events;
    EditText et_name, et_age, et_username;
    UserLocalDatabase userLocalDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_name = (EditText)findViewById(R.id.et_name);
        et_age = (EditText) findViewById(R.id.et_age);
        et_username = (EditText) findViewById(R.id.et_username);
        btn_logout = (Button) findViewById(R.id.btn_logout);
        btn_view_events = (Button) findViewById(R.id.btn_view_events);
        btn_logout.setOnClickListener(this);
        btn_view_events.setOnClickListener(this);
        userLocalDatabase = new UserLocalDatabase(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(authenticate()){
            displayUserDetails();
        }else{
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }

    private void displayUserDetails(){
        User user = userLocalDatabase.getLoggedInUser();
        et_name.setText(user.name);
        et_username.setText(user.username);
        et_age.setText(user.age + "");
    }

    private boolean authenticate(){
        return  userLocalDatabase.getUserLoggedIn();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_logout:
                userLocalDatabase.clearUserData();
                userLocalDatabase.setUserLoggedIn(false);
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                break;
            case R.id.btn_view_events:
                startActivity(new Intent(MainActivity.this, EventListActivity.class));
                break;
        }
    }
}
