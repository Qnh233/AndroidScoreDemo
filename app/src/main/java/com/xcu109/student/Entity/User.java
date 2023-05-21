package com.xcu109.student.Entity;

/**
 * 登录信息类
 */

public class User {
    private long student_id;
    private String password;
    public User(){

    }
    public User(Long student_id, String password){
        this.student_id = student_id;
        this.password = password;
    }
    public long getId(){return  student_id;}
    public void setStudent_id(int id){student_id=id;}
    public String getPassword(){return password;}
    public void setPassword(String password){this.password=password;}
}
