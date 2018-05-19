package ark.cw_dinner.database.tables.mealsmenu;

import ark.cw_dinner.database.tables.meals.MealObject;

public class MenuObject {
    private MealObject meal;
    private String dayName;

    public MenuObject() {
    }

    public MenuObject(MealObject meal, String dayName) {
        this.meal = meal;
        this.dayName = dayName;
    }

    public MealObject getMeal() {
        return meal;
    }

    public void setMeal(MealObject mealName) {
        this.meal = mealName;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }
}
