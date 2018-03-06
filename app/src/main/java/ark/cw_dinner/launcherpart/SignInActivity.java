package ark.cw_dinner.launcherpart;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ark.cw_dinner.R;
import ark.cw_dinner.database.DBManager;
import ark.cw_dinner.database.tables.account.AccountObject;
import ark.cw_dinner.mainpart.AppMainActivity;
import ark.cw_dinner.utils.TagsValues;

public class SignInActivity extends AppCompatActivity {
    private String TEST_TAG = "SignInActivity_DEBUG_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        EditText loginField = (EditText) findViewById(R.id.login_field);
        EditText passwdField = (EditText) findViewById(R.id.passwd_field);

        Button signInBtn = (Button) findViewById(R.id.login_button);
            signInBtn.setOnClickListener(getLoginBtnListener(loginField, passwdField));
    }


    private View.OnClickListener getLoginBtnListener(final EditText loginField, final EditText passwdField){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = loginField.getText().toString();
                String passwd = passwdField.getText().toString();

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
            String login = strings[0];
            String passwd = strings[1];

            DBManager dbManager = new DBManager(SignInActivity.this);

            return dbManager.getLoginedUser(login, passwd);
        }

        @Override
        protected void onPostExecute(AccountObject loginedUser) {
            super.onPostExecute(loginedUser);

            Intent intent;

            if (loginedUser != null){
                intent = new Intent(SignInActivity.this, AppMainActivity.class);
                intent.putExtra(TagsValues.LOGINED_USER_EXTRAS, loginedUser);
            }
            else {
                intent = new Intent(SignInActivity.this, SignUpActivity.class);
            }

            SignInActivity.this.startActivity(intent);
        }
    }
}
