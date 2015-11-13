package androidsql.pavan.com.androidsql;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Pavan on 10/10/2015.
 */
public class ServerRequest {
    ProgressDialog progressDialog;
    public static final int CONNECTION_TIMEOUT = 15000; // 15 seconds
    // public static final String SERVER_ADDRESS = "http://192.168.0.14/android-sql/";
    public static final String SERVER_ADDRESS = "http://10.183.1.180/android-sql/";

    public ServerRequest(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Processing your request...");
    }

    public void storeUserDataInBackground(User user, GetUserCallback userCallback) {
        progressDialog.show();
        new StoreUserDataAsyncTask(user, userCallback).execute();

    }

    public void fetchUserDataInBackground(User user, GetUserCallback userCallback) {
        progressDialog.show();
        new FetchUserDataAsyncTask(user, userCallback).execute();
    }

    /*public void fetchEventsListDataInBackground(Events events){
        progressDialog.show();
        new fetchEventListAsyncTask(events);
    }
*/
    public class StoreUserDataAsyncTask extends AsyncTask<Void, Void, Void> {
        User user;
        GetUserCallback userCallback;

        public StoreUserDataAsyncTask(User user, GetUserCallback userCallback) {
            this.user = user;
            this.userCallback = userCallback;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("name", user.name));
            dataToSend.add(new BasicNameValuePair("age", user.age + ""));
            dataToSend.add(new BasicNameValuePair("username", user.username));
            dataToSend.add(new BasicNameValuePair("password", user.password));

            // BasicHttpParams is deprecated and last comment in this url helped get dependency
            // http://stackoverflow.com/questions/29995749/with-what-can-i-replace-http-deprecated-methods

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient httpClient = new DefaultHttpClient(httpRequestParams);
            HttpPost httpPost = new HttpPost(SERVER_ADDRESS + "register.php");
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(dataToSend));
                httpClient.execute(httpPost);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            userCallback.done(null);
            super.onPostExecute(aVoid);
        }
    }

    public class FetchUserDataAsyncTask extends AsyncTask<Void, Void, User> {
        User user;
        GetUserCallback userCallback;

        public FetchUserDataAsyncTask(User user, GetUserCallback userCallback) {
            this.user = user;
            this.userCallback = userCallback;
        }

        @Override
        protected User doInBackground(Void... voids) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();

            dataToSend.add(new BasicNameValuePair("username", user.username));
            dataToSend.add(new BasicNameValuePair("password", user.password));

            // BasicHttpParams is deprecated and last comment in this url helped get dependency
            // http://stackoverflow.com/questions/29995749/with-what-can-i-replace-http-deprecated-methods

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient httpClient = new DefaultHttpClient(httpRequestParams);
            HttpPost httpPost = new HttpPost(SERVER_ADDRESS + "fetch_user_data.php");

            User returnedUser = null;

            try {
                httpPost.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                String result = EntityUtils.toString(httpEntity);
                // System.out.println(result.substring(result.indexOf("{"), result.lastIndexOf("}") +1));
                JSONObject jsonObject = new JSONObject(result.substring(result.indexOf("{"),
                        result.lastIndexOf("}") + 1));

                if (jsonObject.length() == 0) {
                    returnedUser = null;
                } else {
                    String name = jsonObject.getString("name");
                    int age = jsonObject.getInt("age");

                    returnedUser = new User(name, age, user.username, user.password);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return returnedUser;
        }

        @Override
        protected void onPostExecute(User returnedUser) {
            progressDialog.dismiss();
            userCallback.done(returnedUser);
            super.onPostExecute(returnedUser);
        }

    }


    public String url = "http://10.183.1.180/android-sql/getlist.php";


    public class fetchEventListAsyncTask extends AsyncTask<String, Void, String>{
        public Context context;
        Activity activity;
        public String jsonResult;

        public ListView listView;


        public fetchEventListAsyncTask(Context context, Activity activity) {
            this.context = context;
            this.activity = activity;
            listView = (ListView)this.activity.findViewById(R.id.list_events);
        }

        //Events events;
        @Override
        protected String doInBackground(String... strings) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(strings[0]);

            try {
                HttpResponse httpResponse = httpClient.execute(httpPost);
                jsonResult = inputStreamToString(httpResponse.getEntity().getContent()).toString();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private StringBuilder inputStreamToString(InputStream content) {
            String rLine = "";
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(content));
            try {
                while((rLine = bufferedReader.readLine()) != null){
                    stringBuilder.append(rLine);
                    System.out.println("rLine" + rLine);
                }
            }catch (ConnectException e){

            }catch (IOException e) {
                e.printStackTrace();
                Log.d("ServerRequest", "An error occured in inputStreamToString");
            }

            return stringBuilder;
        }

        @Override
        protected void onPostExecute(String s) {
            ListDrawer();
        }

        private void ListDrawer() {
            List<Map<String, String>> eventList = new ArrayList<Map<String, String>>();
            try {
                JSONObject jsonResponse = new JSONObject(jsonResult);
                JSONArray jsonArray = jsonResponse.getJSONArray("events");
                System.out.println("jsonArray.length()"+jsonArray.length());
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonChildNode = jsonArray.getJSONObject(i);
                    String event_name = jsonChildNode.optString("event_name");
                    String event_id = jsonChildNode.optString("event_id");
                    String event_output = event_name + "-" +event_id;
                    eventList.add(createEventHashmap("all_events" ,event_output));

                }
            } catch (JSONException e) {
                e.printStackTrace();

            }


            SimpleAdapter simpleAdapter = new SimpleAdapter(
                    context.getApplicationContext(),
                    eventList, R.layout.custom_textview,
                    new String[]{"all_events"},
                    new int[] {R.id.tv_list}
            );

            listView.setAdapter(simpleAdapter);
        }

        private Map<String, String> createEventHashmap(String key_name, String event_output) {
            HashMap<String, String> eventNameId = new HashMap<String, String>();
            eventNameId.put(key_name, event_output);
            System.out.println("key_name:" + key_name);
            return eventNameId;
        }
    }
    public void accessWebService(Context context, Activity activity) {
        fetchEventListAsyncTask jsonReadTask = new fetchEventListAsyncTask(context, activity);
        jsonReadTask.execute(new String[]{url});

    }

}

