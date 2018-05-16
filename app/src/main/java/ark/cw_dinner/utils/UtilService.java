package ark.cw_dinner.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import ark.cw_dinner.R;
import ark.cw_dinner.database.DBManager;
import ark.cw_dinner.mainpart.orderinghistory.HistoryFragment;

/**
 * Created by Ar-Krav on 12.05.2018.
 */

public class UtilService {
    final private static String UTIL_TEST_TAG = "UTIL_SERVICE_TEST_TAG";

    public static int getUserId(Context context){
        int userID = context.getSharedPreferences(TagsValues.LOGINED_USER_PREFERENCES_NAME, Context.MODE_PRIVATE).getInt(TagsValues.LOGINED_USER_PREFERENCES, -1);
        Log.d("USER_TAG_TEST", "getUserId: " + userID);
        return userID;
    }

    public static String getCurrentDate(){
        return new SimpleDateFormat("dd/MM/yyyy").format(new Date());
    }
}
