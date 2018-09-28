package com.weather.infraestructure;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;


/**
 * Created by macksuel on 10/6/15.
 */
public class RequestHandler<T> implements Handler<T> {

    private final Context mContext;
    private ProgressDialog mProgressDialog;

    public RequestHandler(Context context) {
        mContext = context;

        mProgressDialog = new ProgressDialog(mContext);
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
        if (error != null && error.getMessage() != null) {
            Log.e("RequestHandler", error.getMessage());
        }

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
