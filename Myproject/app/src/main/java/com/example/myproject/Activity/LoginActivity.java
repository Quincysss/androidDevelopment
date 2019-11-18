package com.example.myproject.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myproject.HashUtil;
import com.example.myproject.R;
import com.example.myproject.RestClient;
import com.example.myproject.model.Users;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    EditText _Username;
    EditText _Password;
    Button _LoginButton;
    TextView _SignupLink;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        _Username = (EditText) findViewById(R.id.input_username);
        _Password = (EditText) findViewById(R.id.input_password);
        _LoginButton = (Button) findViewById(R.id.btn_login);
        _SignupLink = (TextView) findViewById(R.id.link_signup);
        _LoginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });
        _SignupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });


    }
    public void login()
    {
        Log.d(TAG, "Login");
        if (!validate())
        {
            return;
        }
        _LoginButton.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        String accountname = _Username.getText().toString();
                        String password = _Password.getText().toString();
                        HashUtil util = new HashUtil();
                        String hashPassword = util.md5(password);
                        // On complete call either onLoginSuccess or onLoginFailed
                        LoginAsyncTask LoginAsyncTask = new LoginAsyncTask();
                        LoginAsyncTask.execute(accountname,hashPassword);
                        // onLoginFailed();
                        onLoginSuccess();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    private class LoginAsyncTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... params) {
            //String _credential = RestClient.findByUsername(params[0]);
            String username = params[0];
            String password = params[1];
            String result = RestClient.findByUsernameandPassword(username,password);
            try {
                // JSONArray credentialJsonArray = null;
                JSONArray credentialJsonArray = new JSONArray(result);
                JSONObject credentialJson = credentialJsonArray.getJSONObject(0);
                if(!result.equals("[]"))
                {
                    String userid = credentialJson.getString("userid");
                    JSONObject userJson = new JSONObject(userid);
                    // JSONObject userJson = userJsonArray.getJSONObject(0);
                    String _userid = userJson.getString("userid");
                    String _firstrname = userJson.getString("firstname");
                    String _surname = userJson.getString("surname");
                    String _Email = userJson.getString("EMail");
                    String _Dob = userJson.getString("dob");
                    String _Height = userJson.getString("height");
                    String _Weight = userJson.getString("weight");
                    String _Gender = userJson.getString("gender");
                    String _Address = userJson.getString("address");
                    String _Actlevel = userJson.getString("actlevel");
                    String _Steps = userJson.getString("steps");

                    Integer userid_int = Integer.valueOf(_userid);
                    Double height_double = Double.valueOf(_Height);
                    Double weight_double = Double.valueOf(_Weight);
                    Integer Actlevel_int = Integer.valueOf(_Actlevel);
                    Integer Steps_int = Integer.valueOf(_Steps);
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    Date Dob_date = null;
                    try {
                        Dob_date = df.parse(_Dob);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Users newUser = new Users(userid_int);
                    newUser.setAddress(_Address);
                    newUser.setDob(Dob_date);
                    newUser.setEMail(_Email);
                    newUser.setGender(_Gender);
                    newUser.setHeight(height_double);
                    newUser.setWeight(weight_double);
                    newUser.setActlevel(Actlevel_int);
                    newUser.setFirstname(_firstrname);
                    newUser.setSurname(_surname);
                    newUser.setSteps(Steps_int);

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putParcelable("newUsers",newUser);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else{
                    onLoginFailed();
                    return "Login fail";
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
            return "finish";


        }

        @Override
        protected void onPostExecute(String result){
            _Username.setText(result);
        }
    }
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }
    public void onLoginSuccess() {
        _LoginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _LoginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String username = _Username.getText().toString();
        String password = _Password.getText().toString();

        if (username.isEmpty()) {
            _Username.setError("account name can not be empty");
            valid = false;
        } else {
            _Username.setError(null);
        }

        if (password.isEmpty()) {
            _Password.setError("account password can not be empty");
            valid = false;
        } else {
            _Password.setError(null);
        }

        return valid;
    }
}