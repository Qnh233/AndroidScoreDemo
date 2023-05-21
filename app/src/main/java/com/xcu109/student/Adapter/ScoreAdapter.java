package com.xcu109.student.Adapter;

/**
 * 显示学生未选课的信息，显示到SecondFragment中
 */

import android.content.Context;
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
import com.xcu109.student.Entity.Course;
import com.xcu109.student.Fragment.FirstFragment;
import com.xcu109.student.Fragment.SecondFragment;
import com.xcu109.student.MainActivity;
import com.xcu109.student.MyApplication;
import com.xcu109.student.R;

import java.lang.reflect.Field;
import java.util.List;
@RequiresApi(api = Build.VERSION_CODES.N)
public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ViewHolder>{
    Context context = MyApplication.getInstance();

    private List<Course> mCourseList;
    private ScoreDao scoreDao = new ScoreDao(context);
    static class ViewHolder extends RecyclerView.ViewHolder {
        View CourseView;
        ImageView CourseImage;
        TextView CourseName;

        public ViewHolder(View view) {
            super(view);
            CourseView = view;
            CourseImage = (ImageView) view.findViewById(R.id.Course_image);
            CourseName = (TextView) view.findViewById(R.id.Course_name);
        }
    }
    public ScoreAdapter(List<Course> courseList) {
        mCourseList=courseList;
    }
    public ScoreAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item, parent, false);
        final ScoreAdapter.ViewHolder holder = new ScoreAdapter.ViewHolder(view);
        holder.CourseView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Course course = mCourseList.get(position);
                scoreDao.chooseCourse(MainActivity.getStudentId(),course.getId());
                Toast.makeText(v.getContext(), "选课成功！" , Toast.LENGTH_SHORT).show();
                SecondFragment.updata();
                FirstFragment.update();

            }
        });
        holder.CourseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Course course = mCourseList.get(position);
                scoreDao.chooseCourse(MainActivity.getStudentId(),course.getId());
                Toast.makeText(v.getContext(), "选课成功！ " , Toast.LENGTH_SHORT).show();
                SecondFragment.updata();
                FirstFragment.update();

            }
        });
        return holder;
    }


    /**
     * 将课程信息显示在textView上
     */
    public void onBindViewHolder(ScoreAdapter.ViewHolder holder, int position) {
        Course course = mCourseList.get(position);
        try {
            Field field = R.drawable.class.getDeclaredField(course.getImageId());
            int imgRid = field.getInt(R.drawable.class);
            holder.CourseImage.setImageResource(imgRid);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        holder.CourseName.setText("课程号："+course.getId()+"          "+course.getName()+"\n学分："+course.getCredit());
    }

    @Override
    public int getItemCount() {
        return mCourseList.size();
    }


}
