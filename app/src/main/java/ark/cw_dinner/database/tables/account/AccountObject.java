package ark.cw_dinner.database.tables.account;

import java.io.Serializable;

public class AccountObject implements Serializable{
    private String name;
    private String lastName;
    private String login;
    private String type;

    public AccountObject(){
    }

    public AccountObject(String name, String lastName, String type) {
        this.name = name;
        this.lastName = lastName;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
