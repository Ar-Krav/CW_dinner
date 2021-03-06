package ark.cw_dinner.mainpart;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import ark.cw_dinner.R;
import ark.cw_dinner.database.DBManager;
import ark.cw_dinner.database.tables.ordering.OrderingObject;
import ark.cw_dinner.mainpart.adminpanel.AdminPanelFragment;
import ark.cw_dinner.mainpart.foodmenu.FoodMenuFragment;
import ark.cw_dinner.mainpart.orderingfood.OrderingBaseFragment;
import ark.cw_dinner.mainpart.orderinghistory.HistoryFragment;
import ark.cw_dinner.mainpart.orderingfood.OrderingFragment;
import ark.cw_dinner.utils.TagsValues;
import ark.cw_dinner.utils.UtilService;

public class BaseAppActivity extends AppCompatActivity {
    String TEST_TAG = "AppMainActivity_DEBUG_TEST";

    Boolean isUserAdmin;
    Fragment selectedFragment;
    BaseAppActivity thisActivity = this;

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

        isUserAdmin = UtilService.getCurrentUserType(this) == TagsValues.ACCOUNT_TYPE_ADMIN;

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Ordering");

        mHandler = new Handler();

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this,mDrawer,toolbar,R.string.drawer_open,R.string.drawer_close);
        mDrawer.addDrawerListener(drawerToggle);

        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        nvDrawer.setNavigationItemSelectedListener(getNavMenuListener());

        prewiousSelectedItem = nvDrawer.getMenu().getItem(0);
        nvDrawer.getMenu().findItem(R.id.admin_area).setVisible(isUserAdmin);

        placeBaseOrderingFragment();

        /**
        * DB TEST DEBUG
        * */
        DBManager dbManager = new DBManager(this);
        /*for (OrderingObject menuObject : dbManager.getUserOrderingHistory(UtilService.getCurrentUserId(this))){
            Log.d(TEST_TAG, "onCreate: " + menuObject.getMeal().getName());
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
                        selectedFragment = new OrderingBaseFragment();
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
                    case R.id.nav_admin_panel:{
                        selectedFragment = new AdminPanelFragment();
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
            showConfirmHistoryDelDialog();
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

    private void showConfirmHistoryDelDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(thisActivity);

        builder.setTitle("Delete history")
                .setMessage("Do you want delete all history")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Yes",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        DBManager dbManager = new DBManager(thisActivity);
                        dbManager.deleteOrderingHistory(UtilService.getCurrentUserId(thisActivity));
                        thisActivity.getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_area, new HistoryFragment())
                                .commit();

                        Toast.makeText(thisActivity, "History deleted", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    public void replaceToOrderingFragment(){
        thisActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_area, new OrderingFragment())
                .commit();
    }

    public void placeBaseOrderingFragment(){
        thisActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_area, new OrderingBaseFragment())
                .commit();
    }
}
