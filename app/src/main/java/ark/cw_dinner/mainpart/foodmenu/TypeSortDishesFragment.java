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
import ark.cw_dinner.utils.UtilService;

public class TypeSortDishesFragment extends Fragment {
    public TypeSortDishesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_type_sort_dishes, container, false);

        HashMap<String, List<MealObject>> mealsTypeMap = new UtilService().getMealsSortedByType(this.getActivity());
        List<String> mealsTypeList = new ArrayList<>(mealsTypeMap.keySet());

        ExpandableListView expLV = (ExpandableListView) fragmentView.findViewById(R.id.typeSortExpLV);
        ExpLVAdapter expLVAdapter = new ExpLVAdapter(getContext(), mealsTypeList, mealsTypeMap);
        expLV.setAdapter(expLVAdapter);

        return fragmentView;
    }
}
