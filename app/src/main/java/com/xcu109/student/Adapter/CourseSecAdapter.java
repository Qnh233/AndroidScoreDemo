package com.xcu109.student.Adapter;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xcu109.student.Dao.ScoreDao;
import com.xcu109.student.Entity.CourseSec;
import com.xcu109.student.Fragment.FirstFragment;
import com.xcu109.student.Fragment.SecondFragment;
import com.xcu109.student.MainActivity;
import com.xcu109.student.MyApplication;
import com.xcu109.student.R;

import java.lang.reflect.Field;
import java.util.List;

/**
 *课程类适配器，将课程信息展示在CourseSecFragment上
 * 管理员查看所有课程信息
 */
@RequiresApi(api = Build.VERSION_CODES.N)
public class CourseSecAdapter extends RecyclerView.Adapter<CourseSecAdapter.ViewHolder>{

    private List<CourseSec> mCourseSecList;

    private ScoreDao scoreDao = new ScoreDao(MyApplication.getInstance());


    static class ViewHolder extends RecyclerView.ViewHolder {
        View CourseSecView;
        ImageView CourseSecImage;
        TextView CourseSecName;

        public ViewHolder(View view) {
            super(view);
            CourseSecView = view;
            CourseSecImage = (ImageView) view.findViewById(R.id.Course_image);
            CourseSecName = (TextView) view.findViewById(R.id.Course_name);
        }
    }

    public CourseSecAdapter(List<CourseSec> CourseSecList) {
        mCourseSecList=CourseSecList;
    }
    public void changeAdapter(List<CourseSec> CourseSecList){
        mCourseSecList=CourseSecList;
    }

    @Override
    /**
     * 点击对应部分显示toast
     */
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.CourseSecView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                int position = holder.getAdapterPosition();
                CourseSec CourseSec = mCourseSecList.get(position);
                builder.setMessage("确定要删除"+ CourseSec.getCourse_name()+"吗？");
                builder.setTitle("提示");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        // 执行操作
//                        Toast.makeText(MyApplication.getInstance(), "你点击确定了", Toast.LENGTH_SHORT).show();
                        Toast.makeText(v.getContext(), "退课成功！ " , Toast.LENGTH_SHORT).show();
                        scoreDao.rmCourse(MainActivity.getStudentId(),CourseSec.getCourse_id());
                        SecondFragment.updata();
                        FirstFragment.update();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();

            }
        });
        holder.CourseSecImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                CourseSec CourseSec = mCourseSecList.get(position);
            }
        });
        return holder;
    }

    @Override
    /**
     * 将课程信息显示在textView上
     */
    public void onBindViewHolder(ViewHolder holder, int position) {
        CourseSec CourseSec = mCourseSecList.get(position);
        try {
            Field field = R.drawable.class.getDeclaredField(CourseSec.getImageId());
            int imgRid = field.getInt(R.drawable.class);
            holder.CourseSecImage.setImageResource(imgRid);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        holder.CourseSecName.setText("学号： "+CourseSec.getCourse_id()+"         "+CourseSec.getCourse_name()+"\n学分："+CourseSec.getCredit()+"\n成绩："+CourseSec.getGrade());
    }

    @Override
    public int getItemCount() {
        return mCourseSecList.size();
    }

}