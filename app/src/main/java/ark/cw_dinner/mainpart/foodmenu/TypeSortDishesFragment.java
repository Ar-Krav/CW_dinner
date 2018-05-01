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

public class TypeSortDishesFragment extends Fragment {
    public TypeSortDishesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_type_sort_dishes, container, false);


        List<String> headerList = new ArrayList<>();
        headerList.add("Point 1");
        headerList.add("Point 2");
        headerList.add("Point 3");
        headerList.add("Point 4");


        HashMap<String, List<String>> childDataMap = new HashMap<>();

        List<String> childList1 = new ArrayList<>();
        childList1.add("Point 1.1");
        childList1.add("Point 1.2");
        childList1.add("Point 1.3");
        childList1.add("Point 1.4");
        childDataMap.put(headerList.get(0), childList1);

        List<String> childList2 = new ArrayList<>();
        childList2.add("Point 2.1");
        childList2.add("Point 2.2");
        childList2.add("Point 2.3");
        childList2.add("Point 2.4");
        childDataMap.put(headerList.get(1), childList2);

        List<String> childList3 = new ArrayList<>();
        childList3.add("Point 3.1");
        childList3.add("Point 3.2");
        childList3.add("Point 3.3");
        childList3.add("Point 3.4");
        childDataMap.put(headerList.get(2), childList3);

        List<String> childList4 = new ArrayList<>();
        childList4.add("Point 1.1");
        childList4.add("Point 1.2");
        childList4.add("Point 1.3");
        childList4.add("Point 1.4");
        childDataMap.put(headerList.get(3), childList4);


        ExpandableListView expLV = (ExpandableListView) fragmentView.findViewById(R.id.typeSortExpLV);
        ExpLVAdapter expLVAdapter = new ExpLVAdapter(getContext(), headerList, childDataMap);
        expLV.setAdapter(expLVAdapter);


        return fragmentView;
    }
}
