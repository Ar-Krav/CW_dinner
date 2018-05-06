package ark.cw_dinner.mainpart.foodmenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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

public class TypeSortDishesFragment extends Fragment {
    public TypeSortDishesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_type_sort_dishes, container, false);

        HashMap<String, List<MealObject>> mealsTypeMap = getMealsSortedByType();
        List<String> mealsTypeList = new ArrayList<>(mealsTypeMap.keySet());

        ExpandableListView expLV = (ExpandableListView) fragmentView.findViewById(R.id.typeSortExpLV);
        ExpLVAdapter expLVAdapter = new ExpLVAdapter(getContext(), mealsTypeList, mealsTypeMap);
        expLV.setAdapter(expLVAdapter);

        return fragmentView;
    }

    private HashMap<String, List<MealObject>> getMealsSortedByType(){
        HashMap<String, List<MealObject>> mealsTypeMap = new HashMap<String, List<MealObject>>();
        for (MealObject mealObj : getMealsInMenu()){
            if (mealsTypeMap.containsKey(mealObj.getType())){
                mealsTypeMap.get(mealObj.getType()).add(mealObj);
            }
            else {
                List<MealObject> newList = new ArrayList<MealObject>();
                newList.add(mealObj);
                mealsTypeMap.put(mealObj.getType(), newList);
            }
        }

        return mealsTypeMap;
    }

    private List<MealObject> getMealsInMenu(){
        DBManager dbManager = new DBManager(this.getActivity());

        HashMap<Integer, MealObject> mealsInMenu = new HashMap<>();
        for (MenuObject menuObj : dbManager.getMealsMenu()){
            MealObject mealObj = menuObj.getMeal();

            if (mealsInMenu.containsKey(mealObj.getMealId())){
                List<String> daysAvailable = mealsInMenu.get(mealObj.getMealId()).getAvailableInDays();
                daysAvailable.add(menuObj.getDayName());
            }
            else {
                List<String> daysNameList = new ArrayList<>();
                daysNameList.add(menuObj.getDayName());
                mealObj.setAvailableInDays(daysNameList);

                mealsInMenu.put(mealObj.getMealId(), mealObj);
            }
        }

        return new ArrayList<>(mealsInMenu.values());
    }
}
