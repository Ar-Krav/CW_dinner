package ark.cw_dinner.database.tables.account;

import ark.cw_dinner.utils.TagsValues;

/**
 * Created by Ar-Krav on 17.05.2018.
 */

public class AccountTrigger {
    final static public String TRIGGER_NAME = "account_trigger_name";
    final static public String TRIGGER_TYPE = "AFTER INSERT";
    final static public String TRIGGER_CONDITION = "(SELECT COUNT(" + AccountsTable.FIELD_ID + ") % " + TagsValues.NUM_OF_USER_TO_WIN + " FROM " + AccountsTable.TABLE_NAME + ") == 0";
    final static public String TRIGGER_BODY  = "UPDATE " + AccountsTable.TABLE_NAME +
                                                " SET " + AccountsTable.FIELD_TYPE + " = " + TagsValues.ACCOUNT_TYPE_VINNER +
                                                " WHERE " + AccountsTable.FIELD_ID + " = new." + AccountsTable.FIELD_ID + ";";

    final static public String CREATE_QUERY = "CREATE TRIGGER " + TRIGGER_NAME + " " + TRIGGER_TYPE +
            " ON " + AccountsTable.TABLE_NAME +
            " WHEN " + TRIGGER_CONDITION +
            " BEGIN " +
                TRIGGER_BODY +
            " END;";
}
