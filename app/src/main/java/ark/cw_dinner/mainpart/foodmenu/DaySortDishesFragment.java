package ark.cw_dinner.mainpart.foodmenu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ark.cw_dinner.R;
import ark.cw_dinner.database.DBManager;
import ark.cw_dinner.database.tables.meals.MealObject;
import ark.cw_dinner.database.tables.mealsmenu.MenuObject;

public class DaySortDishesFragment extends Fragment {
    public DaySortDishesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_day_sort_dishes, container, false);

        HashMap<String, List<MealObject>> mealsDaysMap = getMealsSortedByDays();
        List<String> daysOfWeek = new ArrayList<>();
            daysOfWeek.add("Monday");
            daysOfWeek.add("Tuesday");
            daysOfWeek.add("Wednesday");
            daysOfWeek.add("Thursday");
            daysOfWeek.add("Friday");
            daysOfWeek.add("Sunday");
            daysOfWeek.add("Saturday");

        ExpandableListView expLV = (ExpandableListView) fragmentView.findViewById(R.id.daySortExpLV);
        ExpLVAdapter expLVAdapter = new ExpLVAdapter(getContext(), daysOfWeek, mealsDaysMap);
        expLV.setAdapter(expLVAdapter);

        return fragmentView;
    }


    private HashMap<String, List<MealObject>> getMealsSortedByDays(){
        HashMap<String, List<MealObject>> mealsDaysMap = new HashMap<>();
        DBManager dbManager = new DBManager(this.getActivity());

        for (MenuObject menuObj : dbManager.getMealsMenu()){
            if (mealsDaysMap.containsKey(menuObj.getDayName())){
                mealsDaysMap.get(menuObj.getDayName()).add(menuObj.getMeal());
            }
            else {
                List<MealObject> mealObjects = new ArrayList<>();
                mealObjects.add(menuObj.getMeal());

                mealsDaysMap.put(menuObj.getDayName(), mealObjects);
            }
        }

        return mealsDaysMap;
    }
}
