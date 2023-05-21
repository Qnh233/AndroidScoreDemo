package com.xcu109.student.Fragment;

/**
 * BottomNavigationView对应的第三个Fragment
 * 显示个人信息
 * 从修改信息的界面返回ThirdFragment时，状态为onResume,重写onResume方法实现刷新数据。
 */

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

import com.xcu109.student.Adapter.StudentAdapter;
import com.xcu109.student.Dao.StudentDao;
import com.xcu109.student.Entity.Student_info;
import com.xcu109.student.MainActivity;
import com.xcu109.student.MyApplication;
import com.xcu109.student.R;
import com.xcu109.student.StudentChangeActivity;

import java.util.List;



@RequiresApi(api = Build.VERSION_CODES.N)
public class ThirdFragment extends Fragment {
    Context context = MyApplication.getInstance();
    private StudentDao student = new StudentDao(context);
    private List<Student_info> StudentList = student.getStudent(MainActivity.getStudentId());
    private RecyclerView recyclerView;
    private Long studentId = MainActivity.getStudentId();
    public ThirdFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_third, container, false);
        //initCourse();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        //StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        StudentAdapter adapter = new StudentAdapter(StudentList);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),layoutManager.getOrientation());
        recyclerView.addItemDecoration(mDividerItemDecoration);

        Button button = (Button) view.findViewById (R.id.click);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                Intent intent = new Intent(context, StudentChangeActivity.class);
                intent.putExtra("id",studentId);
                startActivity(intent);

            }

        });

        // Inflate the layout for this fragment
        return view;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onResume(){
        super.onResume();
        StudentList = student.getStudent(MainActivity.getStudentId());
        StudentAdapter adapter = new StudentAdapter(StudentList);
        recyclerView.setAdapter(adapter);

    }

}