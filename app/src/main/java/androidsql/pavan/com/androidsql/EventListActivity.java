package androidsql.pavan.com.androidsql;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class EventListActivity extends AppCompatActivity {

    private String jsonResult;
    private String url = "http://10.193.3.37/android-sql/getlist.php";
    private ListView listView;
    public ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading events");
        progressDialog.setCancelable(false);
        progressDialog.show();
        listView = (ListView) findViewById(R.id.list_events);
        ServerRequest serverRequest = new ServerRequest(this);
        serverRequest.accessWebService(this, this);
        progressDialog.dismiss();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
