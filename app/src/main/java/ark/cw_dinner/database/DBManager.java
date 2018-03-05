package ark.cw_dinner.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ark.cw_dinner.database.tables.DaysOfWeekTable;
import ark.cw_dinner.database.tables.MealsTypeTable;
import ark.cw_dinner.database.tables.UserTypeTable;
import ark.cw_dinner.database.tables.account.AccountObject;
import ark.cw_dinner.database.tables.account.AccountsTable;
import ark.cw_dinner.database.tables.meals.MealsTable;
import ark.cw_dinner.database.tables.mealsmenu.MenuTable;
import ark.cw_dinner.database.tables.ordering.OrderingTable;

public class DBManager extends SQLiteOpenHelper {

    private static final String DB_NAME = "cw_dinner_db";
    private static final int DB_VERSION = 1;

    public DBManager(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
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

    public List<String> getAllUsers(){
        List<String> resultList = new ArrayList<>();

        Log.d("TEST_TAG", "in users");

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT meals.name, days_week.day FROM menu " +
                "INNER JOIN meals ON meals.id = menu.meal_id " +
                "INNER JOIN days_week ON days_week.id = menu.week_day_id;";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()){
            do{
                resultList.add(cursor.getString(1));
            }while (cursor.moveToNext());
        }

        return resultList;
    }

    public List<String> getAllUserType(){
        List<String> resultList = new ArrayList<>();

        Log.d("TEST_TAG", "in users");

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + UserTypeTable.TABLE_NAME;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()){
            do{
                resultList.add(cursor.getString(1));
            }while (cursor.moveToNext());
        }

        return resultList;
    }

    public List<AccountObject> getDBrawQueryResult(){
        List<AccountObject> resultList = new ArrayList<>();

        Log.d("TEST_TAG", "in users");

        SQLiteDatabase db = this.getWritableDatabase();


        String query = "Select " + AccountsTable.TABLE_NAME + "." + AccountsTable.FIELD_NAME +", " + UserTypeTable.TABLE_NAME + "." + UserTypeTable.FIELD_TYPE +" from " + AccountsTable.TABLE_NAME +
                " INNER JOIN " + UserTypeTable.TABLE_NAME + " ON "+ UserTypeTable.TABLE_NAME + "." + UserTypeTable.FIELD_ID + " = " + AccountsTable.TABLE_NAME + "." + AccountsTable.FIELD_TYPE + ";";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()){
            do{
                AccountObject ao = new AccountObject();
                ao.setName(cursor.getString(0));
                ao.setType(cursor.getString(1));
                resultList.add(ao);
            }while (cursor.moveToNext());
        }

        return resultList;
    }

    /*public void addUSerInfo(String userName){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
            contentValues.put(DB_TABLE_FIELD_NAME, userName);

        db.insert(DB_TABLE_NAME, null, contentValues);
        db.close();
    }*/

    /*public List<String> getAllUsers(){
        List<String> resultList = new ArrayList<>();

        Log.d("TEST_TAG", "in users");

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + DB_TABLE_NAME;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()){
            do{
                resultList.add(cursor.getString(1));
            }while (cursor.moveToNext());
        }

        return resultList;
    }*/
}
