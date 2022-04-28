package com.example.myapplication.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.AllUser;
import com.example.myapplication.R;
import com.example.myapplication.RequestData;
import com.example.myapplication.User;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.myapplication.activity.LoginActivity.username;
import static com.example.myapplication.activity.LoginActivity.usertel;
import static com.example.myapplication.activity.MainActivity.alluser;


public class MoreActivity extends AppCompatActivity implements View.OnClickListener{
    TextView userTv,bgTv,shareTv;
    Button changeBtn,shopBtn,exitBtn;
    RadioGroup exbgRg;
    ImageView backIv;
    private SharedPreferences pref;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        changeBtn = findViewById(R.id.button1);
        shopBtn = findViewById(R.id.button2);
        exitBtn = findViewById(R.id.button3);
        bgTv = findViewById(R.id.more_tv_exchangebg);
        shareTv = findViewById(R.id.more_tv_share);
        backIv = findViewById(R.id.more_iv_back);
        userTv = findViewById(R.id.more_tv_user);
        exbgRg = findViewById(R.id.more_rg);

        changeBtn.setOnClickListener(this);
        shopBtn.setOnClickListener(this);
        exitBtn.setOnClickListener(this);
        bgTv.setOnClickListener(this);
        shareTv.setOnClickListener(this);
        backIv.setOnClickListener(this);
        pref = getSharedPreferences("bg_pref", MODE_PRIVATE);
        setRGListener();

        userTv.setText(alluser.getNowUser(usertel).getName());


        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //页面跳转
                AlertDialog.Builder builder = new AlertDialog.Builder( view.getContext());

                final View myview =  getLayoutInflater().inflate(R.layout.activity_user_ex,null);
                final EditText nameEt = myview.findViewById(R.id.editText);

                builder.setView(myview);
                builder.setTitle("更改个人信息");

                builder.setPositiveButton( "保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String musername = nameEt.getText().toString();
                        userTv.setText(musername);
                        RequestData requestData9;
                        RequestData.myCallback myCallback9 = new RequestData.myCallback(){
                            @SuppressLint("LongLogTag")
                            @Override
                            public void onSuccess(String response9) {
                                Log.e("9，注册，创建用户ID，tbName，Password",response9);
                                try {
                                    JSONObject jsonObject1 = new JSONObject(response9);

                                    int status = jsonObject1.getInt("status");
                                    Log.e("\n循环：","\n"+status+"\n");

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                            //
                            @Override
                            public void onFail(IOException e) {

                            }
                        };

                        //9，注册，创建用户ID，tbName，Password

                        requestData9 = new RequestData(myCallback9);
                        requestData9.setUrl("http://124.220.159.4/update.php");
                        List<String> key9 = new ArrayList<>();
                        key9.add("TEL");
                        key9.add("NAME");

                        List<String> param9 = new ArrayList<>();
                        param9.add(usertel);//变量名ID
                        param9.add(musername);
                        requestData9.post(key9,param9);

                    }
                } );
                builder.setNegativeButton( "取消",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                } );
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        shopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent postintent_2 = new Intent(MoreActivity.this,ShopActivity.class);
                startActivity(postintent_2);
            }
        });

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final SharedPreferences sp = getSharedPreferences("userInfo", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("ZDDL",false);
                editor.commit();
                Intent exitintent = new Intent(MoreActivity.this, LoginActivity.class);
                startActivity(exitintent);
            }
        });

    }

    private void setRGListener() {
        /* 设置改变背景图片的单选按钮的监听*/
        exbgRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                获取目前的默认壁纸
                int bg = pref.getInt("bg", 0);
                SharedPreferences.Editor editor = pref.edit();
                Intent intent = new Intent(MoreActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                switch (checkedId) {
                    case R.id.more_rb_green:
                        if (bg==0) {
                            Toast.makeText(MoreActivity.this,"您选择的为当前背景，无需改变！",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        editor.putInt("bg",0);
                        editor.commit();
                        break;
                    case R.id.more_rb_pink:
                        if (bg==1) {
                            Toast.makeText(MoreActivity.this,"您选择的为当前背景，无需改变！",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        editor.putInt("bg",1);
                        editor.commit();
                        break;
                    case R.id.more_rb_blue:
                        if (bg==2) {
                            Toast.makeText(MoreActivity.this,"您选择的为当前背景，无需改变！",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        editor.putInt("bg",2);
                        editor.commit();
                        break;
                }
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*case R.id.button1:
                Intent changeintent = new Intent(MoreActivity.this,UserChangeActivity.class);
                startActivity(changeintent);
                break;

            case R.id.button2:
                Intent shopintent = new Intent(MoreActivity.this,ShopActivity.class);
                startActivity(shopintent);
                break;

            case R.id.button3:

                break;*/

            case R.id.more_iv_back:
                finish();
                break;
/*            case R.id.more_tv_cache:
                clearCache();
                break;*/
            case R.id.more_tv_share:
                shareSoftwareMsg("你好我是王著，如果你喜欢此项目，可以前往我的github仓库进行克隆，仓库地址：https://github.com/wangzhu0104/bishework");
                break;
            case R.id.more_tv_exchangebg:
                if (exbgRg.getVisibility() == View.VISIBLE) {
                    exbgRg.setVisibility(View.GONE);
                }else{
                    exbgRg.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    private void shareSoftwareMsg(String s) {
        /* 分享软件的函数*/
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,s);
        startActivity(Intent.createChooser(intent,"天气"));
    }

//    private void clearCache() {
        /* 清除缓存的函数*/
/*        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示信息").setMessage("确定要删除所有缓存么？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DBManager.deleteAllInfo();
                Toast.makeText(MoreActivity.this,"已清除全部缓存！",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MoreActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }).setNegativeButton("取消",null);
        builder.create().show();
    }*/

//    public String getVersionName() {
        /* 获取应用的版本名称*/
/*        PackageManager manager = getPackageManager();
        String versionName = null;
        try {
            PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
            versionName = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }*/
}
