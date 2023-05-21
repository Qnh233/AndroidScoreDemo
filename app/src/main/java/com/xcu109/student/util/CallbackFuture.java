package com.xcu109.student.util;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


@RequiresApi(api = Build.VERSION_CODES.N)
public
class CallbackFuture extends CompletableFuture<Response> implements Callback {
    public void onResponse(Call call, Response response) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            super.complete(response);
        }
    }
    public void onFailure(Call call, IOException e){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            super.completeExceptionally(e);
        }
    }
}