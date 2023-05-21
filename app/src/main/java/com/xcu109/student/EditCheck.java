package com.xcu109.student;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
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

        if (label.equals("名字"))
        {
            Pattern pattern = Pattern.compile("^[\\u4e00-\\u9fa5]{2,4}$");
            Matcher matcher = pattern.matcher(input);
            if (!matcher.matches())
            {
                warning = label + "格式错误！";
                return false;
            }
        }
        if(label.equals("电话"))
        {
            Pattern pattern = Pattern.compile("^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$");
            Matcher matcher = pattern.matcher(input);
            if (!matcher.matches())
            {
                warning = label + "格式错误！";
                return false;
            }
        }
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
