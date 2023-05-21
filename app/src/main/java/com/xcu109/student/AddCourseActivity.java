package com.xcu109.student;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xcu109.student.Dao.CourseDao;
import com.xcu109.student.Entity.Course;

public class AddCourseActivity extends AppCompatActivity {
        private EditText idText;
        private EditText nameText;
        private EditText creditText;

        private CourseDao courseDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        idText = (EditText)findViewById(R.id.studentId);
        nameText = (EditText)findViewById(R.id.courseId);
        creditText = (EditText)findViewById(R.id.credit);
        Button add = (Button)findViewById(R.id.add);
        Button delete = (Button)findViewById(R.id.delete);
        Button cancel = (Button)findViewById(R.id.cancel_a);
        add.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onClick(View v){
                String word;
                if(!EditCheck.CheckInt(idText.getText().toString(),"课程号",10000,99999)){
                    Toast.makeText(getApplicationContext(),EditCheck.getWarning(),Toast.LENGTH_SHORT).show();
                }
                else if(!EditCheck.CheckString(nameText.getText().toString(),"课程名",10)){
                    Toast.makeText(getApplicationContext(),EditCheck.getWarning(),Toast.LENGTH_SHORT).show();
                }
                else if(!EditCheck.CheckInt(creditText.getText().toString(),"学分",1,10)){
                    Toast.makeText(getApplicationContext(),EditCheck.getWarning(),Toast.LENGTH_SHORT).show();
                }
                else if(addDatebase()){
                    word = "添加课程成功！";
                    Toast.makeText(getApplicationContext(),word,Toast.LENGTH_SHORT).show();
                    idText.setText("");
                    nameText.setText("");
                    creditText.setText("");
                   // finish();
                }else{
                    word = "添加课程失败，请重试！";
                    Toast.makeText(getApplicationContext(),word,Toast.LENGTH_SHORT).show();
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onClick(View v){
                String word ;
                if(idText.getText().toString().equals("")){
                    word = "课程号不能为空！";
                    Toast.makeText(getApplicationContext(),word,Toast.LENGTH_SHORT).show();
                }
                else if(deleteDatabase()){
                    word = "删除课程成功！";
                    Toast.makeText(getApplicationContext(),word,Toast.LENGTH_SHORT).show();
                    idText.setText("");
                    nameText.setText("");
                    creditText.setText("");
                    // finish();
                }else{
                     word = "删除课程失败，请重试！";
                    Toast.makeText(getApplicationContext(),word,Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                finish();
            }
        });

    }
    /**
     * 向数据库添加课程信息
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean addDatebase(){
        CourseDao courseDao = new CourseDao(MyApplication.getInstance());
        int courseId = Integer.parseInt(idText.getText().toString());
        String courseName = nameText.getText().toString();
        int credit = Integer.parseInt(creditText.getText().toString());
        Course course = new Course(courseId,courseName,credit,"course");
        if(courseDao.addCourse(course)){
            return true;
        }else{
            return false;
        }

    }
    /**
     * 在数据库删除课程信息
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean deleteDatabase(){
        CourseDao courseDao = new CourseDao(MyApplication.getInstance());
        int courseId = Integer.parseInt(idText.getText().toString());
        if(courseDao.deleteCourse(courseId)){
            return true;
        }else{
            return false;
        }
    }
}
