package ark.cw_dinner.utils;

import android.content.Context;
import android.util.Log;

/**
 * Created by Ar-Krav on 12.05.2018.
 */

public class UtilService {
    public static int getUserId(Context context){
        int userID = context.getSharedPreferences(TagsValues.LOGINED_USER_PREFERENCES_NAME, Context.MODE_PRIVATE).getInt(TagsValues.LOGINED_USER_PREFERENCES, -1);
        Log.d("USER_TAG_TEST", "getUserId: " + userID);
        return userID;
    }
}
