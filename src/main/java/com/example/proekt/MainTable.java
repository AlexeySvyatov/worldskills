package com.example.proekt;

public class MainTable {
    private String name;
    private String date;
    private Integer days;
    private Integer city;

    public MainTable(String name, String date, Integer days, Integer city){
        this.name = name;
        this.date = date;
        this.days = days;
        this.city = city;
    }

    public MainTable() {}

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getDate(){
        return date;
    }
    public void setDate(String date){
        this.date = date;
    }

    public Integer getDays(){
        return days;
    }
    public void setDays(Integer days){
        this.days = days;
    }

    public Integer getCity(){
        return city;
    }
    public void setCity(Integer city){
        this.city = city;
    }
}
