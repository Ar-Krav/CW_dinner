package ark.cw_dinner.mainpart.orderingfood;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ark.cw_dinner.R;


public class TodayOrderingHistoryFragment extends Fragment {

    public TodayOrderingHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_today_ordering_history, container, false);
    }
}
