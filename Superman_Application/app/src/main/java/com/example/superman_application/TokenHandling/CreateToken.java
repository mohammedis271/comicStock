package com.example.superman_application.TokenHandling;

import android.os.AsyncTask;

public class CreateToken extends AsyncTask<String,Void,Void> {
        @Override
        protected Void doInBackground(String... strings) {
            Token token =new Token();
            token.getAccessTokenFromUser(strings[0],strings[1]);
            return null;
        }
    }
