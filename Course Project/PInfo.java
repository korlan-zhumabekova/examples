package com.example.korlan.myapplication;

/**
 * Created by Korlan on 04.12.2015.
 */
public class PInfo {
    public String key;
    public String value;

    public PInfo(String key, String value){
        this.key = key;
        this.value = value;
    }

    public String getPInfo (){
        return key;
    }
    @Override
    public String toString(){
       return (key + ": " + value);

    }
}
