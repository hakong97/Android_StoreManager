package com.example.storemanager;

public class Inventory {

    String inv_name;
    String inv_count;

    public String getInv_name() {
        return inv_name;
    }

    public void setInv_name(String inv_name) {
        this.inv_name = inv_name;
    }

    public String getInv_count() {
        return inv_count;
    }

    public void setInv_count(String inv_count) {
        this.inv_count = inv_count;
    }

    public Inventory(String inv_name, String inv_count) {
        this.inv_name = inv_name;
        this.inv_count = inv_count;
    }
}
