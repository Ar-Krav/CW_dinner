package ark.cw_dinner.mainpart.orderingfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ark.cw_dinner.R;
import ark.cw_dinner.database.DBManager;
import ark.cw_dinner.database.tables.meals.MealObject;
import ark.cw_dinner.database.tables.mealsmenu.MenuObject;
import ark.cw_dinner.database.tables.ordering.OrderingObject;

public class OrderingFragment extends Fragment {

    public static Map<Integer, OrderingObject> selectedMeals;

    public OrderingFragment() {
        selectedMeals = new HashMap<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_ordering, container, false);

        DBManager dbManager = new DBManager(this.getActivity());

        String currentDate = new SimpleDateFormat("EE, dd MMMM").format(new Date());
        String currentDayOfWeek = new SimpleDateFormat("u").format(new Date());

        ((TextView) fragmentView.findViewById(R.id.dateLabel)).setText(currentDate);

        List<MealObject> mealsList = new ArrayList<>();
        for (MenuObject menuItem : dbManager.getMealsMenuByDay(currentDayOfWeek)){
            mealsList.add(menuItem.getMeal());
        }

        OrderingListAdapter orderingListAdapter = new OrderingListAdapter(this.getActivity(), mealsList);
        ListView dayMenuListView = (ListView) fragmentView.findViewById(R.id.orderingListMenu);
        dayMenuListView.setAdapter(orderingListAdapter);

        Button buySelectedItems = (Button) fragmentView.findViewById(R.id.buySelectedItems);
        buySelectedItems.setOnClickListener(getBuyBtnClickListener());

        return fragmentView;
    }

    private View.OnClickListener getBuyBtnClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedMeals.values().isEmpty()){
                    Toast.makeText(getActivity(), "Select Items to buy", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(getActivity(), OrderingConfirmationDialogActivity.class);
                getActivity().startActivity(intent);
            }
        };
    }
}
