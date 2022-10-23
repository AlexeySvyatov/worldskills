package com.example.proekt;

public class MainTable {
    private String name;
    private String direction;
    private String date;

    public MainTable(String name, String date){
        this.name = name;
        this.date = date;
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
}
