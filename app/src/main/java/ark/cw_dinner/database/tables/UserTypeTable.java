package ark.cw_dinner.database.tables;

public class UserTypeTable {
    public final static String TABLE_NAME= "user_type";

    public final static String FIELD_ID = "id";
    public final static String FIELD_TYPE = "name";

    public final static String CREATION_QUERY= "CREATE TABLE IF NOT EXISTS "+ TABLE_NAME +"(" +
            FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            FIELD_TYPE + " varchar(50) NOT NULL );";

    public final static String DEFAULT_VALUE_QUERY = "INSERT INTO "+ TABLE_NAME +"("+ FIELD_ID +", "+ FIELD_TYPE +") VALUES" +
            "(1, 'admin')," +
            "(2, 'user');";
}
