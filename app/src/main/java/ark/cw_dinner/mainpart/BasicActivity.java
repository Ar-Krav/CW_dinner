package ark.cw_dinner.mainpart;

import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import ark.cw_dinner.R;
import ark.cw_dinner.database.tables.account.AccountObject;
import ark.cw_dinner.utils.TagsValues;

public class BasicActivity extends AppCompatActivity {
    String TEST_TAG = "AppMainActivity_DEBUG_TEST";

    Boolean isUserAdmin;

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private MenuItem prewiousSelectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        AccountObject loginedUser = (AccountObject) getIntent().getSerializableExtra(TagsValues.LOGINED_USER_EXTRAS);
        isUserAdmin = loginedUser.getType() == TagsValues.ACCOUNT_TYPE_ADMIN;

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Home");

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this,mDrawer,toolbar,R.string.drawer_open,R.string.drawer_close);
        mDrawer.addDrawerListener(drawerToggle);

        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        nvDrawer.setNavigationItemSelectedListener(getNavMenuListener());

        prewiousSelectedItem = nvDrawer.getMenu().getItem(0);
        nvDrawer.getMenu().findItem(R.id.admin_area).setVisible(isUserAdmin);

    }


    private NavigationView.OnNavigationItemSelectedListener getNavMenuListener(){
        return new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == prewiousSelectedItem.getItemId()) return false;

                prewiousSelectedItem = item;

                item.setChecked(true);

                setTitle(item.getTitle());
                mDrawer.closeDrawers();

                return true;
            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
