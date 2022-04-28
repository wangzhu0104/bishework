package com.example.myapplication;

import java.util.ArrayList;
import java.util.Calendar;

public class User {
    private  String Password;//密码
    private  String Name;//姓名
    private  int TEL;//手机号
    private int Number;//留言次数
    private boolean zddl;//自动登陆
    private boolean jzmm;//记住密码
    public User(){

    }

    public User(int Tel, String Password){
        this.Password = Password;
        this.Name = "";
        this.TEL = Tel;

        this.zddl = false;
        this.jzmm = false;
    }

    public User( String Password, String Name,int Number,
                int TEL,boolean zddl,boolean jzmm) {


        this.Password = Password;
        this. Name =  Name;
        this.TEL = TEL;
        this.Number = Number;

        this.zddl = zddl;
        this.jzmm = jzmm;
    }



    public boolean isJzmm() {
        return jzmm;
    }

    public boolean isZddl() {
        return zddl;
    }


    public void setZddl(boolean b){
        this.zddl = b;
    }



    public void setJzmm(boolean b){
        this.jzmm = b;
    }

    public String getPassword() { return Password; }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public int getTEL(){
        return TEL;
    }

    public void setTEL(int TEL){
        this.TEL = TEL;
    }

    public int getNumber() { return Number; }

    public void setNumber(int Number) { this.Number = Number; }

    @Override
    public String toString(){
        return String.valueOf(this.getTEL());
    }

}
