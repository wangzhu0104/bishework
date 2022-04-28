package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication.AllUser;
import com.example.myapplication.CityFragmentPagerAdapter;
import com.example.myapplication.CityWeatherFragment;
import com.example.myapplication.R;
import com.example.myapplication.RequestData;
import com.example.myapplication.User;
import com.example.myapplication.city_manager.CityManagerActivity;
import com.example.myapplication.db.DBManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.myapplication.activity.LoginActivity.usertel;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView addCityIv,moreIv;
    LinearLayout pointLayout;
    RelativeLayout outLayout;
    ViewPager mainVp;
    TextView chatTv;

    //    ViewPager的数据源
    List<Fragment>fragmentList;
    //    表示需要显示的城市的集合
    List<String>cityList;
    //    表示ViewPager的页数指数器显示集合
    List<ImageView>imgList;
    private CityFragmentPagerAdapter adapter;
    private SharedPreferences pref;
    private int bgNum;
    public static AllUser alluser;

    public static boolean  weather = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (weather) {
            Intent loginaintent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginaintent);
            MainActivity.this.finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addCityIv = findViewById(R.id.main_iv_add);
        moreIv = findViewById(R.id.main_iv_more);
        pointLayout = findViewById(R.id.main_layout_point);
        outLayout = findViewById(R.id.main_out_layout);
        exchangeBg();
        mainVp = findViewById(R.id.main_vp);
        chatTv = findViewById(R.id.frag_chat);
//        添加点击事件
        InitView();

        chatTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InitView();
                Intent chatintent = new Intent(MainActivity.this, ChatActivity.class);
                startActivity(chatintent);
            }
        });

        addCityIv.setOnClickListener(this);
        moreIv.setOnClickListener(this);

        fragmentList = new ArrayList<>();
//        cityList = new ArrayList<>();
        cityList = DBManager.queryAllCityName();  //获取数据库包含的城市信息列表
        imgList = new ArrayList<>();

        if (cityList.size()==0) {
            cityList.add("湖北省 武汉");
//            cityList.add("北京");
//            cityList.add("上海");
//            cityList.add("广东省 广州");
//            cityList.add("广东省 深圳");
//            cityList.add("福建省 厦门");
        }
        /* 因为可能搜索界面点击跳转此界面，会传值，所以此处获取一下*/
        try {
            Intent intent = getIntent();
            String city = intent.getStringExtra("city");
            if (!cityList.contains(city)&&!TextUtils.isEmpty(city)) {
                cityList.add(city);
            }
        }catch (Exception e){
            Log.i("王著","程序出现问题了！！");
        }
//        初始化ViewPager页面的方法
        initPager();
        adapter = new CityFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        mainVp.setAdapter(adapter);
//        创建小圆点指示器
        initPoint();
//        设置最后一个城市信息
        mainVp.setCurrentItem(fragmentList.size()-1);
//        设置ViewPager页面监听
        setPagerListener();

        moreIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InitView();
                Intent postintent = new Intent(MainActivity.this,MoreActivity.class);
                startActivity(postintent);
            }
        });

    }

    public void InitView() {
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
                        Log.e("\n循环2搞出：","\n"+"\n"+Password+"\n"+Name+"\n"+TEL+"\n"+zddl+"\n"+jzmm+"\n");

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
    }


    //        换壁纸的函数
    public void exchangeBg(){
        pref = getSharedPreferences("bg_pref", MODE_PRIVATE);
        bgNum = pref.getInt("bg", 2);
        switch (bgNum) {
            case 0:
                outLayout.setBackgroundResource(R.mipmap.bg);
                break;
            case 1:
                outLayout.setBackgroundResource(R.mipmap.bg2);
                break;
            case 2:
                outLayout.setBackgroundResource(R.mipmap.bg3);
                break;
        }

    }
    private void setPagerListener() {
        /* 设置监听事件*/
        mainVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < imgList.size(); i++) {
                    imgList.get(i).setImageResource(R.mipmap.a1);
                }
                imgList.get(position).setImageResource(R.mipmap.a2);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void initPoint() {
//        创建小圆点 ViewPager页面指示器的函数
        for (int i = 0; i < fragmentList.size(); i++) {
            ImageView pIv = new ImageView(this);
            pIv.setImageResource(R.mipmap.a1);
            pIv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) pIv.getLayoutParams();
            lp.setMargins(0,0,20,0);
            imgList.add(pIv);
            pointLayout.addView(pIv);
        }
        imgList.get(imgList.size()-1).setImageResource(R.mipmap.a2);
    }

    private void initPager() {
        /* 创建Fragment对象，添加到ViewPager数据源当中*/
        for (int i = 0; i < cityList.size(); i++) {
            CityWeatherFragment cwFrag = new CityWeatherFragment();
            Bundle bundle = new Bundle();
            bundle.putString("city",cityList.get(i));
            cwFrag.setArguments(bundle);
            fragmentList.add(cwFrag);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.main_iv_add:
                intent.setClass(this, CityManagerActivity.class);
                break;
            /*case R.id.main_iv_more:
                intent.setClass(this,MoreActivity.class);
                break;*/
        }
        startActivity(intent);
    }

    /* 当页面重写加载时会调用的函数，这个函数在页面获取焦点之前进行调用，此处完成ViewPager页数的更新*/
    @Override
    protected void onRestart() {
        super.onRestart();
//        获取数据库当中还剩下的城市集合
        List<String> list = DBManager.queryAllCityName();
        if (list.size()==0) {
            list.add("湖北省 武汉");
        }
        cityList.clear();    //重写加载之前，清空原本数据源
        cityList.addAll(list);
//        剩余城市也要创建对应的fragment页面
        fragmentList.clear();
        initPager();
        adapter.notifyDataSetChanged();
//        页面数量发生改变，指示器的数量也会发生变化，重写设置添加指示器
        imgList.clear();
        pointLayout.removeAllViews();   //将布局当中所有元素全部移除
        initPoint();
        mainVp.setCurrentItem(fragmentList.size()-1);
    }
}
