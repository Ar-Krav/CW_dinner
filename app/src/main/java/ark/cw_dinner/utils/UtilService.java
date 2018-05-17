package ark.cw_dinner.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Ar-Krav on 12.05.2018.
 */

public class UtilService {
    final private static String UTIL_TEST_TAG = "UTIL_SERVICE_TEST_TAG";

    public static int getCurrentUserId(Context context){
        int userID = context.getSharedPreferences(TagsValues.SHARED_PREFERENCE_NAME, MODE_PRIVATE).getInt(TagsValues.LOGINED_USER_ID_PREFERENCES, -1);
        Log.d("USER_TAG_TEST", "getCurrentUserId: " + userID);
        return userID;
    }

    public static int getCurrentUserType(Context context){
        int userType = context.getSharedPreferences(TagsValues.SHARED_PREFERENCE_NAME, MODE_PRIVATE).getInt(TagsValues.LOGINED_USER_TYPE_PREFERENCE, -1);
        Log.d("USER_TAG_TEST", "getCurrentUserId: " + userType);
        return userType;
    }

    public static String getCurrentDate(){
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public static void saveInSharedPreference(Context context, String tagValue, int savedValue){
        SharedPreferences loginedUserIdPreference = context.getSharedPreferences(TagsValues.SHARED_PREFERENCE_NAME,MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = loginedUserIdPreference.edit();
        prefEditor.putInt(tagValue, savedValue);
        prefEditor.apply();
    }
}
