package ark.cw_dinner.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import ark.cw_dinner.database.tables.DaysOfWeekTable;
import ark.cw_dinner.database.tables.MealsTypeTable;
import ark.cw_dinner.database.tables.UserTypeTable;
import ark.cw_dinner.database.tables.account.AccountObject;
import ark.cw_dinner.database.tables.account.AccountsTable;
import ark.cw_dinner.database.tables.meals.MealObject;
import ark.cw_dinner.database.tables.meals.MealsTable;
import ark.cw_dinner.database.tables.mealsmenu.MenuObject;
import ark.cw_dinner.database.tables.mealsmenu.MenuTable;
import ark.cw_dinner.database.tables.ordering.OrderingObject;
import ark.cw_dinner.database.tables.ordering.OrderingTable;
import ark.cw_dinner.utils.TagsValues;
import ark.cw_dinner.utils.UtilService;

public class DBManager extends SQLiteOpenHelper {
    private String TEST_TAG = "DBManager_DEBUG_TEST";

    private Context context;

    private static final String DB_NAME = "cw_dinner_db";
    private static final int DB_VERSION = 1;

    public DBManager(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserTypeTable.CREATION_QUERY);
        db.execSQL(UserTypeTable.DEFAULT_VALUE_QUERY);

        db.execSQL(AccountsTable.CREATION_QUERY);
        db.execSQL(AccountsTable.DEFAULT_VALUE_QUERY);

        db.execSQL(DaysOfWeekTable.CREATION_QUERY);
        db.execSQL(DaysOfWeekTable.DEFAULT_VALUE_QUERY);

        db.execSQL(MealsTypeTable.CREATION_QUERY);
        db.execSQL(MealsTypeTable.DEFAULT_VALUE_QUERY);

        db.execSQL(MealsTable.CREATION_QUERY);
        db.execSQL(MealsTable.DEFAULT_VALUE_QUERY);

        db.execSQL(MenuTable.CREATION_QUERY);
        db.execSQL(MenuTable.DEFAULT_VALUE_QUERY);

        db.execSQL(OrderingTable.CREATION_QUERY);
        db.execSQL(OrderingTable.DEFAULT_VALUE_QUERY); //TODO del on reliese
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UserTypeTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + OrderingTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MenuTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MealsTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MealsTypeTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DaysOfWeekTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + AccountsTable.TABLE_NAME);

        onCreate(db);
    }

    /**
       Methods for working with DB
     */

    public AccountObject getLoginedUser(String login, String passwd){
        AccountObject loginedUser = new AccountObject();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " +
                            AccountsTable.FIELD_ID + ", " +
                            AccountsTable.FIELD_NAME + ", " +
                            AccountsTable.FIELD_LAST_NAME + ", " +
                            AccountsTable.FIELD_LOGIN + ", " +
                            AccountsTable.FIELD_TYPE +
                       " FROM " + AccountsTable.TABLE_NAME +
                       " WHERE " + AccountsTable.FIELD_LOGIN + " = '" + login + "' AND " + AccountsTable.FIELD_PASSWD + " = '" + passwd + "';";


        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()){
            do {
                loginedUser.setUserId(cursor.getInt(0));
                loginedUser.setName(cursor.getString(1));
                loginedUser.setLastName(cursor.getString(2));
                loginedUser.setLogin(cursor.getString(3));
                loginedUser.setType(cursor.getInt(4));
            }while (cursor.moveToNext());

            return loginedUser;
        }
        else {
            return null;
        }
    }

    public Boolean insertNewAccount(AccountObject userInfo){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
            contentValues.put(AccountsTable.FIELD_NAME, userInfo.getName());
            contentValues.put(AccountsTable.FIELD_LAST_NAME, userInfo.getLastName());
            contentValues.put(AccountsTable.FIELD_LOGIN, userInfo.getLogin());
            contentValues.put(AccountsTable.FIELD_PASSWD, userInfo.getPasswd());
            contentValues.put(AccountsTable.FIELD_TYPE, TagsValues.ACCOUNT_TYPE_USER);

        Boolean isAccountCreated = db.insert(AccountsTable.TABLE_NAME, null, contentValues) > 0;
        db.close();

        return isAccountCreated;
    }

    public List<MealObject> getMeals(){
        List<MealObject> mealObjectList = new ArrayList<>();

        String query = "SELECT " +
                            MealsTable.TABLE_NAME + "." + MealsTable.FIELD_NAME + ", " +
                            MealsTable.TABLE_NAME + "." + MealsTable.FIELD_COST + ", " +
                            MealsTable.TABLE_NAME + "." + MealsTable.FIELD_DESCRIPTION + ", " +
                            MealsTypeTable.TABLE_NAME + "." + MealsTypeTable.FIELD_NAME +
                       " FROM " + MealsTable.TABLE_NAME +
                       " INNER JOIN " + MealsTypeTable.TABLE_NAME +
                               " ON " + MealsTypeTable.TABLE_NAME + "." + MealsTypeTable.FIELD_ID + " = " + MealsTable.TABLE_NAME + "." + MealsTable.FIELD_TYPE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()){
            do {
                MealObject mealObject = new MealObject();

                mealObject.setName(cursor.getString(0));
                mealObject.setCost(cursor.getInt(1));
                mealObject.setDescription(cursor.getString(2));
                mealObject.setType(cursor.getString(3));

                mealObjectList.add(mealObject);
            }while (cursor.moveToNext());

            return mealObjectList;
        }
        else {
            return null;
        }
    }

    public List<MenuObject> getMealsMenu(){
        String query = getQueryAllMealsMenu();
        return getListMenuItems(query);
    }

    public List<MenuObject> getMealsMenuByDay(String dayOfWeek){
        String query = getQueryAllMealsMenu() +
                        " WHERE " + MenuTable.TABLE_NAME + "." + MenuTable.FIELD_WEEK_DAY_ID + " = " + dayOfWeek;

        return getListMenuItems(query);
    }

    public List<OrderingObject> getUserOrderingHistory(int userId){
        String query = getUserOrderingHistoryQuery(userId) +
                        " ORDER BY " + OrderingTable.TABLE_NAME + "." + OrderingTable.FIELD_ORDER_DATE + " DESC";

        return getOrderingTableData(query);
    }

    public List<OrderingObject> getOrderingHistoryForToday(){
        String query = getUserOrderingHistoryQuery(UtilService.getUserId(context)) +
                        " AND " + OrderingTable.TABLE_NAME + "." + OrderingTable.FIELD_ORDER_DATE + " = '" + UtilService.getCurrentDate() + "'";

        return getOrderingTableData(query);
    }

    public void makeOrderOfFood(Map<Integer, OrderingObject> userBuyMeals){
        SQLiteDatabase dbManager = this.getWritableDatabase();
        List<OrderingObject> objectsToInsert = new ArrayList<>();

        List<OrderingObject> currentDayUserOrdering = getOrderingHistoryForToday();
        if (currentDayUserOrdering == null || currentDayUserOrdering.isEmpty()){
            objectsToInsert.addAll(userBuyMeals.values());
            dbManager.execSQL(getQueryInserOrderingObjects(objectsToInsert));
            return;
        }

        for (OrderingObject orderingObj : currentDayUserOrdering){
            if (userBuyMeals.containsKey(orderingObj.getMeal().getMealId())){
                OrderingObject buferOrderingObj = userBuyMeals.get(orderingObj.getMeal().getMealId());

                int newValue = buferOrderingObj.getValue() + orderingObj.getValue();
                int newCost = buferOrderingObj.getCost() + orderingObj.getCost();

                buferOrderingObj.setValue(newValue);
                buferOrderingObj.setCost(newCost);

                userBuyMeals.put(buferOrderingObj.getMeal().getMealId(), buferOrderingObj);
            }
        }

        String delQuery = "DELETE FROM " + OrderingTable.TABLE_NAME +
                " WHERE " + OrderingTable.FIELD_USER_ID + " = " + UtilService.getUserId(context) +
                " AND " + OrderingTable.TABLE_NAME + "." + OrderingTable.FIELD_ORDER_DATE + " = '" + UtilService.getCurrentDate() + "'";
        dbManager.execSQL(delQuery);

        objectsToInsert.addAll(userBuyMeals.values());
        dbManager.execSQL(getQueryInserOrderingObjects(objectsToInsert));
    }

    public void deleteOrderingHistory(int userId){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "DELETE FROM " + OrderingTable.TABLE_NAME +
                " WHERE " + OrderingTable.FIELD_USER_ID + " = " + userId;

        db.execSQL(query);
    }

    private String getQueryAllMealsMenu(){
        return "SELECT " +
                        MenuTable.TABLE_NAME + "." + MenuTable.FIELD_MEAL_ID + ", " +
                        MealsTable.TABLE_NAME + "." + MealsTable.FIELD_NAME + ", " +
                        MealsTable.TABLE_NAME + "." + MealsTable.FIELD_COST + ", " +
                        MealsTable.TABLE_NAME + "." + MealsTable.FIELD_DESCRIPTION + ", " +
                        MealsTypeTable.TABLE_NAME + "." + MealsTypeTable.FIELD_NAME + ", " +
                        DaysOfWeekTable.TABLE_NAME + "." + DaysOfWeekTable.FIELD_DAY +
                " FROM " + MenuTable.TABLE_NAME +
                " INNER JOIN " + MealsTable.TABLE_NAME +
                        " ON " + MealsTable.TABLE_NAME + "." + MealsTable.FIELD_ID + " = " + MenuTable.TABLE_NAME + "." + MenuTable.FIELD_MEAL_ID +
                " INNER JOIN " + DaysOfWeekTable.TABLE_NAME +
                        " ON " + DaysOfWeekTable.TABLE_NAME + "." + DaysOfWeekTable.FIELD_ID + " = " + MenuTable.TABLE_NAME + "." + MenuTable.FIELD_WEEK_DAY_ID +
                " INNER JOIN " + MealsTypeTable.TABLE_NAME +
                        " ON " + MealsTypeTable.TABLE_NAME + "." + MealsTypeTable.FIELD_ID + " = " + MealsTable.TABLE_NAME + "." + MealsTable.FIELD_TYPE;
    }

    private List<MenuObject> getListMenuItems(String query){
        List<MenuObject> mealsMenu = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()){
            do {
                MenuObject menuObject = new MenuObject();
                MealObject mealObject = new MealObject();

                mealObject.setMealId(cursor.getInt(0));
                mealObject.setName(cursor.getString(1));
                mealObject.setCost(cursor.getInt(2));
                mealObject.setDescription(cursor.getString(3));
                mealObject.setType(cursor.getString(4));

                menuObject.setMeal(mealObject);
                menuObject.setDayName(cursor.getString(5));

                mealsMenu.add(menuObject);
            }while (cursor.moveToNext());

            return mealsMenu;
        }
        else {
            return null;
        }
    }

    private String getUserOrderingHistoryQuery(int userId){
        return  "SELECT " +
                OrderingTable.TABLE_NAME + "." + OrderingTable.FIELD_MEAL_ID + ", " +
                MealsTable.TABLE_NAME + "." + MealsTable.FIELD_NAME + ", " +
                MealsTable.TABLE_NAME + "." + MealsTable.FIELD_COST + ", " +
                MealsTable.TABLE_NAME + "." + MealsTable.FIELD_DESCRIPTION + ", " +
                MealsTypeTable.TABLE_NAME + "." + MealsTypeTable.FIELD_NAME + ", " +
                OrderingTable.TABLE_NAME + "." + OrderingTable.FIELD_VALUE + ", " +
                OrderingTable.TABLE_NAME + "." + OrderingTable.FIELD_COST + ", " +
                OrderingTable.TABLE_NAME + "." + OrderingTable.FIELD_ORDER_DATE +
                " FROM " + OrderingTable.TABLE_NAME +
                " INNER JOIN " + MealsTable.TABLE_NAME +
                " ON " + MealsTable.TABLE_NAME + "." + MealsTable.FIELD_ID + " = " + OrderingTable.TABLE_NAME + "." + OrderingTable.FIELD_MEAL_ID+
                " INNER JOIN " + MealsTypeTable.TABLE_NAME +
                " ON " + MealsTypeTable.TABLE_NAME + "." + MealsTypeTable.FIELD_ID + " = " + MealsTable.TABLE_NAME + "." + MealsTable.FIELD_TYPE +
                " WHERE " + OrderingTable.TABLE_NAME + "." + OrderingTable.FIELD_USER_ID + " = " + userId;
    }

    private List<OrderingObject> getOrderingTableData(String query){
        List<OrderingObject> orderingObjectList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()){
            do {
                OrderingObject orderingObj = new OrderingObject();
                MealObject mealObject = new MealObject();

                mealObject.setMealId(cursor.getInt(0));
                mealObject.setName(cursor.getString(1));
                mealObject.setCost(cursor.getInt(2));
                mealObject.setDescription(cursor.getString(3));
                mealObject.setType(cursor.getString(4));

                orderingObj.setMeal(mealObject);
                orderingObj.setValue(cursor.getInt(5));
                orderingObj.setCost(cursor.getInt(6));
                orderingObj.setDate(cursor.getString(7));

                orderingObjectList.add(orderingObj);
            }while (cursor.moveToNext());

            return orderingObjectList;
        }
        else {
            return null;
        }
    }

    private String getQueryInserOrderingObjects(List<OrderingObject> objectsToInsert){
        String query = "INSERT INTO "+ OrderingTable.TABLE_NAME +"(" + OrderingTable.FIELD_USER_ID +", "+ OrderingTable.FIELD_MEAL_ID +", "+ OrderingTable.FIELD_VALUE +", "+ OrderingTable.FIELD_COST +", "+ OrderingTable.FIELD_ORDER_DATE +") VALUES";
        for (int i = 0; i < objectsToInsert.size(); i++){
            if (i == objectsToInsert.size() - 1){
                query += " " + parseOrderingObjToQueryInsert(objectsToInsert.get(i)) + ";";
            }
            else {
                query += " " + parseOrderingObjToQueryInsert(objectsToInsert.get(i)) + ",";
            }
        }

        return query;
    }

    private String parseOrderingObjToQueryInsert(OrderingObject orderingObj){
        return "(" + UtilService.getUserId(context) + "," +
                orderingObj.getMeal().getMealId() + ", " +
                orderingObj.getValue() + ", " +
                orderingObj.getCost() + ", " +
                "'" + orderingObj.getDate() + "')";
    }
}
