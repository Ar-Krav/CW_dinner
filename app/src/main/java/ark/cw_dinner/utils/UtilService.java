package ark.cw_dinner.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import ark.cw_dinner.database.DBManager;
import ark.cw_dinner.database.tables.meals.MealObject;
import ark.cw_dinner.database.tables.mealsmenu.MenuObject;

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

    public HashMap<String, List<MealObject>> getMealsSortedByType(Context context){
        HashMap<String, List<MealObject>> mealsTypeMap = new HashMap<String, List<MealObject>>();
        for (MealObject mealObj : getMealsInMenu(context)){
            if (mealsTypeMap.containsKey(mealObj.getType())){
                mealsTypeMap.get(mealObj.getType()).add(mealObj);
            }
            else {
                List<MealObject> newList = new ArrayList<MealObject>();
                newList.add(mealObj);
                mealsTypeMap.put(mealObj.getType(), newList);
            }
        }

        return mealsTypeMap;
    }

    private List<MealObject> getMealsInMenu(Context context){
        DBManager dbManager = new DBManager(context);

        HashMap<Integer, MealObject> mealsInMenu = new HashMap<>();
        for (MenuObject menuObj : dbManager.getMealsMenu()){
            MealObject mealObj = menuObj.getMeal();

            if (mealsInMenu.containsKey(mealObj.getMealId())){
                List<String> daysAvailable = mealsInMenu.get(mealObj.getMealId()).getAvailableInDays();
                daysAvailable.add(menuObj.getDayName());
            }
            else {
                List<String> daysNameList = new ArrayList<>();
                daysNameList.add(menuObj.getDayName());
                mealObj.setAvailableInDays(daysNameList);

                mealsInMenu.put(mealObj.getMealId(), mealObj);
            }
        }

        return new ArrayList<>(mealsInMenu.values());
    }
}
