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

import com.example.sample.sampleprimer.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity implements View.OnClickListener {

    TextInputLayout tilEmail;
    EditText etEmail;
    Button btnResetPassword;
    Button btnBack;
    ProgressBar progressBar;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        firebaseAuth = FirebaseAuth.getInstance();

        tilEmail = (TextInputLayout) findViewById(R.id.activity_reset_password_til_email);
        etEmail = (EditText) findViewById(R.id.activity_reset_password_et_email);
        btnResetPassword = (Button) findViewById(R.id.activity_reset_password_btn_reset_password);
        btnBack = (Button) findViewById(R.id.activity_reset_password_btn_back);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        btnResetPassword.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_reset_password_btn_reset_password:
                String emailId = etEmail.getText().toString().trim();

                credentialValidation(emailId);
                break;
            case R.id.activity_reset_password_btn_back:
                break;
            default:
                finish();
                break;
        }
    }

    private void credentialValidation(String emailId) {
        if (TextUtils.isEmpty(emailId)) {
            tilEmail.setError(getResources().getString(R.string.email_empty_error));
            return;
        } else {
            tilEmail.setError(null);
        }

        progressBar.setVisibility(View.VISIBLE);

        firebaseAuth.sendPasswordResetEmail(emailId)
                .addOnCompleteListener(ResetPassword.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            startActivity(new Intent(ResetPassword.this, RegisterActivity.class));
                            finish();
                        } else
                            tilEmail.setError(getResources().getString(R.string.email_empty_error));
                    }
                });
    }
}
