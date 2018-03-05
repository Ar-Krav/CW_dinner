package ark.cw_dinner.database.tables.meals;

public class MealObject {
    private String name;
    private int cost;
    private String description;
    private String type;

    public MealObject(String name, int cost, String description, String type) {
        this.name = name;
        this.cost = cost;
        this.description = description;
        this.type = type;
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
}
