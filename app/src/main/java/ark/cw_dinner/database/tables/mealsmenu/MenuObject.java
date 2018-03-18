package ark.cw_dinner.database.tables.mealsmenu;

import ark.cw_dinner.database.tables.meals.MealObject;

public class MenuObject {
    private MealObject meal;
    private String dayName;
    private Boolean isEnabled;

    public MenuObject() {
    }

    public MenuObject(MealObject meal, String dayName, Boolean isEnabled) {
        this.meal = meal;
        this.dayName = dayName;
        this.isEnabled = isEnabled;
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

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }
}
