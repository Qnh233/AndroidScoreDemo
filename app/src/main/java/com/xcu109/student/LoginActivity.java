package com.xcu109.student;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xcu109.student.Dao.UserDao;

public class LoginActivity extends AppCompatActivity {

    private EditText account;
    private EditText password;
    private Button log;
    private Button sign;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        account = findViewById(R.id.account);
        password = findViewById(R.id.password);
        log = findViewById(R.id.login);
        sign = findViewById(R.id.signin);
        final UserDao user = new UserDao(this);
        log.setOnClickListener(new View.OnClickListener() {         //点击登录，跳转到MainActivity,传入对应id
            @Override
            public void onClick(View view) {
                String id_str = account.getText().toString();
               // int id = Integer.parseInt(id_str);
                String passwd = password.getText().toString();
                if(!EditCheck.CheckInt(id_str,"账号",0,99999)){
//                    showNormalDialog(EditCheck.getWarning());
                    Toast.makeText(getApplicationContext(),EditCheck.getWarning(),Toast.LENGTH_SHORT).show();
                }
                else if(!EditCheck.CheckString(passwd,"密码",20)){
//                    showNormalDialog(EditCheck.getWarning());
                    Toast.makeText(getApplicationContext(),EditCheck.getWarning(),Toast.LENGTH_SHORT).show();
                }
                else if(user.check(Long.parseLong(id_str),passwd)){              //账号匹配成功,进入MainActivity
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    Toast.makeText(getApplicationContext(),id_str+",欢迎登录。",Toast.LENGTH_SHORT).show();
                    intent.putExtra("id",Long.parseLong(id_str));
                    startActivity(intent);
                    finish();
                }else{                              //弹出错误提示框
                    String word = "账号或密码错误！";
//                    showNormalDialog(word);
                    Toast.makeText(getApplicationContext(),word,Toast.LENGTH_SHORT).show();
                }

            }
        });
        sign.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                    Intent intent = new Intent(LoginActivity.this,ModifyPasswdActivity.class);
                    startActivity(intent);
            }
        });
    }


}

