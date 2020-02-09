package com.example.superman_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.superman_application.Interfaces.ILogin;
import com.example.superman_application.Presenter.LoginPresenter;
import com.example.superman_application.TokenHandling.CreateToken;

public class LoginActivity extends AppCompatActivity implements ILogin {
    EditText cEmail, cPassword;
    Button cLogin;

    @Override
    protected void onStart() {
        super.onStart();
        LoginPresenter loginPresenter = new LoginPresenter(this);
        cEmail = (EditText) findViewById(R.id.InputEmail);
        cPassword = (EditText)findViewById(R.id.InputPassword);
        cLogin = (Button)findViewById(R.id.btnLogin);

        cLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateToken createToken = new CreateToken();
                createToken.execute(cEmail.getText().toString(), cPassword.getText().toString());
                LoginUser();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void LoginUser() {
        Intent goToActivity = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(goToActivity);
    }
}
