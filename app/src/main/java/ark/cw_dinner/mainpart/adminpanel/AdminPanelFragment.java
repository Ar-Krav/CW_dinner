package ark.cw_dinner.mainpart.adminpanel;

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
import ark.cw_dinner.mainpart.foodmenu.ExpLVAdapter;
import ark.cw_dinner.utils.UtilService;

public class AdminPanelFragment extends Fragment {
    public AdminPanelFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_admin_panel, container, false);

        HashMap<String, List<MealObject>> mealsTypeMap = new UtilService().getMealsSortedByType(this.getActivity());
        List<String> mealsTypeList = new ArrayList<>(mealsTypeMap.keySet());

        ExpandableListView expLV = (ExpandableListView) fragmentView.findViewById(R.id.mealsList);
        AdminPanelAdapter expLVAdapter = new AdminPanelAdapter(getContext(), mealsTypeList, mealsTypeMap);
        expLV.setAdapter(expLVAdapter);

        return fragmentView;
    }
}
