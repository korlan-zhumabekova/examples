package com.example.korlan.myapplication;

/**
 * Created by Korlan on 03.12.2015.
 */
public class ItemInfo {
    public String key;
    public String value;

    public ItemInfo(String key, String value){
        this.key = key;
        this.value = value;
    }

    public String getItemInfo (){
        return key;
    }
    @Override
    public String toString(){
        if (key.equals("Borrowed")) {
            if (value.equals("0")) {
                return (key + ": No");
            } else {
                return (key + ": Yes");
            }
        }
        return (key + ": " + value);

    }
}
