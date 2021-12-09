package com.example.storemanager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ShopListAdapter extends BaseAdapter {

    private Context context;
    private List<Shop> shopList;

    public ShopListAdapter(Context context, List<Shop> shopList){
        this.context = context;
        this.shopList = shopList;
    }

    //출력할 총 갯수를 설정
    @Override
    public int getCount() {
        return shopList.size();
    }

    //특정 매장을 반환하는 메소드
    @Override
    public Object getItem(int i){
        return shopList.get(i);
    }

    @Override
    public long getItemId(int i){
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        View v = View.inflate(context, R.layout.shop, null);

        TextView shop_Name = (TextView) v.findViewById(R.id.Shop_Name);
        TextView shop_Location = (TextView) v.findViewById(R.id.Shop_Location);
        TextView shop_Opentime = (TextView) v.findViewById(R.id.Shop_Opentime);
        TextView shop_Closetime = (TextView) v.findViewById(R.id.SHop_Closetime);

        shop_Name.setText(shopList.get(i).getShop_Name());
        shop_Location.setText(shopList.get(i).getShop_Location());
        shop_Opentime.setText(shopList.get(i).getShop_Opentime());
        shop_Closetime.setText(shopList.get(i).Shop_Closetime);

        return v;

    }
}
