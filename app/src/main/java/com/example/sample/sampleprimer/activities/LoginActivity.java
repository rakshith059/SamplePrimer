package com.example.sample.sampleprimer.activities;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    TextInputLayout tilEmailId;
    TextInputLayout tilPassword;
    EditText etEmailId;
    EditText etPassword;

    Button btnSignIn;
    TextView tvRegister;
    TextView tvForgotPassword;

    ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        tilEmailId = (TextInputLayout) findViewById(R.id.activity_login_til_email_id);
        tilPassword = (TextInputLayout) findViewById(R.id.activity_login_til_password);
        etEmailId = (EditText) findViewById(R.id.activity_login_et_email_id);
        etPassword = (EditText) findViewById(R.id.activity_login_et_password);
        btnSignIn = (Button) findViewById(R.id.activity_login_btn_login);
        tvRegister = (TextView) findViewById(R.id.activity_login_tv_register);
        tvForgotPassword = (TextView) findViewById(R.id.activity_login_tv_forgot_password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        tvRegister.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_login_btn_login:
                String email = etEmailId.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                credentialValidation(email, password);

                break;
            case R.id.activity_login_tv_register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.activity_login_tv_forgot_password:
                startActivity(new Intent(LoginActivity.this, ResetPassword.class));
                break;
        }
    }

    private void credentialValidation(String email, String password) {
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
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);

                        if (task.isSuccessful()) {
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } else {
                            tilEmailId.setError(getResources().getString(R.string.email_password_error));
                        }
                    }
                });
    }
}
