package ark.cw_dinner.mainpart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import ark.cw_dinner.R;
import ark.cw_dinner.database.tables.account.AccountObject;
import ark.cw_dinner.utils.TagsValues;

public class AppMainActivity extends AppCompatActivity {

    String TEST_TAG = "AppMainActivity_DEBUG_TEST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_main);

        AccountObject loginedUser = (AccountObject) getIntent().getSerializableExtra(TagsValues.LOGINED_USER_EXTRAS);

        Log.d(TEST_TAG, "loginUser: " + loginedUser.getType() + "  |   logU: " + loginedUser.getName());
    }
}
