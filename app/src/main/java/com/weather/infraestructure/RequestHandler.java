package com.weather.infraestructure;

import android.app.ProgressDialog;
import android.content.Context;


/**
 * Created by macksuel on 10/6/15.
 */
public class RequestHandler<T> implements Handler<T> {

    private final ProgressDialog mProgressDialog;

    public RequestHandler(Context context) {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);

        showProgressDialog();
    }

    @Override
    public void onSuccess(T response) {
        dismissProgressDialog();
    }

    @Override
    public void onFail(Throwable error) {
        dismissProgressDialog();
    }

    private void showProgressDialog() {
        try {
            if (mProgressDialog != null && !mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }
        } catch (Exception ignored) {
        }
    }

    private void dismissProgressDialog() {
        try {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        } catch (Exception ignored) {
        }
    }
}
