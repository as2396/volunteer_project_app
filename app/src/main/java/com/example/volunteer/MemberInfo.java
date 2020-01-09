package com.example.volunteer;

import android.widget.EditText;

public class MemberInfo {
    private  String uid;
    private  String  id;
    private  String password;
    private String email;
    private  String address;
    private  String day;
    private  String number;
    private  String usergender;

    public MemberInfo() {

    }

    public MemberInfo(String uid, String id, String password, String email, String address, String day, String number, String usergender){
        this.uid = uid;
        this.id = id;
        this.password = password;
        this.email = email;
        this.address = address;
        this.day = day;
        this.number = number;
        this.usergender = usergender;
    }


    public String getUid(){
        return this.uid;
    }

    public void setUid(String uid){
        this.uid = uid;
    }

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }
    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getAddress(){
        return this.address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getDay(){
        return this.day;
    }

    public void setDay(){
        this.day = day;
    }

    public String getNumber(){
        return this.number;
    }

    public void setNumber(){
        this.number = number;
    }

    public String getUsergender(){
        return this.usergender;
    }

    public void setUsergender(){
        this.usergender = usergender;
    }
}
