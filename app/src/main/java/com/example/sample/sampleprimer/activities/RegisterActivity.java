package com.example.sample.sampleprimer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sample.sampleprimer.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputLayout tilEmailId;
    TextInputLayout tilPassword;
    EditText etEmailId;
    EditText etPassword;

    TextView tvSignIn;
    Button btnSignUp;
    TextView tvResetPassword;

    ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();

        tilEmailId = (TextInputLayout) findViewById(R.id.activity_register_til_email_id);
        tilPassword = (TextInputLayout) findViewById(R.id.activity_register_til_password);
        etEmailId = (EditText) findViewById(R.id.activity_register_et_email_id);
        etPassword = (EditText) findViewById(R.id.activity_register_et_password);
        tvSignIn = (TextView) findViewById(R.id.activity_register_tv_sign_in);
        btnSignUp = (Button) findViewById(R.id.activity_register_btn_sign_up);
        tvResetPassword = (TextView) findViewById(R.id.activity_register_tv_reset_password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        btnSignUp.setOnClickListener(this);
        tvSignIn.setOnClickListener(this);
        tvResetPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_register_btn_sign_up:
                String email = etEmailId.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                credentialValidation(email, password);

                break;
            case R.id.activity_register_tv_sign_in:
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                break;
            case R.id.activity_register_tv_reset_password:
                startActivity(new Intent(RegisterActivity.this, ResetPassword.class));
                break;
        }
    }

    private void credentialValidation(final String email, final String password) {
        if (TextUtils.isEmpty(email)) {
            tilEmailId.setError(getResources().getString(R.string.email_empty_error));
            return;
        } else {
            tilEmailId.setError(null);
        }
        if (TextUtils.isEmpty(password)) {
            tilPassword.setError(getResources().getString(R.string.password_empty_error));
            return;
        }
        if (password.length() < 6) {
            tilPassword.setError(getResources().getString(R.string.password_short_error));
            return;
        } else {
            tilPassword.setError(null);
        }

        progressBar.setVisibility(View.VISIBLE);

        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                if (firebaseUser != null) {
                    if (firebaseUser.isEmailVerified()) {
                        createUserWithEmailAndPassword(email, password);
                    } else {
                        firebaseUser.sendEmailVerification();
                    }
                }
            }
        });
    }

    private void createUserWithEmailAndPassword(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);

                        if (task.isSuccessful()) {
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                            finish();
                        } else {
                            tilEmailId.setError(getResources().getString(R.string.email_password_error));
                        }
                    }
                });

    }
}
