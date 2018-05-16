package ark.cw_dinner.utils;

import android.content.Context;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        Log.d(UTIL_TEST_TAG, "Date: " + new SimpleDateFormat("dd/MM/yyyy").format(new Date()));

        return new SimpleDateFormat("dd/MM/yyyy").format(new Date());
    }
}
