package com.example.myapplication;

import android.util.Log;

import java.util.ArrayList;


public class AllUser{

    static public ArrayList<Msg> allUserMsg = new ArrayList<>();
    static public ArrayList<User> myAllUser = new ArrayList<>();

    public AllUser(){

    }

    public User getNowUser(String nowZH){
        int size = myAllUser.size();
        for (int i = 0; i < size; i++) {
            if (nowZH.equals(String.valueOf(myAllUser.get(i).getTEL()))){
                return myAllUser.get(i);
            }
        }
        return null;
    }

    public boolean isZC(String nowZH){
        int size = myAllUser.size();
        for (int i = 0; i < size; i++) {
            if (nowZH.equals(String.valueOf(myAllUser.get(i).getTEL()))){
                return true;
            }
        }
        return false;
    }

    public boolean isRight(String nowZH,String nowMM){
        int size = myAllUser.size();
        for (int i = 0; i < size; i++) {
            if (nowZH.equals(String.valueOf(myAllUser.get(i).getTEL()))){
                if (nowMM.equals(myAllUser.get(i).getPassword())){
                    return true;
                }
            }
        }
        return false;
    }

}
