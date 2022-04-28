package com.example.myapplication.activity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.AllUser;
import com.example.myapplication.ListAdapter;
import com.example.myapplication.Msg;
import com.example.myapplication.R;
import com.example.myapplication.RequestData;
import com.example.myapplication.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.myapplication.activity.LoginActivity.usertel;
import static com.example.myapplication.activity.MainActivity.alluser;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView backTv;
    Button sendBtn,flashBtn;
    TextView chatTv,chatnameTv;
    EditText etMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        //InitView();
        backTv = findViewById(R.id.chat_iv_back);
        flashBtn = findViewById(R.id.chat_flash);

        chatTv = findViewById(R.id.chat_text);
        chatnameTv = findViewById(R.id.chat_user_name);

        sendBtn = findViewById(R.id.chat_send);
        etMsg = findViewById(R.id.chat_edit);
        InitView();
        ListView listview = findViewById(R.id.chat_msg);
        MyBaseAdapter mAdapter= new MyBaseAdapter();
        //加载适配器
        listview.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        chatTv.setText(alluser.getNowUser(usertel).getName() + "，您要说什么：（剩余留言次数：" + alluser.getNowUser(usertel).getNumber() + "次。");

        flashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InitView();
                ListView listview = findViewById(R.id.chat_msg);
                MyBaseAdapter mAdapter= new MyBaseAdapter();
                //加载适配器
                listview.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mynumber = alluser.getNowUser(usertel).getNumber();
                if (mynumber == 0){
                    Toast.makeText(ChatActivity.this,"您的留言次数不足，请充值！",Toast.LENGTH_SHORT).show();
                    return;
                }
                sendMsg(view);
                --mynumber;
                chatTv.setText(alluser.getNowUser(usertel).getName() + "，您要说什么：（剩余留言次数：" + mynumber + "次。");
                Toast.makeText(ChatActivity.this,"发送成功！",Toast.LENGTH_SHORT).show();
            }
        });



        backTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    class MyBaseAdapter extends BaseAdapter {

        @Override
        public int getCount(){       //得到item的总数
            return alluser.allUserMsg.size();    //返回ListView Item条目代表的对象
        }

        @Override
        public Object getItem(int position){
            return alluser.allUserMsg.get(position); //返回item的数据对象
        }
        @Override
        public long getItemId(int position){
            return position;         //返回item的id
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent){//获取item中的View视图
            ViewHolderdingdan holder;
            if(convertView==null){
                convertView=View.inflate(ChatActivity.this,R.layout.list_item, null);
                holder=new ViewHolderdingdan();
                holder.textView=convertView.findViewById(R.id.chat_user_name);
                holder.textView2=convertView.findViewById(R.id.chat_time);
                holder.textView3=convertView.findViewById(R.id.chat_textview);
                convertView.setTag(holder);
            }else{
                holder=(ViewHolderdingdan)convertView.getTag();
            }

            holder.textView.setText(alluser.allUserMsg.get(position).getName());
            holder.textView2.setText(alluser.allUserMsg.get(position).getTime());
            holder.textView3.setText(alluser.allUserMsg.get(position).getContent());
            return convertView;
        }
    }

    public void sendMsg(View view) {
        //获取用户输入的信息和系统时间，封装成Msg对象，添加到集合中
        String msg = etMsg.getText().toString();
        if (msg.length()==0 || "".equals(msg)) {
            Toast.makeText(this,"请输入您要发送的内容！", Toast.LENGTH_SHORT).show();
            return;
        }
        //获取系统时间
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeStr = sdf.format(date);
        //获取用户名
        String Name = alluser.getNowUser(usertel).getName();

        RequestData requestData9;
        RequestData.myCallback myCallback9 = new RequestData.myCallback(){
            @SuppressLint("LongLogTag")
            @Override
            public void onSuccess(String response9) {
                Log.e("9，注册，创建用户ID，tbName，Password",response9);
                try {
                    JSONObject jsonObject2 = new JSONObject(response9);
                    int status = jsonObject2.getInt("status");
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
        requestData9.setUrl("http://124.220.159.4/upchat.php");
        List<String> key9 = new ArrayList<>();
        key9.add("TEL");
        key9.add("MSG");
        key9.add("TIME");
        key9.add("NAME");

        List<String> param9 = new ArrayList<>();
        param9.add(usertel);//变量名ID
        param9.add(msg);
        param9.add(timeStr);
        param9.add(Name);
        requestData9.post(key9,param9);



        RequestData requestData10;
        RequestData.myCallback myCallback10 = new RequestData.myCallback(){
            @SuppressLint("LongLogTag")
            @Override
            public void onSuccess(String response10) {
                Log.e("9，注册，创建用户ID，tbName，Password",response10);
                try {
                    JSONObject jsonObject3 = new JSONObject(response10);
                    int status = jsonObject3.getInt("status");
                    Log.e("\n循环：","\n"+status+"\n");

                } catch (JSONException ee) {
                    ee.printStackTrace();
                }

            }
            //
            @Override
            public void onFail(IOException ee) {

            }
        };

        //9，注册，创建用户ID，tbName，Password

        requestData10 = new RequestData(myCallback9);
        requestData10.setUrl("http://124.220.159.4/chatnumber.php");
        List<String> key10 = new ArrayList<>();
        key10.add("TEL");

        List<String> param10 = new ArrayList<>();
        param10.add(usertel);//变量名ID
        requestData10.post(key10,param10);

        etMsg.setText("");

        InitView();
        ListView listview = findViewById(R.id.chat_msg);
        MyBaseAdapter mAdapter= new MyBaseAdapter();
        //加载适配器
        listview.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View view) {

    }

    public void InitView() {
        //user开始了

        RequestData requestData22;
        RequestData.myCallback myCallback22 = new RequestData.myCallback(){
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

        requestData22 = new RequestData(myCallback22);
        requestData22.setUrl("http://124.220.159.4/query.php");
        List<String> key22 = new ArrayList<>();
        key22.add("tbName");
        List<String> param22 = new ArrayList<>();
        param22.add("user");
        //requestData.get();
        requestData22.post(key22,param22);
        if (alluser == null)
            alluser = new AllUser();

        RequestData requestData2;
        RequestData.myCallback myCallback2 = new RequestData.myCallback(){
            @Override
            public void onSuccess(String response2) {
                Log.e("chat",response2);
                try {
                    JSONArray jsonArray = new JSONArray(response2);
                    Log.e("jsarrychat", String.valueOf(jsonArray));
                    //JSONObject jsonObject = new JSONObject(response);
                    alluser.allUserMsg.clear();
                    int m = jsonArray.length();
                    for (int i = 0;i < jsonArray.length();i++){
                        JSONObject jsonObject1 = (JSONObject)jsonArray.get(i);

                        int ID = jsonObject1.getInt("Id");
                        int TEL = jsonObject1.getInt("TEL");

                        String NAME = jsonObject1.getString("NAME");
                        String MSG = jsonObject1.getString("MSG");
                        String  TIME = jsonObject1.getString("TIME");


                        Log.e("\n循环10搞出：","\n"+"\n"+ID+"\n"+TEL+"\n"+MSG+"\n"+TIME+"\n"+NAME+"\n");

                        innallchat(ID,TEL,MSG,TIME,NAME);
                    }
                    Log.e("json","王铁柱");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            public void innallchat( int ID, int TEL,String MSG, String TIME, String NAME){
                alluser.allUserMsg.add(new Msg(TEL,MSG,TIME,NAME));
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
        param2.add("chat");
        //requestData.get();
        requestData2.post(key2,param2);
    }

}
