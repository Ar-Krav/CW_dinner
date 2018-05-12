package ark.cw_dinner.mainpart;

import android.content.res.Configuration;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import ark.cw_dinner.R;
import ark.cw_dinner.database.DBManager;
import ark.cw_dinner.database.tables.account.AccountObject;
import ark.cw_dinner.database.tables.ordering.OrderingObject;
import ark.cw_dinner.mainpart.foodmenu.FoodMenuFragment;
import ark.cw_dinner.mainpart.orderinghistory.HistoryFragment;
import ark.cw_dinner.mainpart.fragments.OrderingFragment;
import ark.cw_dinner.utils.TagsValues;
import ark.cw_dinner.utils.UtilService;

public class BasicActivity extends AppCompatActivity {
    String TEST_TAG = "AppMainActivity_DEBUG_TEST";

    Boolean isUserAdmin;
    Fragment selectedFragment;

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private MenuItem prewiousSelectedItem;
    private Handler mHandler;

    private Menu appMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        AccountObject loginedUser = (AccountObject) getIntent().getSerializableExtra(TagsValues.LOGINED_USER_EXTRAS);
        isUserAdmin = loginedUser.getType() == TagsValues.ACCOUNT_TYPE_ADMIN;

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Home");

        mHandler = new Handler();

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this,mDrawer,toolbar,R.string.drawer_open,R.string.drawer_close);
        mDrawer.addDrawerListener(drawerToggle);

        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        nvDrawer.setNavigationItemSelectedListener(getNavMenuListener());

        prewiousSelectedItem = nvDrawer.getMenu().getItem(0);
        nvDrawer.getMenu().findItem(R.id.admin_area).setVisible(isUserAdmin);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_area, new OrderingFragment())
                .commit();


        /**
        * DB TEST DEBUG
        * */
        /*DBManager dbManager = new DBManager(this);
        for (OrderingObject menuObject : dbManager.getUserOrderingHistory(1)){
            Log.d(TEST_TAG, "onCreate: " + menuObject.getMeal());
            Log.d(TEST_TAG, "onCreate: " + menuObject.getDate());
            Log.d(TEST_TAG, "onCreate: " + menuObject.getCost());
            Log.d(TEST_TAG, "onCreate: " + menuObject.getValue());
        }*/
    }


    private NavigationView.OnNavigationItemSelectedListener getNavMenuListener(){
        return new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == prewiousSelectedItem.getItemId()) return false;
                prewiousSelectedItem = item;

                appMenu.findItem(R.id.delete_ordering_history).setVisible(false);

                item.setChecked(true);
                setTitle(item.getTitle());

                switch (item.getItemId()){
                    case R.id.nav_ordering:{
                        selectedFragment = new OrderingFragment();
                        break;
                    }

                    case R.id.nav_food_menu:{
                        selectedFragment = new FoodMenuFragment();
                        break;
                    }

                    case R.id.nav_ordering_history:{
                        appMenu.findItem(R.id.delete_ordering_history).setVisible(true);
                        selectedFragment = new HistoryFragment();
                        break;
                    }
                    default: selectedFragment = null;
                }

                Runnable mPendingRunnable = null;
                if (selectedFragment != null){
                    mPendingRunnable = new Runnable() {
                        @Override
                        public void run() {
                            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                                    android.R.anim.fade_out);
                            fragmentTransaction.replace(R.id.fragment_area, selectedFragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                    };
                }

                if (mPendingRunnable != null) {
                    mHandler.post(mPendingRunnable);
                }

                mDrawer.closeDrawers();

                return true;
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_menu, menu);
        appMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete_ordering_history){
            DBManager dbManager = new DBManager(this);
            dbManager.deleteOrderingHistory(UtilService.getUserId(this));
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_area, new HistoryFragment())
                    .commit();

            Toast.makeText(this, "History deleted", Toast.LENGTH_SHORT).show();
        }

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        else{
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }
}
