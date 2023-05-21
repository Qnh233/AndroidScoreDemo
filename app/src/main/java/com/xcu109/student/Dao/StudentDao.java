package com.xcu109.student.Dao;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xcu109.student.Entity.Student;
import com.xcu109.student.Entity.Student_info;
import com.xcu109.student.R;
import com.xcu109.student.util.CallbackFuture;
import com.xcu109.student.util.HttpUtil;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 对student表进行数据库操作
 */
@RequiresApi(api = Build.VERSION_CODES.N)
public class StudentDao {
    private static final String TAG = "StudentDao";

    // 列定义
    private final String[] ORDER_COLUMNS = new String[] {"id","name","sex","age","phone"};

    private Context context;

    static Gson gson = new Gson();

    public StudentDao(Context context) {
        this.context = context;
    }
    /**
     * 判断表中是否有数据
     */
    public boolean isDataExist(){
        try {
            CallbackFuture future =new CallbackFuture();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(HttpUtil.getUrl() +"/Student/exist").get().build();
            client.newCall(request).enqueue(future);
            Response response = future.get();
            String string = response.body().string();
            int count = Integer.parseInt(string);
            if(count>0)
            {
                return true;
            }
        }
        catch (Exception e) {
            Log.e(TAG, "", e);
        }

        return false;
    }



    /**
     * 根据学生Id查询学生信息
     */
    public List<Student_info> getStudent(Long id){
        try {
            CallbackFuture future =new CallbackFuture();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(HttpUtil.getUrl() +"/Student/"+id).get().build();
            client.newCall(request).enqueue(future);
            Response response = future.get();
            String string = response.body().string();
           Student student = gson.fromJson(string, new TypeToken<Student>() {
            }.getType());
            System.out.println(student.getId());
                ArrayList<Student_info> StudentList = new ArrayList<>(5);
                Student_info sid = new Student_info(R.drawable.id,Long.toString(student.getId()));
                StudentList.add(sid);
                Student_info sname = new Student_info(R.drawable.name,student.getName());
                StudentList.add(sname);
                Student_info sex = new Student_info(R.drawable.sex,student.getSex());
                StudentList.add(sex);
                Student_info age = new Student_info(R.drawable.age,Integer.toString(student.getAge()));
                StudentList.add(age);
                Student_info phone = new Student_info(R.drawable.phone,student.getPhone());
                StudentList.add(phone);
                return StudentList;
            }  catch (Exception e) {
            Log.e(TAG, "", e);
        }
        return null;
    }

    /**
     * 更新学生信息,学生用户自己修改个人信息,根据Id进行更新，无法更改Id
     */
    public boolean updateStudent(Student student){
        SQLiteDatabase db = null;
        try {
            CallbackFuture future =new CallbackFuture();
            OkHttpClient client = new OkHttpClient();
            String toJson = gson.toJson(student);
            RequestBody body = RequestBody.create(
                    MediaType.parse("application/json"), toJson);
            Request request = new Request.Builder().url(HttpUtil.getUrl() +"/Student").post(body).build();
            client.newCall(request).enqueue(future);
            Response response = future.get();
            String string = response.body().string();
            if (string.equals("true"))
            {
                return true;
            }
        }catch (Exception e) {
            Log.e(TAG, "", e);
        }
        return false;
    }

    /**
     * 插入学生信息，用户注册
     */
    public boolean insertStudent(Student student){
        try {
            CallbackFuture future =new CallbackFuture();
            OkHttpClient client = new OkHttpClient();
            String toJson = gson.toJson(student);
            RequestBody body = RequestBody.create(
                    MediaType.parse("application/json"), toJson);
            Request request = new Request.Builder().url(HttpUtil.getUrl() +"/Student").put(body).build();
            client.newCall(request).enqueue(future);
            Response response = future.get();
            String string = response.body().string();
            if (string.equals("true"))
            {
                return true;
            }
            return true;
        }catch (SQLiteConstraintException e){
            Toast.makeText(context, "主键重复", Toast.LENGTH_SHORT).show();
            return false;
        }catch (Exception e){
            Log.e(TAG, "", e);
        }
        return false;
    }
}
