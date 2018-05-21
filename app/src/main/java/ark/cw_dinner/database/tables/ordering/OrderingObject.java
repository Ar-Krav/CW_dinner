package ark.cw_dinner.database.tables.ordering;

import ark.cw_dinner.database.tables.meals.MealObject;

public class OrderingObject {
    private String userName;
    private MealObject meal;
    private int value;
    private double cost;
    private String date;

    public OrderingObject() {}

    public OrderingObject(String userName, MealObject meal, int value, int cost, String date) {
        this.userName = userName;
        this.meal = meal;
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

    public MealObject getMeal() {
        return meal;
    }

    public void setMeal(MealObject meal) {
        this.meal = meal;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
