package ark.cw_dinner.database.tables.meals;

import ark.cw_dinner.database.tables.MealsTypeTable;

public class MealsTable {
    public final static String TABLE_NAME= "meals";

    public final static String FIELD_ID = "id";
    public final static String FIELD_NAME = "name";
    public final static String FIELD_COST = "cost";
    public final static String FIELD_DESCRIPTION = "description";
    public final static String FIELD_TYPE = "type";

    public final static String CREATION_QUERY= "CREATE TABLE IF NOT EXISTS "+ TABLE_NAME +"(" +
            FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            FIELD_NAME + " varchar(100) NOT NULL DEFAULT ''," +
            FIELD_COST + " INTEGER(10) NOT NULL DEFAULT '0'," + /*TODO fix to decimal for coins!*/
            FIELD_DESCRIPTION + " varchar(255) DEFAULT ''," +
            FIELD_TYPE + " INTEGER(10) NOT NULL," +
            "  FOREIGN KEY ("+ FIELD_TYPE +") REFERENCES "+ MealsTypeTable.TABLE_NAME +" ("+ MealsTypeTable.FIELD_ID +") ON UPDATE CASCADE );";

    public final static String DEFAULT_VALUE_QUERY = "INSERT INTO "+ TABLE_NAME +"("+ FIELD_ID +", "+ FIELD_NAME +", "+ FIELD_COST +", "+ FIELD_DESCRIPTION +", "+ FIELD_TYPE +") VALUES" +
            "(2, 'borsch with meat', 15, 'borshch with meat', 1)," +
            "(3, 'cream soup with mushrooms', 15, 'taste soup', 1)," +
            "(4, 'soup with meat', 20, 'taste soup with meat', 1)," +
            "(5, 'grechka with meat', 25, 'grechka', 2)," +
            "(6, 'plov with meat', 25, 'plov', 2)," +
            "(7, 'rus wiht paluchku s salad', 18, 'rus', 2)," +
            "(8, 'greek salad', 18, 'greek salad', 3)," +
            "(9, 'cesar with meat', 16, 'cesar', 3)," +
            "(10, 'lite salad', 18, 'ogirku s pomidoru s kartopla', 3)," +
            "(11, 'oladku', 13, 'oladku s jabluka', 4)," +
            "(12, 'sharlotka', 15, 'sharlotka with jabluka', 4)," +
            "(13, 'surna zapikanka', 20, 'surna zapikanka', 4);";
}
