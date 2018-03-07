package ark.cw_dinner.database;

import android.content.ContentValues;
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
import ark.cw_dinner.utils.TagsValues;

public class DBManager extends SQLiteOpenHelper {
    private String TEST_TAG = "DBManager_DEBUG_TEST";


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

    /**
       Methods for working with DB
     */

    public AccountObject getLoginedUser(String login, String passwd){
        AccountObject loginedUser = new AccountObject();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " +
                            AccountsTable.FIELD_NAME + ", " +
                            AccountsTable.FIELD_LAST_NAME + ", " +
                            AccountsTable.FIELD_LOGIN + ", " +
                            AccountsTable.FIELD_TYPE +
                       " FROM " + AccountsTable.TABLE_NAME +
                       " WHERE " + AccountsTable.FIELD_LOGIN + " = '" + login + "' AND " + AccountsTable.FIELD_PASSWD + " = '" + passwd + "';";


        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()){
            do {
                loginedUser.setName(cursor.getString(0));
                loginedUser.setLastName(cursor.getString(1));
                loginedUser.setLogin(cursor.getString(2));
                loginedUser.setType(cursor.getInt(3));
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
}
