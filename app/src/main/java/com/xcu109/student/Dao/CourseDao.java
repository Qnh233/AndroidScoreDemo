package com.xcu109.student.Dao;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xcu109.student.Entity.Course;
import com.xcu109.student.untility.CallbackFuture;
import com.xcu109.student.untility.HttpUtility;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * 对course表进行数据库操作
 */

public class CourseDao {


    private Handler handler;
    private static final String TAG = "CourseDao";

    // 列定义
    private final String[] ORDER_COLUMNS = new String[] {"course_id","course_name","credit"};

    private Context context;

    static Gson gson = new Gson();

    static List<Course> courseList;

    public CourseDao(Context context) {
        this.context = context;
    }

    /**
     * 判断表中是否有数据
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean isDataExist(){
        try {
            CallbackFuture future =new CallbackFuture();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(HttpUtility.getUrl() +"/Course/exist").get().build();
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
     * 查询课程信息
     */
//    public List<Course> getCourse(){
//        SQLiteDatabase db = null;
//        Cursor cursor = null;
//        String sql = "select * from course";
//        try {
//            db = dbHelper.getReadableDatabase();
//            cursor = db.rawQuery(sql,null);
//            //cursor = db.rawQuery("select * from course",null);
//           /* cursor = db.query(dbHelper.TABLE_NAME,
//                    ORDER_COLUMNS,
//                    "course_name = ?",
//                    new String[] {"math"},
//                    null, null, null);*/
//            if (cursor.getCount() > 0) {
//                List<Course> courseList = new ArrayList<Course>(cursor.getCount());
//                while (cursor.moveToNext()) {
//                    Course course = parseCourse(cursor);
//                    courseList.add(course);
//                }
//                return courseList;
//            }
//        }
//        catch (Exception e) {
//            Log.e(TAG, "", e);
//        }
//        finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//            if (db != null) {
//                db.close();
//            }
//        }
//
//        return null;
//    }

    //异步不需要创建线程
    static List<Course> ls;
    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Course> getCourse() {
        System.out.println("!!!!!!!!");
        CallbackFuture future =new CallbackFuture();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(HttpUtility.getUrl()+"/Course/list").get().build();
        client.newCall(request).enqueue(future);
        try {
            Response response = future.get();
            if (response==null)
            {
                return courseList;
            }
            String respondDate = response.body().string();
            System.out.println(respondDate);
            Log.d(TAG, "onResponse: " + respondDate);
            courseList = gson.fromJson(respondDate, new TypeToken<List<Course>>() {
            }.getType());
            for (Course c :
                    courseList) {
                System.out.println(c.getImageId());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return courseList;
    }

    /**
     * 添加课程
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean addCourse(Course course) {
        try {
            String toJson = gson.toJson(course);
            RequestBody body = RequestBody.create(
                    MediaType.parse("application/json"), toJson);
            CallbackFuture future =new CallbackFuture();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(HttpUtility.getUrl()+"/Course").put(body).build();
            client.newCall(request).enqueue(future);
            String string = future.get().body().string();
            System.out.println(string);
            if(string.equals("true"))
            {
                return true;
            }
        }catch (SQLiteConstraintException e){
            Toast.makeText(context, "主键重复", Toast.LENGTH_SHORT).show();
            return false;
        }catch (Exception e){
            Log.e(TAG, "", e);
        }
        return false;

    }
    /**
     * 删除课程,以课程id
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean deleteCourse(int courseId) {
        try {
            CallbackFuture future =new CallbackFuture();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(HttpUtility.getUrl()+"/Course?courseId="+courseId).delete().build();
            client.newCall(request).enqueue(future);
            String string = future.get().body().string();
            if(string.equals("true"))
            {
                return true;
            }
            return true;
        }catch (SQLiteConstraintException e){
            //Toast.makeText(context, "主键重复", Toast.LENGTH_SHORT).show();
            return false;
        }catch (Exception e){
            Log.e(TAG, "删除课程发生异常", e);
        }
        return false;
    }

}
