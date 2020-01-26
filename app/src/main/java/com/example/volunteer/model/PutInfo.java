package com.example.volunteer.model;

public class PutInfo {
    private  String publisher;
    private  String name;
    private  String memo;
    private  String airport;
    private  String year;
    private  String month;
    private  String day;
    private  String id;
    private Object DateTime;

    public PutInfo(String publisher, String name, String memo, String airport, String year, String month, String day, String id){
        this.publisher = publisher;
        this.name = name;
        this.memo = memo;
        this.airport = airport;
        this.year = year;
        this.month = month;
        this.day = day;
        this.id = id;
    }

    public PutInfo(String publisher, String name, String memo, String airport, String year, String month, String day){
        this.publisher = publisher;
        this.name = name;
        this.memo = memo;
        this.airport = airport;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublisher(){
        return this.publisher;
    }

    public void setPublisher(String publisher){
        this.publisher = publisher;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getMemo(){
        return this.memo;
    }

    public void setMemo(String memo){
        this.memo = memo;
    }

    public String getAirport(){
        return this.airport;
    }

    public void setAirport(String airport){
        this.airport = airport;
    }

    public String getYear(){
        return this.year;
    }

    public void setYear(String year){
        this.year = year;
    }

    public String getMonth(){
        return this.month;
    }

    public void setMonth(String month){
        this.month = month;
    }

    public String getDay(){
        return this.day;
    }

    public void setDay(){
        this.day = day;
    }

    public Object getDateTime() { return  this.DateTime; }

    public void setDateTime(Object datetime) { DateTime = datetime; }

}
