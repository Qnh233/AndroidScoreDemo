package com.xcu109.student.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.xcu109.student.Adapter.CourseAdapter;
import com.xcu109.student.AddCourseActivity;
import com.xcu109.student.Dao.CourseDao;
import com.xcu109.student.Entity.Course;
import com.xcu109.student.MyApplication;
import com.xcu109.student.R;

import java.util.List;

/**
 * 以管理员身份登入系统的第一个界面，显示所有课程信息及选课人数，底部有添加课程和删除课程的按钮
 */

public class ManagerFirstFragment extends Fragment {
    static Context context = MyApplication.getInstance();
    private CourseDao course = new CourseDao(context);
    private static RecyclerView recyclerView;
    private static TextView emptyText;
    private Button add;
    private static List<Course> CourseList;
    public ManagerFirstFragment(){}
    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manager_first, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        emptyText = (TextView) view.findViewById(R.id.empty_text);
        add = (Button) view.findViewById(R.id.add);
        CourseList = course.getCourse();
        if(CourseList==null){
            emptyText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }else{
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(layoutManager);
            CourseAdapter adapter = new CourseAdapter(CourseList);
            recyclerView.setAdapter(adapter);
            DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),layoutManager.getOrientation());
            recyclerView.addItemDecoration(mDividerItemDecoration);
        }

        add.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                //跳转到添加课程页面
                Intent intent = new Intent(context, AddCourseActivity.class);
                startActivity(intent);
            }
        });



        return view;
    }
    public void onResume(){
        super.onResume();
        CourseList = course.getCourse();
        if(CourseList==null){
            emptyText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }else{
            emptyText.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(layoutManager);
            CourseAdapter adapter = new CourseAdapter(CourseList);
            recyclerView.setAdapter(adapter);
            DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),layoutManager.getOrientation());
            recyclerView.addItemDecoration(mDividerItemDecoration);
        }
    }
}
