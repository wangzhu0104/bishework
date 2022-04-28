package com.example.myapplication.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.AllUser;
import com.example.myapplication.Msg;
import com.example.myapplication.R;
import com.example.myapplication.RequestData;
import com.example.myapplication.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static com.example.myapplication.activity.MainActivity.alluser;
import static com.example.myapplication.activity.MainActivity.weather;


public class LoginActivity extends AppCompatActivity {
    private EditText mTel;                        //用户名编辑
    private EditText mPwd;                            //密码编辑
    private CheckBox mLoginauto;                       //自动登录
    private CheckBox mRemember;                        //记住密码
    private Button mRegisterButton;
    private Button mLoginButton;
    public static String usertel;
    public static String username;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //通过id找到按钮
        //注册按钮
        mRegisterButton = findViewById(R.id.login_btn_register);
        //登录按钮
        mLoginButton = findViewById(R.id.login_btn_login);
        mLoginauto = findViewById(R.id.loginauto);
        mRemember = findViewById(R.id.remember);
        //通过id找到editText的值
        mTel = findViewById(R.id.login_edit_tel);
        mPwd = findViewById(R.id.login_edit_pwd);

        InitView();
        final SharedPreferences sp = getSharedPreferences("userInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (sp.getBoolean("ZDDL",false)) {
            Intent intent = new Intent();
            intent.setClass(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            LoginActivity.this.finish();
            weather = false;
        }
        if (sp.getBoolean("JZMM",false)) {
            mTel.setText(sp.getString("TEL",""));
            mPwd.setText(sp.getString("Password",""));
            mRemember.setChecked(true);
        }

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User nowUser = new User();

                if(mRemember.isChecked()){
                    nowUser.setJzmm(true);
                }
                else{
                    nowUser.setJzmm(false);
                }
                if(mLoginauto.isChecked()){
                    nowUser.setZddl(true);
                }
                else{
                    nowUser.setZddl(false);
                }


                String nowZH = mTel.getText().toString();
                String nowMM = mPwd.getText().toString();

                if (!isUserNameAndPwdValid()) {
                    return;
                }

                if(alluser.isZC(nowZH)){
                    Toast.makeText( LoginActivity.this,"账号已存在", Toast.LENGTH_SHORT).show();
                    return;
                }
                final SharedPreferences sp = getSharedPreferences("userInfo", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();


                editor.clear();
                nowUser.setTEL(Integer.parseInt(mTel.getText().toString()));
                nowUser.setPassword(mPwd.getText().toString());
                editor.putBoolean("ZDDL",nowUser.isZddl());
                editor.putBoolean("JZMM",nowUser.isJzmm());
                editor.putString("TEL",String.valueOf(nowUser.getTEL()));
                editor.putString("Password",nowUser.getPassword());
                editor.commit();


                alluser.myAllUser.add(nowUser);
                RequestData requestData9;
                RequestData.myCallback myCallback9 = new RequestData.myCallback(){
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onSuccess(String response9) {
                        Log.e("9，注册，创建用户ID，tbName，Password",response9);
                        try {
                            JSONObject jsonObject1 = new JSONObject(response9);

                            int status = jsonObject1.getInt("status");
                            Log.e("\n循环4搞出：","\n"+status+"\n");

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
                requestData9.setUrl("http://124.220.159.4/register.php");
                List<String> key9 = new ArrayList<>();
                key9.add("TEL");
                key9.add("Password");
                key9.add("NUM1");
                key9.add("NUM2");
                List<String> param9 = new ArrayList<>();
                param9.add(String.valueOf(nowUser.getTEL()));//变量名ID
                param9.add(nowUser.getPassword());
                if(nowUser.isZddl()){
                    param9.add("1");
                }
                else {
                    param9.add("0");
                }
                if(nowUser.isJzmm()){
                    param9.add("1");
                }
                else {
                    param9.add("0");
                }


                //requestData.get();
                requestData9.post(key9,param9);

                weather = false;
                usertel = mTel.getText().toString();

                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                LoginActivity.this.finish();
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nowZH = mTel.getText().toString();
                String nowMM = mPwd.getText().toString();

                if (!isUserNameAndPwdValid()) {
                    return;
                }

                if(alluser.isRight(nowZH,nowMM)){
                    User nowUser = alluser.getNowUser(nowZH);

                    if(mRemember.isChecked()){
                        nowUser.setJzmm(true);
                    }
                    else{
                        nowUser.setJzmm(false);
                    }
                    //
                    if(mLoginauto.isChecked()){
                        nowUser.setZddl(true);
                    }
                    else{
                        nowUser.setZddl(false);
                    }
                    final SharedPreferences sp = getSharedPreferences("userInfo", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();

                    editor.putString("Password",nowUser.getPassword());
                    editor.putString("Name",nowUser.getName());
                    editor.putString("TEL",String.valueOf(nowUser.getTEL()));

                    editor.putBoolean("ZDDL",nowUser.isZddl());
                    editor.putBoolean("JZMM",nowUser.isJzmm());
                    editor.commit();
                    //10，登录保存两个勾，ID，Password,num1,2
                    RequestData requestData10;
                    RequestData.myCallback myCallback10 = new RequestData.myCallback(){
                        @SuppressLint("LongLogTag")
                        @Override
                        public void onSuccess(String response10) {
                            Log.e("10，登录保存两个勾，ID，Password,num1,2",response10);
                            try {
                                JSONObject jsonObject1 = new JSONObject(response10);
                                int status = jsonObject1.getInt("status");
                                Log.e("\n循环10状态：","\n"+status+"\n");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        //
                        @Override
                        public void onFail(IOException e) {

                        }
                    };
                    requestData10 = new RequestData(myCallback10);
                    requestData10.setUrl("http://124.220.159.4/register2.php");
                    List<String> key10 = new ArrayList<>();
                    key10.add("TEL");
                    key10.add("PWD");

                    key10.add("NUM1");
                    key10.add("NUM2");
                    List<String> param10 = new ArrayList<>();
                    param10.add(String.valueOf(nowUser.getTEL()));//变量名ID
                    param10.add(nowUser.getPassword());//变量名password

                    if(nowUser.isZddl()){
                        param10.add("1");
                    }
                    else {
                        param10.add("0");
                    }
                    if(nowUser.isJzmm()){
                        param10.add("1");
                    }
                    else {
                        param10.add("0");
                    }
                    //requestData.get();
                    requestData10.post(key10,param10);

                    weather = false;
                    usertel = mTel.getText().toString();

                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();
                }

                else if(alluser.isZC(nowZH)){
                    Toast.makeText( LoginActivity.this,"密码错误", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText( LoginActivity.this,"没有注册账户", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public static void InitView() {
        //2user开始了
        RequestData requestData2;
        RequestData.myCallback myCallback2 = new RequestData.myCallback(){
            @Override
            public void onSuccess(String response2) {
                Log.e("user",response2);
                try {
                    JSONArray jsonArray = new JSONArray(response2);
                    Log.e("jsarryuuuuuuuser", String.valueOf(jsonArray));
                    //JSONObject jsonObject = new JSONObject(response);
                    alluser = new AllUser();
                    alluser.myAllUser.clear();
                    int m = jsonArray.length();
                    for (int i = 0;i < jsonArray.length();i++){
                        JSONObject jsonObject1 = (JSONObject)jsonArray.get(i);

                        String Password = jsonObject1.getString("PASSWORD");
                        String Name = jsonObject1.getString("NAME");


                        int TEL = jsonObject1.getInt("TEL");
                        int Number = jsonObject1.getInt("NUMBER");
                        int zddl = jsonObject1.getInt("ZDDL");
                        boolean b = (zddl != 0);
                        int jzmm = jsonObject1.getInt("JZMM");
                        boolean c = (jzmm != 0);
                        Log.e("\n循环2搞出：","\n"+"\n"+Password+"\n"+Name+"\n"+Number+"\n"+TEL+"\n"+zddl+"\n"+jzmm+"\n");

                        innalluser(Password,Name,Number,TEL,b,c);
                    }
                    Log.e("json","王铁柱");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            public void innalluser( String Password, String Name,int Number,
                                   int TEL,boolean zddl,boolean jzmm){
                alluser.myAllUser.add(new User(Password,Name,Number,TEL,zddl,jzmm));
            }
            @Override
            public void onFail(IOException e) {

            }
        };

        requestData2 = new RequestData(myCallback2);
        requestData2.setUrl("http://124.220.159.4/query.php");
        List<String> key2 = new ArrayList<>();
        key2.add("tbName");
        List<String> param2 = new ArrayList<>();
        param2.add("user");
        //requestData.get();
        requestData2.post(key2,param2);
        if (alluser == null)
            alluser = new AllUser();

        //2user开始了
        RequestData requestData3;
        RequestData.myCallback myCallback3 = new RequestData.myCallback(){
            @Override
            public void onSuccess(String response2) {
                Log.e("user",response2);
                try {
                    JSONArray jsonArray = new JSONArray(response2);
                    Log.e("jsarryuuuuuuuser", String.valueOf(jsonArray));
                    //JSONObject jsonObject = new JSONObject(response);

                    alluser.allUserMsg.clear();
                    int m = jsonArray.length();
                    for (int i = 0;i < jsonArray.length();i++){
                        JSONObject jsonObject1 = (JSONObject)jsonArray.get(i);


                        int TEL = jsonObject1.getInt("TEL");
                        String MSG = jsonObject1.getString("MSG");
                        String TIME = jsonObject1.getString("TIME");
                        int b = jsonObject1.getInt("Id");
                        String NAME = jsonObject1.getString("NAME");


                        innalluser(TEL,MSG,TIME,NAME);
                    }
                    Log.e("json","王铁柱");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            public void innalluser(int TEL,String MSG,String TIME,String NAME){
                alluser.allUserMsg.add(new Msg(TEL,MSG,TIME,NAME));
            }
            @Override
            public void onFail(IOException e) {

            }
        };

        requestData3 = new RequestData(myCallback3);
        requestData3.setUrl("http://124.220.159.4/query.php");
        List<String> key3 = new ArrayList<>();
        key3.add("tbName");
        List<String> param3 = new ArrayList<>();
        param3.add("chat");
        //requestData.get();
        requestData3.post(key3,param3);
        if (alluser == null)
            alluser = new AllUser();
    }




    public boolean isUserNameAndPwdValid() {
        String userName = mTel.getText().toString().trim();    //获取当前输入的用户名和密码信息
        String userPwd = mPwd.getText().toString().trim();
        if (userName.equals("")) { //用户名为空
            Toast.makeText(this, "电话号码不能为空",
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (userPwd.equals("")) {
            Toast.makeText(this, "密码不能为空！",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
