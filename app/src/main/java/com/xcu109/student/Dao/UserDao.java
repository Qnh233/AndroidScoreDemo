package com.xcu109.student.Dao;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.google.gson.Gson;
import com.xcu109.student.Entity.User;
import com.xcu109.student.untility.CallbackFuture;
import com.xcu109.student.untility.HttpUtility;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 对user表进行数据库操作
 */
@RequiresApi(api = Build.VERSION_CODES.N)
public class UserDao {
    private Context context;

    private Gson gson=new Gson();

    public UserDao(Context context) {
        this.context = context;
    }

    /**
     * 检查登录表中是否有对应的账号
     */

    public boolean check(Long id, String password) {
        try {

            CallbackFuture future =new CallbackFuture();
        OkHttpClient client = new OkHttpClient();
        User user = new User(id, password);
        String toJson = gson.toJson(user);
        RequestBody body = RequestBody.create(
                MediaType.parse("application/json"), toJson);
        Request request = new Request.Builder().url(HttpUtility.getUrl() +"/User").post(body).build();
        client.newCall(request).enqueue(future);
        Response response = future.get();
        String string = response.body().string();
        if(string.equals("true"))
            {
                return true;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    /**
     * 向表中插入数据
     */
    public boolean insert(Long studentId, String password){
        try {
            CallbackFuture future =new CallbackFuture();
            OkHttpClient client = new OkHttpClient();
            User user = new User(studentId, password);
            String toJson = gson.toJson(user);
            RequestBody body = RequestBody.create(
                    MediaType.parse("application/json"), toJson);
            Request request = new Request.Builder().url(HttpUtility.getUrl() +"/User").put(body).build();
            client.newCall(request).enqueue(future);
            Response response = future.get();;
            String string = response.body().string();
            if(string.equals("true"))
            {
                return true;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    /**
     * 修改密码
     */
    public boolean modifyPasswd(User user,String newPasswd){
        try {

            CallbackFuture future =new CallbackFuture();
            OkHttpClient client = new OkHttpClient();
            boolean check = this.check(user.getId(), user.getPassword());
            if (!check)
            {
                return false;
            }
            user.setPassword(newPasswd);
            String toJson = gson.toJson(user);
            RequestBody body = RequestBody.create(
                    MediaType.parse("application/json"), toJson);
            Request request = new Request.Builder().url(HttpUtility.getUrl() +"/User").put(body).build();
            client.newCall(request).enqueue(future);
            Response response  = future.get();;
            String string = response.body().string();
            if(string.equals("true"))
            {
                return true;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}