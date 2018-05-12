package ark.cw_dinner.mainpart.orderinghistory;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ark.cw_dinner.R;
import ark.cw_dinner.database.DBManager;
import ark.cw_dinner.database.tables.meals.MealObject;
import ark.cw_dinner.database.tables.ordering.OrderingObject;
import ark.cw_dinner.mainpart.foodmenu.ExpLVAdapter;
import ark.cw_dinner.utils.TagsValues;
import ark.cw_dinner.utils.UtilService;

public class HistoryFragment extends Fragment {
    public HistoryFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_history, container, false);

        HashMap<String, List<OrderingObject>> orderingHistoryMap = getSortedHistory();
        if(orderingHistoryMap == null){
            ((TextView) fragmentView.findViewById(R.id.emptyListLabel)).setVisibility(View.VISIBLE);
            return fragmentView;
        }

        List<String> mealsTypeList = new ArrayList<>(orderingHistoryMap.keySet());

        ExpandableListView expLV = (ExpandableListView) fragmentView.findViewById(R.id.orderHistoryELV);
        OrderingHistoryELVAdapter expLVAdapter = new OrderingHistoryELVAdapter(getContext(), mealsTypeList, orderingHistoryMap);
        expLV.setAdapter(expLVAdapter);

        return fragmentView;
    }

    private HashMap<String, List<OrderingObject>> getSortedHistory(){
        HashMap<String, List<OrderingObject>> orderingHistory = new HashMap<>();
        DBManager dbManager = new DBManager(this.getActivity());

        List<OrderingObject> dbOrderingHistory = dbManager.getUserOrderingHistory(UtilService.getUserId(this.getActivity()));
        if (dbOrderingHistory == null){
            return null;
        }

        for (OrderingObject orderingObj : dbOrderingHistory){
            if (orderingHistory.containsKey(orderingObj.getDate())){
                orderingHistory.get(orderingObj.getDate()).add(orderingObj);
            }
            else {
                List<OrderingObject> newList = new ArrayList<OrderingObject>();
                newList.add(orderingObj);
                orderingHistory.put(orderingObj.getDate(), newList);
            }
        }

        return orderingHistory;
    }

    private int getUserId(){
        int userID = this.getActivity().getSharedPreferences(TagsValues.LOGINED_USER_PREFERENCES_NAME, Context.MODE_PRIVATE).getInt(TagsValues.LOGINED_USER_PREFERENCES, -1);
        Log.d("USER_TAG_TEST", "getUserId: " + userID);
        return userID;
    }
}
