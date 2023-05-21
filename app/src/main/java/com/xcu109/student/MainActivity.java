package com.xcu109.student;

/**
 * 主界面，包含三个Fragment,以学生身份和管理员身份登入系统时对应界面不同
 */

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.xcu109.student.Adapter.ViewPagerAdapter;
import com.xcu109.student.Fragment.FirstFragment;
import com.xcu109.student.Fragment.ManagerFirstFragment;
import com.xcu109.student.Fragment.ManagerSecondFragment;
import com.xcu109.student.Fragment.ManagerThirdFragment;
import com.xcu109.student.Fragment.SecondFragment;
import com.xcu109.student.Fragment.ThirdFragment;
public class MainActivity extends AppCompatActivity {

    //dbHelper

    private static Long studentId;
    BottomNavigationView bottomNavigationView;
    //This is our viewPager
    private ViewPager viewPager;

    private ViewPagerAdapter adapter;
    //Fragments
    FirstFragment firstFragment;
    SecondFragment secondFragment;
    ThirdFragment thirdFragment;
    ManagerFirstFragment managerFirstFragment;
    ManagerSecondFragment managerSecondFragment;
    ManagerThirdFragment managerThirdFragment;
    MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Intent intent = getIntent();
        studentId = intent.getLongExtra("id",-1);
        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        //Initializing the bottomNavigationView
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
//        bottomNavigationView.getMenu().add(0,0,0,"添加1");
//        System.out.println(bottomNavigationView.getMenu().getItem(0).setTitle("李哥"));

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_first:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.action_second:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.action_third:
                                viewPager.setCurrentItem(2);
                                break;
                        }
                        return false;
                    }
                });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                }
                else
                {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: "+position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



        setupViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.switch_item:
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.quit_item:
                finish();
                break;
            default:
        }
        return true;
    }

    public static Long  getStudentId(){
        return studentId;
    }
    public  Context  context(){return this;}
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        if(getStudentId()==0){
            bottomNavigationView.getMenu().getItem(0).setTitle("课程列表");
            bottomNavigationView.getMenu().getItem(1).setTitle("修改成绩");
            bottomNavigationView.getMenu().getItem(2).setTitle("添加学生");
            managerFirstFragment=new ManagerFirstFragment();
            managerSecondFragment=new ManagerSecondFragment();
            managerThirdFragment=new ManagerThirdFragment();
            adapter.addFragment(managerFirstFragment);
            adapter.addFragment(managerSecondFragment);
            adapter.addFragment(managerThirdFragment);
        }else {
            firstFragment = new FirstFragment();
            secondFragment = new SecondFragment();
            thirdFragment = new ThirdFragment();
            adapter.addFragment(firstFragment);
            adapter.addFragment(secondFragment);
            adapter.addFragment(thirdFragment);
        }
        viewPager.setAdapter(adapter);
    }
}