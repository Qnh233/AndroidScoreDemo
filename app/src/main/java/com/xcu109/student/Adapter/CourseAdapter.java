package com.xcu109.student.Adapter;

/**
 * Created by clutchyu on 2018/3/17.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xcu109.student.Entity.Course;
import com.xcu109.student.R;

import java.lang.reflect.Field;
import java.util.List;

/**
 *课程类适配器，将课程信息展示在CourseFragment上
 * 管理员查看所有课程信息
 */
public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder>{

    private List<Course> mCourseList;

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

    public CourseAdapter(List<Course> courseList) {
        mCourseList=courseList;
    }

    @Override
    /**
     * 点击对应部分显示toast
     */
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    /**
     * 将课程信息显示在textView上
     */
    public void onBindViewHolder(ViewHolder holder, int position) {
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