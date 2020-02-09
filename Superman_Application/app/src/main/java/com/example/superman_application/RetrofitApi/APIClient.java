package com.example.superman_application.RetrofitApi;

import com.example.superman_application.TokenHandling.Token;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private static OkHttpClient.Builder httpClient;

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8080")
                    .addConverterFactory(GsonConverterFactory.create());


    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null);
    }

    public static <S> S createService(Class<S> serviceClass, final Token token) {

        if(httpClient == null){
            System.out.println("client null");
            httpClient = new OkHttpClient.Builder();
            if (!token.getToken().contains("null")) {
                System.out.println("Token not null " + token.getToken());
                httpClient.addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request.Builder requestBuilder = original.newBuilder()
                                .header("Accept", "application/json")
                                .header("Authorization", token.getToken())
                                .method(original.method(), original.body());

                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                });
            }
            httpClient.connectTimeout(50, TimeUnit.SECONDS);
            httpClient.addInterceptor(addLogging());
        }

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }



    private static HttpLoggingInterceptor addLogging(){

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }

}

