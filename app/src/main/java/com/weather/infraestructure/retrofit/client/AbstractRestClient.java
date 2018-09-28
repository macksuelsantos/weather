package com.weather.infraestructure.retrofit.client;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weather.infraestructure.Handler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by macksuelsantos on 11/9/17.
 */

abstract class AbstractRestClient<T> {

    static final String APPID = "903a7eab47f20e0bd580b60ee7760f14";

    private final Activity mActivity;

    AbstractRestClient(Activity activity) {
        this.mActivity = activity;
    }

    private Retrofit createRetrofit() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();


        return new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getHttpClient())
                .build();
    }

    private OkHttpClient getHttpClient() {
        return new OkHttpClient().newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    T createService(Class<T> clazz) {
        return createRetrofit().create(clazz);
    }

    void addEnqueue(Call<T> call, final Handler<T> handler) {
        final long start = System.currentTimeMillis();

        Callback<T> callback = new Callback<T>() {
            @Override
            public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
                if (mActivity.isFinishing()) return;

                try {
                    if (response.isSuccessful()) {
                        handler.onSuccess(response.body());
                    } else {
                        handler.onFail(new Exception(getError(response)));
                    }
                } catch (Exception e) {
                    onFailure(call, e);
                }

                Log.i("Retrofit Request", call.request().url().toString() + " - ms: " + (System.currentTimeMillis() - start));
            }

            @Override
            public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
                if (mActivity.isFinishing()) return;

                handler.onFail(t);

                Log.i("Retrofit Request", call.request().url().toString() + " - ms: " + (System.currentTimeMillis() - start));
            }
        };

        call.enqueue(callback);
    }

    private String getError(Response<T> response) {
        String error = "";
        try {
            ResponseBody responseBody = response.errorBody();
            if (responseBody != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(responseBody.byteStream()));
                error = reader.readLine();
            }
        } catch (Exception ignored) {
        }

        return error;
    }
}
