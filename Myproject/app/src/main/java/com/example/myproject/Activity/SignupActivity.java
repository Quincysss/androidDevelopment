package com.example.myproject.Activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myproject.HashUtil;
import com.example.myproject.R;
import com.example.myproject.RestClient;
import com.example.myproject.model.Credential;
import com.example.myproject.model.Users;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class SignupActivity extends AppCompatActivity {
    static final String TAG = "SignupActivity";
    EditText userName;
    EditText passWord;
    EditText firstName;
    EditText lastName;
    EditText Email;
    EditText Dob;
    EditText Height;
    EditText Weight;
    EditText Address;
    EditText Postcode;
    EditText Step;
    RadioGroup Gender;
    RadioButton genderButton;
    Spinner actLevel;
    Users user;
    Button btnRegister;
    TextView loginLink;
    DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        userName = (EditText) findViewById(R.id.register_Username);
        passWord = (EditText) findViewById(R.id.register_password);
        firstName = (EditText) findViewById(R.id.first_name);
        lastName = (EditText) findViewById(R.id.last_name);
        Email = (EditText) findViewById(R.id.input_email);
        Height = (EditText) findViewById(R.id.height);
        Weight = (EditText) findViewById(R.id.weight);
        Address = (EditText) findViewById(R.id.address);
        Postcode = (EditText) findViewById(R.id.postcode);
        Step = (EditText) findViewById(R.id.step);
        btnRegister = (Button) findViewById(R.id.btn_signup);
        Gender = (RadioGroup) findViewById(R.id.radioGroup1);


        actLevel = (Spinner) findViewById(R.id.actlevel);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.level_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        actLevel.setAdapter(adapter);

        Dob = (EditText) findViewById(R.id.Dob);
        Dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                final int Year = c.get(Calendar.YEAR); // current year
                final int Month = c.get(Calendar.MONTH); // current month
                final int Day = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                DatePickerDialog dateTimePick = new DatePickerDialog(SignupActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Dob.setText((Integer.toString(year) + "-" + Integer.toString(monthOfYear + 1) + "-" + Integer.toString(dayOfMonth)));
                    }
                }, Year, Month, Day);
                dateTimePick.show();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignupExistAsyncTask existUsername = new SignupExistAsyncTask();
                if (!validate()) {
                    onRegisterFailed();
                    return;
                }
                try {
                    if(existUsername.execute(userName.getText().toString()).get()){
                        userName.setError("Username existed!");
                        userName.requestFocus();
                    }else{
                        btnRegister.setEnabled(true);

                        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this);
                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("Creating Account...");
                        progressDialog.show();
                        int selectedId = Gender.getCheckedRadioButtonId();
                        genderButton = (RadioButton) findViewById(selectedId);
                        final String accountname = userName.getText().toString();
                        final String email = Email.getText().toString();
                        final String password = passWord.getText().toString();
                        HashUtil util = new HashUtil();
                        final String hashPassword = util.md5(password);
                        final String username = firstName.getText().toString();
                        final String surname = lastName.getText().toString();
                        final String Birthday = Dob.getText().toString();
                        final String height = Height.getText().toString();
                        final String weight = Weight.getText().toString();
                        final String address = Address.getText().toString();
                        final String postcode = Postcode.getText().toString();
                        final String stepPerMile = Step.getText().toString();
                        final String gender = genderButton.getText().toString();
                        final String levelSpiner = actLevel.getSelectedItem().toString();
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {

                                        PostAsyncTask signUpAsyncTask = new PostAsyncTask();
                                        signUpAsyncTask.execute(username,
                                                surname,Birthday,height,weight,gender,email,address,postcode,levelSpiner,stepPerMile
                                                ,accountname,hashPassword);

                                        progressDialog.dismiss();
                                    }
                                }, 3000);
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public class PostAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            Date currentDate = new Date();Users users = new Users();
            Credential credential =new Credential();DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");Date dob = new Date();
            try {
                dob=formatter.parse(params[2]);
                users = new Users(params[0], params[1],dob, Double.valueOf(params[3]), Double.valueOf(params[4]), params[5], params[6], params[7], params[8], Integer.valueOf(params[9]), Double.valueOf(params[10]));
                int user_id = RestClient.createUsers(users);
                Users userid = new Users(user_id);
                credential = new Credential(userid, params[11], params[12], currentDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            RestClient.createCredential(credential);
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            return "Sign up successfully";
        }
        @Override
        protected void  onPostExecute(String response) {
            Toast.makeText(SignupActivity.this,response,Toast.LENGTH_SHORT).show();
        }
    }
    public void onRegisterFailed() {
        Toast.makeText(getBaseContext(), "Register failed", Toast.LENGTH_LONG).show();
        btnRegister.setEnabled(true);
    }
    private class SignupExistAsyncTask extends  AsyncTask<String,Void,Boolean>
    {
        @Override
        protected  Boolean doInBackground(String...strings)
        {
            if(UsernameExist(strings[0]))
            {
                return true;
            }else{
                return false;
            }
        }
    }
    public static boolean UsernameExist(String username)
    {

        String Result = RestClient.findByUsername(username);
        if (Result.equals("[]")){
            return false;
        } else {
            return true;
        }
    }


    public boolean validate() {
        boolean valid = true;
        String _userName = userName.getText().toString();
        String _Password = passWord.getText().toString();
        String _firstName = firstName.getText().toString();
        String _lastName = lastName.getText().toString();
        String _Address = Address.getText().toString();
        String _Email = Email.getText().toString();
        String _Height = Height.getText().toString();
        String _Weight = Weight.getText().toString();
        String _postCode = Postcode.getText().toString();
        String _Step = Step.getText().toString();
        if (_userName.isEmpty() || _userName.length() < 3) {
            userName.setError("at least 3 characters");
            valid = false;
        } else {
            userName.setError(null);
        }
        if (_Password.isEmpty() || _Password.length() < 4 || _Password.length() > 10) {
            passWord.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passWord.setError(null);
        }
        if (_firstName.isEmpty()) {
            firstName.setError("Enter Valid firstName");
            valid = false;
        } else {
            firstName.setError(null);
        }
        if (_lastName.isEmpty()) {
            lastName.setError("Enter Valid lastName");
            valid = false;
        } else {
            lastName.setError(null);
        }
        if (_Address.isEmpty()) {
            Address.setError("Enter Valid Address");
            valid = false;
        } else {
            Address.setError(null);
        }

        if (_Email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(_Email).matches()) {
            Email.setError("enter a valid email address");
            valid = false;
        } else {
            Email.setError(null);
        }
        if (_Height.isEmpty()) {
            Height.setError("Enter Valid Height");
            valid = false;
        } else {
            Height.setError(null);
        }
        if (_Weight.isEmpty()) {
            Weight.setError("Enter Valid Weight");
            valid = false;
        } else {
            Weight.setError(null);
        }
        if (_postCode.isEmpty()) {
            Postcode.setError("Enter Valid postCode");
            valid = false;
        } else {
            Postcode.setError(null);
        }

        if (_Step.isEmpty()) {
            Step.setError("Enter Valid Step");
            valid = false;
        } else {
            Step.setError(null);
        }

        return valid;
    }
}
