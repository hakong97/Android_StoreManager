package com.example.storemanager;

public class Shop {
    String Shop_Name;
    String Shop_Location;
    String Shop_Opentime;
    String Shop_Closetime;

    public String getShop_Name() {
        return Shop_Name;
    }

    public String getShop_Location() {
        return Shop_Location;
    }

    public String getShop_Opentime() {
        return Shop_Opentime;
    }

    public String getShop_Closetime() {
        return Shop_Closetime;
    }

    public void setShop_Name(String shop_Name) {
        Shop_Name = shop_Name;
    }

    public void setShop_Location(String shop_Location) {
        Shop_Location = shop_Location;
    }

    public void setShop_Opentime(String shop_Opentime) {
        Shop_Opentime = shop_Opentime;
    }

    public void setShop_Closetime(String shop_Closetime) {
        Shop_Closetime = shop_Closetime;
    }

    public Shop(String shop_Name, String shop_Location, String shop_Opentime, String shop_Closetime) {
        Shop_Name = shop_Name;
        Shop_Location = shop_Location;
        Shop_Opentime = shop_Opentime;
        Shop_Closetime = shop_Closetime;
    }
}
