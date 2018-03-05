package ark.cw_dinner.database.tables;

public class DaysOfWeekTable {
    public final static String TABLE_NAME= "days_week";

    public final static String FIELD_ID= "id";
    public final static String FIELD_DAY= "day";

    public final static String CREATION_QUERY= "CREATE TABLE IF NOT EXISTS "+ TABLE_NAME +"(" +
            FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            FIELD_DAY + " varchar(20) NOT NULL );";

    public final static String DEFAULT_VALUE_QUERY = "INSERT INTO "+ TABLE_NAME +"("+ FIELD_ID +", "+ FIELD_DAY +") VALUES" +
            "(1, 'Monday')," +
            "(2, 'Tuesday')," +
            "(3, 'Wednesday')," +
            "(4, 'Thursday')," +
            "(5, 'Friday')," +
            "(6, 'Saturday')," +
            "(7, 'Sunday');";
}
