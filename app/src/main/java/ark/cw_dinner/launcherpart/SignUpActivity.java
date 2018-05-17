package ark.cw_dinner.launcherpart;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ark.cw_dinner.R;
import ark.cw_dinner.database.DBManager;
import ark.cw_dinner.database.tables.account.AccountObject;

public class SignUpActivity extends AppCompatActivity {
    private final String TEST_TAG = "signUpActivity__TEST";

    EditText nameField;
    EditText lastNameField;
    EditText loginField;
    EditText passwdField;

    Button signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        nameField = (EditText) findViewById(R.id.user_name_field);
        lastNameField = (EditText) findViewById(R.id.user_last_name_field);
        loginField = (EditText) findViewById(R.id.user_login_field);
        passwdField = (EditText) findViewById(R.id.user_passwd_field);

        signUpBtn = (Button) findViewById(R.id.sign_up_btn);
        signUpBtn.setOnClickListener(getSignUpBtnListener());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SignUpActivity.this.finish();

        return true;
    }

    private View.OnClickListener getSignUpBtnListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CreateNewUserAsync().execute();
            }
        };
    }

    private class CreateNewUserAsync extends AsyncTask<Void, Void, Boolean>{
        AccountObject newUser;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            DBManager dbManager = new DBManager(SignUpActivity.this);
            newUser = new AccountObject(nameField.getText().toString(),
                                        lastNameField.getText().toString(),
                                        loginField.getText().toString(),
                                        passwdField.getText().toString()
                                       );

            return dbManager.insertNewAccount(newUser);
        }

        @Override
        protected void onPostExecute(Boolean isUserCreated) {
            super.onPostExecute(isUserCreated);

            if (isUserCreated){
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                SignUpActivity.this.startActivity(intent);
            }
            else{
                loginField.setText("");
                passwdField.setText("");

                Toast.makeText(SignUpActivity.this, "Such login has been taken by other user", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
