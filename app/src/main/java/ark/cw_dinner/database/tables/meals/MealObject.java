package ark.cw_dinner.database.tables.meals;

import java.util.List;

public class MealObject {
    private int mealId;
    private String name;
    private int cost;
    private String description;
    private String type;
    private List<String> availableInDays;

    public MealObject(){
    }

    public MealObject(String name, int cost, String description, String type) {
        this.name = name;
        this.cost = cost;
        this.description = description;
        this.type = type;
    }

    public int getMealId() {
        return mealId;
    }

    public void setMealId(int mealId) {
        this.mealId = mealId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getAvailableInDays() {
        return availableInDays;
    }

    public void setAvailableInDays(List<String> availableInDays) {
        this.availableInDays = availableInDays;
    }
}
