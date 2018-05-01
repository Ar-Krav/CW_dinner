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

public class TypeSortDishesFragment extends Fragment {
    public TypeSortDishesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_type_sort_dishes, container, false);

        DBManager dbManager = new DBManager(this.getActivity());
        HashMap<String, List<MealObject>> childDataMap = new HashMap<String, List<MealObject>>();
        for (MealObject mealObj : dbManager.getMeals()){
            if (childDataMap.containsKey(mealObj.getType())){
                childDataMap.get(mealObj.getType()).add(mealObj);
            }
            else {
                List<MealObject> newList = new ArrayList<MealObject>();
                newList.add(mealObj);
                childDataMap.put(mealObj.getType(), newList);
            }
        }

        List<String> headerList = new ArrayList<>(childDataMap.keySet());

        ExpandableListView expLV = (ExpandableListView) fragmentView.findViewById(R.id.typeSortExpLV);
        ExpLVAdapter expLVAdapter = new ExpLVAdapter(getContext(), headerList, childDataMap);
        expLV.setAdapter(expLVAdapter);


        return fragmentView;
    }
}
