package androidsql.pavan.com.androidsql;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Pavan on 29/10/2015.
 */
public class Validations{
    Pattern pattern = Pattern.compile(".+@.+\\\\.[a-z]+");

    public boolean validEmail(String str){
        Matcher matcher = pattern.matcher(str);
        if(str.isEmpty() | !matcher.matches()){

            return false;
        }else{
            return true;
        }
    }

    /*public boolean checkInternet(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService
                (Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        android.net.NetworkInfo mobileInternet = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return wifi.isConnected() || mobileInternet.isConnected();
    }*/
}
