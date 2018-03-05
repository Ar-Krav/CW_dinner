package ark.cw_dinner.database.tables;

public class MealsTypeTable {
    public final static String TABLE_NAME= "meals_type";

    public final static String FIELD_ID = "id";
    public final static String FIELD_NAME = "name";

    public final static String CREATION_QUERY= "CREATE TABLE IF NOT EXISTS "+ TABLE_NAME +"(" +
            FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            FIELD_NAME + " varchar(50) NOT NULL );";

    public final static String DEFAULT_VALUE_QUERY = "INSERT INTO "+ TABLE_NAME +"("+ FIELD_ID +", "+ FIELD_NAME +") VALUES" +
            "(1, 'first')," +
            "(2, 'second')," +
            "(3, 'salad')," +
            "(4, 'desert');";
}
