package com.xcu109.student.Entity;

/**
 * 课程类，课程号，课程名，学分，图片id
 */
public class Course  {
    private int courseId;
    private String courseName;
    private int credit;
    private String imageId;
    public Course(){

    }
    public Course(int courseId,String courseName,int credit,String imageId){
        this.courseId = courseId;
        this.courseName = courseName;
        this.credit = credit;
        this.imageId = imageId;
    }

    public int getId(){
        return courseId;
    }
    public void setId(int id){this.courseId = id;}
    public String getName(){
        return courseName;
    }
    public void setName(String name){this.courseName = name;}
    public int getCredit(){
        return credit;
    }
    public void setCredit(int credit){this.credit = credit;}
    public String getImageId(){
        return imageId;
    }
    public void setImageId(String id){this.imageId = id;}
}
