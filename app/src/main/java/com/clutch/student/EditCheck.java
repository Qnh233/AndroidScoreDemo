package com.clutch.student;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by clutchyu on 2018/3/31.
 * 用于数据输入检验
 */

public class EditCheck {
    private static String warning;
    public EditCheck(){

    }
    public static String getWarning(){return warning;}
    public static  boolean CheckInt(String input,String label,int min,int max){
        Pattern pattern;
        if(input.equals("")) {
            warning = label + "不能为空！";
            return false;
        }
        else {
            try{
                if(label.equals("账号") || label.equals("学号")) {
                    pattern = Pattern.compile("^\\d{10}|0$");
                    Matcher matcher = pattern.matcher(input);
                    if(!matcher.matches())
                    {
                        warning = label + "应为10位整数";
                        return false;
                    }
                }
                else if(label.equals("课程号")){
                    pattern = Pattern.compile("^\\d{5}$");
                    Matcher matcher = pattern.matcher(input);
                    if(!matcher.matches())
                    {
                        warning = label + "应为5位的整数";
                        return false;
                    }
                }
                else if(label.equals("年龄"))
                {
                    int i = Integer.parseInt(input);
                    if(!(i>=min&&i<=max))
                    {
                        warning = label + "年龄应在"+min+"-"+max+"之间";
                        return false;
                    }
                }
            }catch(RuntimeException e){
                warning =label+"格式错误！";
                return false;
            }
        }
        return true;
    }
    public static boolean CheckString(String input,String label,int size){
        if(input.equals("")) {
            warning = label + "不能为空！";
            return false;
        }
        else if(input.length()>size){
            warning = label + "长度错误！";
        }
        return true;
    }
}
