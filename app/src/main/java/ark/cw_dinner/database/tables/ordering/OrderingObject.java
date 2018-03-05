package ark.cw_dinner.database.tables.ordering;

public class OrderingObject {
    private String userName;
    private String mealName;
    private int value;
    private int cost;
    private String date;

    public OrderingObject(String userName, String mealName, int value, int cost, String date) {
        this.userName = userName;
        this.mealName = mealName;
        this.value = value;
        this.cost = cost;
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
