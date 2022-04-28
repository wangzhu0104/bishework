package com.example.myapplication.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.RequestData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.myapplication.activity.LoginActivity.usertel;
import static com.example.myapplication.activity.LoginActivity.username;

public class UserChangeActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView backIv;
    EditText nameEt,pwdEt;
    Button xiugai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_ex);
        nameEt = findViewById(R.id.editText);
        backIv.setOnClickListener(this);
        xiugai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String musername = nameEt.getText().toString();
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
                requestData9.setUrl("http://124.220.159.4/update.php");
                List<String> key9 = new ArrayList<>();
                key9.add("TEL");
                key9.add("NAME");

                List<String> param9 = new ArrayList<>();
                param9.add(usertel);//变量名ID
                param9.add(musername);
                requestData9.post(key9,param9);

                Intent mainintent = new Intent(UserChangeActivity.this,MainActivity.class);
                startActivity(mainintent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }
}
