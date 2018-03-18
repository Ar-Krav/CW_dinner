package ark.cw_dinner.mainpart.foodmenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import ark.cw_dinner.R;

public class FoodMenuFragment extends Fragment {

    public FoodMenuFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentLayot =  inflater.inflate(R.layout.fragment_food_menu, container, false);

        BottomNavigationView navigation = (BottomNavigationView) fragmentLayot.findViewById(R.id.bottom_navigator);
        navigation.setOnNavigationItemSelectedListener(getNavigatorListener());

        return fragmentLayot;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener getNavigatorListener(){
        return new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.bottom_navigation_all:{
                        fragment = new AllDishesFragment();
                        break;
                    }

                    case R.id.bottom_navigation_by_day:{
                        fragment = new DaySortDishesFragment();
                        break;
                    }

                    case R.id.bottom_navigation_by_type:{
                        fragment = new TypeSortDishesFragment();
                        break;
                    }

                    default: return false;
                }

                loadFragment(fragment);
                return true;
            }
        };
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.navigator_fragment_area, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
