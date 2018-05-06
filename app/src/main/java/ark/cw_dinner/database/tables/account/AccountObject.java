package ark.cw_dinner.database.tables.account;

import java.io.Serializable;

public class AccountObject implements Serializable{
    private int userId;
    private String name;
    private String lastName;
    private String login;
    private String passwd;
    private int typeID;

    public AccountObject(){
    }

    public AccountObject(String name, String lastName, String login, String passwd) {
        this.name = name;
        this.lastName = lastName;
        this.login = login;
        this.passwd = passwd;
    }

    public AccountObject(String name, String lastName, String login, String passwd, int typeID) {
        this.name = name;
        this.lastName = lastName;
        this.login = login;
        this.passwd = passwd;
        this.typeID = typeID;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public int getType() {
        return typeID;
    }

    public void setType(int type) {
        this.typeID = type;
    }
}
