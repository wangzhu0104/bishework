package com.example.myapplication.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.RequestData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.myapplication.activity.LoginActivity.usertel;
import static com.example.myapplication.activity.MainActivity.alluser;

public class ShopActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    TextView numberTv;
    ImageView backIv;
    RadioGroup priceRg,priceRg_2;
    RadioButton yiyuan,wuyuan,shiyuan,ershiyuan;
    Button chongzhiBtn;
    int number = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        numberTv = findViewById(R.id.shop_number);
        backIv = findViewById(R.id.shop_iv_back);
        priceRg = findViewById(R.id.rg1);
        priceRg_2 = findViewById(R.id.rg2);
        yiyuan = findViewById(R.id.rb1);
        wuyuan = findViewById(R.id.rb2);
        shiyuan = findViewById(R.id.rb3);
        ershiyuan = findViewById(R.id.rb4);
        chongzhiBtn = findViewById(R.id.button5);
        priceRg.check(R.id.rb1);

        initView();

        numberTv.setText("您的剩余留言次数为：" + alluser.getNowUser(usertel).getNumber() + " 次。");

        backIv.setOnClickListener(this);
        chongzhiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPriceListener();
                //System.out.println(number);

                AlertDialog.Builder imgbuilder = new AlertDialog.Builder( view.getContext());
                final View myview =  getLayoutInflater().inflate(R.layout.activity_chongzhi,null);

                imgbuilder.setView(myview);
                imgbuilder.setTitle("收银台");

                imgbuilder.setPositiveButton("我已支付", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int mnumber = alluser.getNowUser(usertel).getNumber();

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
                        requestData9.setUrl("http://124.220.159.4/upnumber.php");
                        List<String> key9 = new ArrayList<>();
                        key9.add("TEL");
                        key9.add("NUMBER");

                        List<String> param9 = new ArrayList<>();
                        param9.add(usertel);//变量名ID
                        param9.add(String.valueOf(number));
                        requestData9.post(key9,param9);

                        numberTv.setText("您的剩余留言次数为：" + String.valueOf(mnumber+number) + " 次。");
                        //充值成功的弹窗
                        Toast.makeText(ShopActivity.this,"恭喜您充值成功！",Toast.LENGTH_SHORT).show();
                    }
                });
                imgbuilder.setNegativeButton( "取消支付",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                } );
                AlertDialog dialog = imgbuilder.create();
                dialog.show();
            }
        });

    }

    private void setPriceListener() {
        priceRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == R.id.rb1) {
                    number = 2;
                } else if (checkedId == R.id.rb2) {
                    number = 10;
                }
            }
        });

        priceRg_2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedI) {
                if (checkedI == R.id.rb3) {
                    number = 20;
                } else if (checkedI == R.id.rb4) {
                    number = 50;
                }
            }
        });
    }

    private void initView() {
        yiyuan.setOnCheckedChangeListener(this);
        wuyuan.setOnCheckedChangeListener(this);
        shiyuan.setOnCheckedChangeListener(this);
        ershiyuan.setOnCheckedChangeListener(this);
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.rb1:
            case R.id.rb2:
                priceRg_2.clearCheck();
                break;
            case R.id.rb3:
            case R.id.rb4:
                priceRg.clearCheck();
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.shop_iv_back:
                finish();
                break;
        }
    }
}
