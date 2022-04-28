package com.example.myapplication;

public class Msg {
    private int tel;
    private String content;
    private String time;
    private String Name;

    public Msg() {

    }

    public Msg(int tel,String content,String time,String Name){
        this.tel = tel;
        this.content = content;
        this.time = time;
        this.Name = Name;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() { return Name; }

    public void setName(String name) { this.Name = name; }
}
