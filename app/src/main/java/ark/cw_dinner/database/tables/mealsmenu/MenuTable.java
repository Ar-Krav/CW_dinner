package ark.cw_dinner.database.tables.mealsmenu;

import ark.cw_dinner.database.tables.DaysOfWeekTable;
import ark.cw_dinner.database.tables.meals.MealsTable;

public class MenuTable {
    public final static String TABLE_NAME= "menu";

    public final static String FIELD_ID = "id";
    public final static String FIELD_MEAL_ID = "meal_id";
    public final static String FIELD_WEEK_DAY_ID = "week_day_id";
    public final static String FIELD_ENABLED = "enabled";

    public final static String CREATION_QUERY= "CREATE TABLE IF NOT EXISTS "+ TABLE_NAME +"(" +
            FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            FIELD_MEAL_ID + " INTEGER(10) NOT NULL DEFAULT '0'," +
            FIELD_WEEK_DAY_ID + " INTEGER(10) NOT NULL DEFAULT '0'," +
            //FIELD_ENABLED + " INTEGER(10) NOT NULL DEFAULT '0'," +
            "FOREIGN KEY ("+ FIELD_WEEK_DAY_ID +") REFERENCES "+ DaysOfWeekTable.TABLE_NAME +"("+ DaysOfWeekTable.FIELD_ID +") ON UPDATE CASCADE," +
            "FOREIGN KEY ("+ FIELD_MEAL_ID +") REFERENCES "+ MealsTable.TABLE_NAME +"("+ MealsTable.FIELD_ID +") ON DELETE CASCADE ON UPDATE CASCADE );";

    public final static String DEFAULT_VALUE_QUERY = "INSERT INTO "+ TABLE_NAME +"("+ FIELD_ID +", "+ FIELD_MEAL_ID +", "+ FIELD_WEEK_DAY_ID +/*", "+ FIELD_ENABLED +*/") VALUES" +
            "(1, 3, 1)," +
            "(2, 5, 1)," +
            "(3, 9, 2)," +
            "(4, 7, 2)," +
            "(6, 10, 3)," +
            "(7, 12, 3)," +
            "(8, 2, 4)," +
            "(9, 13, 4)," +
            "(10, 8, 5)," +
            "(11, 4, 5)," +
            "(12, 6, 1)," +
            "(13, 11, 2);";
}
