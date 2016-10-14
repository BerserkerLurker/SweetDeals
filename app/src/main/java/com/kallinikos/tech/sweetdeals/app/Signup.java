package com.kallinikos.tech.sweetdeals.app;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kallinikos.tech.sweetdeals.R;

import java.util.concurrent.ExecutionException;

public class Signup extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    private EditText realnameText;
    private EditText usernameText;
    private EditText mobileText;
    private EditText emailText;
    private EditText passwordText;
    private Button signupButton;
    private TextView loginLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        realnameText = (EditText)findViewById(R.id.input_realname);
        usernameText = (EditText)findViewById(R.id.input_username);
        mobileText = (EditText)findViewById(R.id.input_mobile);
        emailText = (EditText)findViewById(R.id.input_email);
        passwordText = (EditText)findViewById(R.id.input_password);
        signupButton = (Button)findViewById(R.id.btn_signup);
        loginLink = (TextView)findViewById(R.id.link_login);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    public void signup() {
        Log.d(TAG,"Signup");

        if(!validate()){
            onSignupFailed();
            return;
        }
        signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(Signup.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String realname = realnameText.getText().toString();
        String username = usernameText.getText().toString();
        String mobile = mobileText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        String result = "";


        // Implement your own signup logic here.
        BackgroundEngine backgroundEngine = new BackgroundEngine(this);
        try {
            result = backgroundEngine.execute(TAG, realname, username, mobile, email, password).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        //------------------
        final String finalResult = result;
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        if (finalResult.equals("success")) {
                            onSignupSuccess();
                        }else{
                            onSignupFailed();
                        }
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    public void onSignupSuccess() {
        signupButton.setEnabled(true);
        setResult(RESULT_OK,null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Signup failed", Toast.LENGTH_LONG).show();

        signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String realname = realnameText.getText().toString();
        String username = usernameText.getText().toString();
        String mobile = mobileText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (realname.isEmpty() || realname.length() < 3) {
            realnameText.setError("at least 3 characters");
            valid = false;
        } else {
            realnameText.setError(null);
        }

        if (username.isEmpty() || username.length() < 3) {
            usernameText.setError("at least 3 characters");
            valid = false;
        } else {
            usernameText.setError(null);
        }

        if (mobile.isEmpty() || mobile.length() < 8) {
            mobileText.setError("at least 8 digits");
            valid = false;
        } else {
            mobileText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 20) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }
}
