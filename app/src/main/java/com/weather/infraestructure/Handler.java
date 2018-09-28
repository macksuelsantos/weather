package com.weather.infraestructure;

/**
 * Created by macksuelsantos on 11/13/17.
 */

public interface Handler<T> {

    void onSuccess(T response);

    void onFail(Throwable error);
}
