package ark.cw_dinner.launcherpart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ark.cw_dinner.R;
import ark.cw_dinner.database.DBManager;
import ark.cw_dinner.database.tables.account.AccountObject;
import ark.cw_dinner.mainpart.BaseAppActivity;
import ark.cw_dinner.utils.TagsValues;

public class SignInActivity extends AppCompatActivity {
    private String TEST_TAG = "SignInActivity_DEBUG_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        EditText loginField = (EditText) findViewById(R.id.login_field);
        EditText passwdField= (EditText) findViewById(R.id.passwd_field);

        Button signInBtn = (Button) findViewById(R.id.login_button);
            signInBtn.setOnClickListener(getLoginBtnListener(loginField, passwdField));

        Button registrateBtn = (Button) findViewById(R.id.registrate_button);
            registrateBtn.setOnClickListener(getRegistrateBtnListener());
    }

    private View.OnClickListener getRegistrateBtnListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                SignInActivity.this.startActivity(intent);
            }
        };
    }

    private View.OnClickListener getLoginBtnListener(final EditText loginField, final EditText passwdField){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = loginField .getText().toString();
                String passwd= passwdField.getText().toString();

                new CheckuserLoginAsync().execute(login,passwd);
            }
        };
    }

    private class CheckuserLoginAsync extends AsyncTask<String,Void,AccountObject>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected AccountObject doInBackground(String... strings) {

            //TODO change back!!!!!!
            String login = strings[0];
            String passwd = strings[1];

            /*String login = "ark";
            String passwd = "12345ark";*/

            DBManager dbManager = new DBManager(SignInActivity.this);

            return dbManager.getLoginedUser(login, passwd);
        }

        @Override
        protected void onPostExecute(AccountObject loginedUser) {
            super.onPostExecute(loginedUser);

            if (loginedUser != null){
                saveInSharedPreference(TagsValues.LOGINED_USER_ID_PREFERENCES, loginedUser.getUserId());
                saveInSharedPreference(TagsValues.LOGINED_USER_TYPE_PREFERENCE, loginedUser.getType());

                Intent intent = new Intent(SignInActivity.this, BaseAppActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                SignInActivity.this.startActivity(intent);
                SignInActivity.this.finish();
            }
            else {
                Toast.makeText(SignInActivity.this, "Login or password are incorrect", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveInSharedPreference(String tagValue, int savedValue){
        SharedPreferences loginedUserIdPreference = getSharedPreferences(TagsValues.SHARED_PREFERENCE_NAME,MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = loginedUserIdPreference.edit();
        prefEditor.putInt(tagValue, savedValue);
        prefEditor.apply();
    }
}
