package com.xcu109.student.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xcu109.student.Entity.Course;
import com.xcu109.student.Entity.CourseSec;
import com.xcu109.student.Entity.Score;
import com.xcu109.student.util.CallbackFuture;
import com.xcu109.student.util.HttpUtil;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 对Score表进行数据库操作
 */
@RequiresApi(api = Build.VERSION_CODES.N)
public class ScoreDao {
    private static final String TAG = "ScoreDao";
    private Context context;

    private static List<Course> course;

    Gson gson = new Gson();
    public ScoreDao(Context context) {
        this.context = context;
    }
    /**
     * 判断表中是否有数据
     */

    public boolean isDataExist(){
        try {
            CallbackFuture future =new CallbackFuture();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(HttpUtil.getUrl() +"/Score/exist").get().build();
            client.newCall(request).enqueue(future);
            Response response = future.get();
            String string = response.body().string();
            int count1 = Integer.parseInt(string);
            if(count1>0)
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
     * 录入选课信息及成绩
     */
    public boolean setScore(Score score){
        try {
            CallbackFuture future =new CallbackFuture();
            OkHttpClient client = new OkHttpClient();
            String toJson = gson.toJson(score);
            RequestBody body = RequestBody.create(
                    MediaType.parse("application/json"), toJson);
            Request request = new Request.Builder().url(HttpUtil.getUrl() +"/Score").put(body).build();
            client.newCall(request).enqueue(future);
            Response response = future.get();
            String string = response.body().string();
            if (string.equals("true"))
            {
                return true;
            }
            return true;
        }catch (SQLiteConstraintException e){
            //Toast.makeText(context, "主键重复", Toast.LENGTH_SHORT).show();
            return false;
        }catch (Exception e){
            Log.e(TAG, "", e);
        }
        return false;
    }
    /**
     * 查询选课情况及课程分数,根据学生Id
     */
    public List<CourseSec> getScore(Long student_id){

//        String sql = "select c.course_id,c.course_name,credit,grade " +
//                "from course as c,score as s " +
//                "where s.student_id= ? and s.course_id=c.course_id";
        try{
            CallbackFuture future =new CallbackFuture();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(HttpUtil.getUrl() +"/Score/"+student_id).get().build();
            client.newCall(request).enqueue(future);
            Response response = future.get();
            String string = response.body().string();
            List<CourseSec> Slist = gson.fromJson(string, new TypeToken<List<CourseSec>>() {
            }.getType());
                return Slist;
            } catch (Exception e) {
            Log.e(TAG, "", e);
        }
        return null;

    }
    /**
     * 查询学生已选课程，Course类
     */
    public List<Course> getCourse(Long id)
    {
        try {
            CallbackFuture future =new CallbackFuture();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(HttpUtil.getUrl() +"/Score/selected/"+id).get().build();
            client.newCall(request).enqueue(future);
            Response response = future.get();
            String string = response.body().string();
            List<Course> courseList = gson.fromJson(string, new TypeToken<List<Course>>() {
            }.getType());
            return courseList;
        }
        catch (Exception e) {
            Log.e(TAG, "", e);
        }
        return null;
    }
    /**
     * 查询学生未选课程信息
     */
    public List<Course> getUnCourse(Long id){
        //除去socre表中已选的课程
        try {
            CallbackFuture future =new CallbackFuture();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(HttpUtil.getUrl() +"/Score/unselected/"+id).get().build();
            client.newCall(request).enqueue(future);
            Response response = future.get();
            String string = response.body().string();
            List<Course> courseList = gson.fromJson(string, new TypeToken<List<Course>>() {
            }.getType());
            return courseList;
        }
        catch (Exception e) {
            Log.e(TAG, "", e);
        }
        return null;
    }
    /**
     * 学生选课
     */
    public boolean chooseCourse(Long student_id,int course_id){

        try {
            CallbackFuture future =new CallbackFuture();
            OkHttpClient client = new OkHttpClient();
            Score score = new Score(course_id, student_id,0);
            String toJson = gson.toJson(score);
            RequestBody body = RequestBody.create(
                    MediaType.parse("application/json"), toJson);
            Request request = new Request.Builder().url(HttpUtil.getUrl() +"/Score/addupdate/"+student_id+'/'+course_id).post(body).build();
            client.newCall(request).enqueue(future);
            Response response = future.get();
            String string = response.body().string();
            if(string.equals("true"))
            {
                return true;
            }

        }catch (Exception e){
            Toast.makeText(context, "主键重复", Toast.LENGTH_SHORT).show();
            return false;
        }
        return false;
    }

    /**
     * 学生 退选
     * @param student_id
     * @param course_id
     * @return
     */
    public boolean rmCourse(Long student_id,int course_id){
        try {
            CallbackFuture future =new CallbackFuture();
            OkHttpClient client = new OkHttpClient();
            Score score = new Score(course_id, student_id,0);
            String toJson = gson.toJson(score);
            RequestBody body = RequestBody.create(
                    MediaType.parse("application/json"), toJson);
            Request request = new Request.Builder().url(HttpUtil.getUrl() +"/Score/rmupdate/"+student_id+'/'+course_id).post(body).build();
            client.newCall(request).enqueue(future);
            Response response = future.get();
            String string = response.body().string();
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
     * 录入学生成绩，需要先判断学生是否选了该门课程，没选提示错误
     * 进行update操作即便是表中数据不符合要更新的条件，无法进行更行，也不会产生异常
     * 先对要进行更新的学生id和课程id进行查询，看表中是否有对应的项，如果有则进行update操作，没有则返回false
     */
    public boolean writeScore(Score score){
//        SQLiteDatabase db = null;
//        String sql = "update score set grade = ? where student_id = ? and course_id = ?";
            course = this.getCourse(score.getStudentId());
            for (Course c :
                    course) {
                if (c.getId() == score.getCourseId()) {
                    return setScore(score);
                }
            }
            return false;
    }
    /**
     * 根据学生id,和课程Id，去Score中查询是否有对应项，有返回true,无返回false
     */
    private boolean checkCourse(Long student_id,int course_id){
        Cursor cursor = null;
        String sql = "select * from score where student_id = ? and course_id = ?";
//        cursor = db.rawQuery(sql,new String[]{String.valueOf(student_id),String.valueOf(course_id)});
        course = this.getCourse(student_id);
        if(course.isEmpty())
        {
            return false;
        }
        for (Course c :
                course) {
            if (c.getId() == course_id) {
                return true;
            }
        }
        return false;
    }

}
