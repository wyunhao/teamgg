package com.example.vince.eatwise;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vince.eatwise.Utility.LoginInfo;
import com.example.vince.eatwise.Utility.Registration;
import com.example.vince.eatwise.Utility.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * The entry activity of the app.
 * User can login/register from here.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "EmailPassword";

    private EditText mEmail;
    private EditText mPassword;
    private Button mLogin;
    private Button mRegister;
    private FirebaseAuth mAuth;
    private TextView mStatus;
//    private DatabaseReference mRootRef;

    /**
     * Find UI elements related to different fields
     *
     * @param savedInstanceState Saved inputs from the previous login attempts
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Log in");

        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mLogin = (Button) findViewById(R.id.email_sign_in_button);
        mRegister = (Button) findViewById(R.id.email_register_button);
        mStatus = (TextView) findViewById(R.id.status_bar);
        mAuth = FirebaseAuth.getInstance();
//        mRootRef = FirebaseDatabase.getInstance().getReference();


        mLogin.setOnClickListener(this);
        mRegister.setOnClickListener(this);
    }

    /**
     * Check if the user is already signed in to display corresponding interface
     */
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            updateUI(currentUser);
        }
    }

    /**
     * Respond to login successes and login failures
     *
     * @param user the user object authenticated
     */
    private void updateUI(FirebaseUser user) {
        // pass user information to the next Activity: NavigationDrawerActivity
        if (user != null) {
            mStatus.setText("log in successfully");
            Intent intent = new Intent(LoginActivity.this, NavigationDrawerActivity.class);
            startActivity(intent);
        } else {
            mStatus.setText("log in unsuccessful");
        }
    }

    /**
     * Respond to registration successes and registration failures
     *
     * @param user the user object newly created
     */
    private void setDisplayName(FirebaseUser user) {
        if (user != null) {
            mStatus.setText("Create account successfully");
            findViewById(R.id.login_form).setVisibility(View.GONE);
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, new RegisterFragment()).commit();
        } else {
            mStatus.setText("Create account unsuccessful");
        }
    }



    /**
     * syntax level validation on user input
     * @return  if the input is valid
     */
    private boolean validateForm() {
        boolean valid = true;

        String email = mEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmail.setError("Required.");
            valid = false;
        } else {
            mEmail.setError(null);
        }

        String password = mPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPassword.setError("Required.");
            valid = false;
        } else {
            mPassword.setError(null);
        }

        return valid;
    }

    /**
     * Create account with new email address and password combination
     * @param email new email address to be registered
     * @param password the password corresponds to the email
     */

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            setDisplayName(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                            // TODO: catch the exceptions
                            mStatus.setText(task.getException().toString());
                        }
                    }
                });

    }

    /**
     * Attempt to sign in with email with password online when they are both valid and match each other would login succeed
     * @param email
     * @param password
     */

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn" + email);
        if (!validateForm()) {
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            mStatus.setText(R.string.error_sign_in);
                            mStatus.setText(task.getException().toString());
                        }
                    }
                });
    }

    private void signOut() {
        mAuth.signOut();
    }

    /**
     * Click the button will trigger Sign In attempt
     * @param v The search view
     */
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.email_sign_in_button) {
            signIn(mEmail.getText().toString(), mPassword.getText().toString());
        } else if (i == R.id.email_register_button) {
            // TODO: create new user in database
            createAccount(mEmail.getText().toString(), mPassword.getText().toString());
        }
    }
}
