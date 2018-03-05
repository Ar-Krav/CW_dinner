package ark.cw_dinner.database.tables.mealsmenu;

public class MenuObject {
    private String mealName;
    private String dayName;
    private Boolean isEnabled;

    public MenuObject(String mealName, String dayName, Boolean isEnabled) {
        this.mealName = mealName;
        this.dayName = dayName;
        this.isEnabled = isEnabled;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
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
