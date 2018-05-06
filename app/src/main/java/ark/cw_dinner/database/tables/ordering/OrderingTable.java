package ark.cw_dinner.database.tables.ordering;

import ark.cw_dinner.database.tables.account.AccountsTable;
import ark.cw_dinner.database.tables.meals.MealsTable;

public class OrderingTable {
    public final static String TABLE_NAME= "ordering";

    public final static String FIELD_ID = "id";
    public final static String FIELD_USER_ID = "user_id";
    public final static String FIELD_MEAL_ID = "meal_id";
    public final static String FIELD_VALUE = "value";
    public final static String FIELD_COST = "cost";
    public final static String FIELD_ORDER_DATE = "order_date";

    public final static String CREATION_QUERY = "CREATE TABLE IF NOT EXISTS 'ordering' (" +
            FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            FIELD_USER_ID + " INTEGER(10) NOT NULL," +
            FIELD_MEAL_ID + " INTEGER(10) NOT NULL," +
            FIELD_VALUE + " INTEGER(10) NOT NULL," +
            FIELD_COST + " INTEGER(10) NOT NULL," +
            FIELD_ORDER_DATE + " date NOT NULL," +
            "FOREIGN KEY ("+ FIELD_MEAL_ID +") REFERENCES "+ MealsTable.TABLE_NAME +" ("+ MealsTable.FIELD_ID +") ON DELETE NO ACTION ON UPDATE CASCADE," +
            "FOREIGN KEY ("+ FIELD_USER_ID +") REFERENCES "+ AccountsTable.TABLE_NAME +" ("+ AccountsTable.FIELD_ID +") ON DELETE NO ACTION ON UPDATE CASCADE );";
}
