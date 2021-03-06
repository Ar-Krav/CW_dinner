package ark.cw_dinner.database.tables.account;

import ark.cw_dinner.database.tables.UserTypeTable;

public class AccountsTable {
    public final static String TABLE_NAME= "accounts";

    public final static String FIELD_ID = "id";
    public final static String FIELD_NAME = "name";
    public final static String FIELD_LAST_NAME = "last_name";
    public final static String FIELD_LOGIN = "login";
    public final static String FIELD_PASSWD = "passwd";
    public final static String FIELD_TYPE = "user_type_id";

    public final static String CREATION_QUERY= "CREATE TABLE IF NOT EXISTS "+ TABLE_NAME +"(" +
            FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT  NOT NULL," +
            FIELD_NAME + " varchar(50) NOT NULL," +
            FIELD_LAST_NAME + " varchar(50) NOT NULL," +
            FIELD_LOGIN + " varchar(20) NOT NULL UNIQUE," +
            FIELD_PASSWD + " varchar(30) NOT NULL," +
            FIELD_TYPE + " INTEGER NOT NULL," +
            "FOREIGN KEY ("+ FIELD_TYPE +") REFERENCES "+ UserTypeTable.TABLE_NAME +" ("+ UserTypeTable.FIELD_ID +") ON DELETE NO ACTION ON UPDATE CASCADE);";

    public final static String DEFAULT_VALUE_QUERY = "INSERT INTO "+ TABLE_NAME /*+"("+ FIELD_ID +", "+ FIELD_NAME +", "+ FIELD_LAST_NAME +", "+ FIELD_TYPE */+ " VALUES" +
            "(1, 'Artem', 'Kravchenko', 'ark', '12345ark', 1)," +
            "(2, 'Olexiy', 'Pogribnyak', 'olp', '12345olp', 2)," +
            "(3, 'Vitaliy', 'Odinisov', 'vlo', '12345vlo', 2);";
}
