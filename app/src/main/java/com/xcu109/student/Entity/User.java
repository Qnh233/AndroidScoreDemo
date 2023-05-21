package com.xcu109.student.Entity;

/**
 * 登录信息类
 */

public class User {
    private Long id;
    private String password;
    public User(){

    }
    public User(Long student_id, String password){
        this.id = student_id;
        this.password = password;
    }
    public long getId(){return id;}
    public void setId(Long id){
        this.id =id;}
    public String getPassword(){return password;}
    public void setPassword(String password){this.password=password;}
}
