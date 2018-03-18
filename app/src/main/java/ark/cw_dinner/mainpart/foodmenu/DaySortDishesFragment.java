package ark.cw_dinner.mainpart.foodmenu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ark.cw_dinner.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DaySortDishesFragment extends Fragment {


    public DaySortDishesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_day_sort_dishes, container, false);
    }

}
