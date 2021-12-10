package com.example.storemanager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class InventoryAdapter extends BaseAdapter {

    private Context context;
    private List<Inventory> inventoryList;

    public InventoryAdapter(Context context, List<Inventory> inventoryList){
        this.context = context;
        this.inventoryList = inventoryList;
    }

    //출력할 총 갯수를 설정
    @Override
    public int getCount() {
        return inventoryList.size();
    }

    //특정 재고를 반환하는 메소드
    @Override
    public Object getItem(int i){
        return inventoryList.get(i);
    }

    @Override
    public long getItemId(int i){
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        View v = View.inflate(context, R.layout.inventory, null);

        TextView inv_name = (TextView) v.findViewById(R.id.tv_invName);
        TextView inv_count = (TextView) v.findViewById(R.id.tv_invCount);

        inv_name.setText(inventoryList.get(i).getInv_name());
        inv_count.setText(inventoryList.get(i).getInv_count());
        return v;

    }
}
