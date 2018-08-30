package com.isysnext.medviewmd.medviewconnect.utils;

import android.content.Context;

import com.isysnext.medviewmd.medviewconnect.doctor.AppointmentFragment;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by anupamchugh on 05/01/17.
 */
public class APIClient {

    private static Retrofit retrofit = null;
    private  AppSession appSession;
    String newSession;
    private Context context;


    public static Retrofit getClient() {


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
       // OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("Authorization","Bearer "+ AppointmentFragment.token.getToken())
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });
        OkHttpClient client1 = client.build();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://medviewmd-api.noemaplatform.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client1)
                .build();

        return retrofit;
    }

}
