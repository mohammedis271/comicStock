package com.example.superman_application.Presenter;

import android.os.AsyncTask;

import com.example.superman_application.Controller.ICustomerController;
import com.example.superman_application.DTO.LoginDTO;
import com.example.superman_application.Interfaces.ILogin;
import com.example.superman_application.RetrofitApi.APIClient;
import com.example.superman_application.TokenHandling.Token;

import java.io.IOException;

import retrofit2.Call;

public class LoginPresenter implements ILoginPresenter {
    final ILogin iLogin;


    public LoginPresenter(ILogin iLogin) {
        this.iLogin = iLogin;
    }

    @Override
    public void loginUser(LoginDTO loginDTO) {
        LoginUserTask loginUserTask = new LoginUserTask(iLogin);
        loginUserTask.execute(loginDTO);
    }



    private static class LoginUserTask extends AsyncTask<LoginDTO,Void,Integer> {
        private ILogin iLogin;

        private LoginUserTask(ILogin iLogin) {

            this.iLogin = iLogin;
        }
        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            iLogin.LoginUser();
        }

        @Override
        protected Integer doInBackground(LoginDTO... loginDTOS) {
            ICustomerController iLoginController = APIClient.createService(ICustomerController.class, Token.getInstance());
            Call<Integer> call = iLoginController.loginUser(loginDTOS[0]);
            try {
                return call.execute().body();
            } catch (IOException e){
                e.printStackTrace();
            }
            return -1;
        }
    }
}


