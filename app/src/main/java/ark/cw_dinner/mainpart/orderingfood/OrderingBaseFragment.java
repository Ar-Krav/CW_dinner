package ark.cw_dinner.mainpart.orderingfood;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import ark.cw_dinner.R;
import ark.cw_dinner.database.DBManager;
import ark.cw_dinner.database.tables.ordering.OrderingObject;
import ark.cw_dinner.mainpart.BaseAppActivity;


public class OrderingBaseFragment extends Fragment {

    public OrderingBaseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_basic_ordering, container, false);

        FloatingActionButton makeNewOrdering = (FloatingActionButton) fragmentView.findViewById(R.id.makeNewOrder);
        makeNewOrdering.setOnClickListener(getFloatBtnClickListener());

        DBManager dbManager = new DBManager(this.getActivity());
        List<OrderingObject> orderingObjectList = dbManager.getOrderingHistoryForToday();
        //Log.d("VERY_TEST_TAG", "onCreateView: " + orderingObjectList.toString());
        if (orderingObjectList == null){
            ((TextView) fragmentView.findViewById(R.id.emptyListLabel)).setVisibility(View.VISIBLE);
            return fragmentView;
        }

        TodayOrderingHistoryAdapter historyAdapter = new TodayOrderingHistoryAdapter(getActivity(), orderingObjectList);
        ListView historyListView = (ListView) fragmentView.findViewById(R.id.orderingDayHistoryList);
        historyListView.setAdapter(historyAdapter);

        return fragmentView;
    }

    private View.OnClickListener getFloatBtnClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseAppActivity) getActivity()).replaceToOrderingFragment();
            }
        };
    }
}
