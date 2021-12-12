package com.example.storemanager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class InvListAdapter extends BaseAdapter {

    private Context context;
    private List<Inventory> InvList;

    public InvListAdapter(Context context, List<Inventory> InvList){
        this.context = context;
        this.InvList = InvList;
    }

    //출력할 총 갯수를 설정
    @Override
    public int getCount() {
        return InvList.size();
    }

    //특정 매장을 반환하는 메소드
    @Override
    public Object getItem(int i){
        return InvList.get(i);
    }

    @Override
    public long getItemId(int i){
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        View v = View.inflate(context, R.layout.inventory, null);

        TextView inv_Name = (TextView)v.findViewById(R.id.tv_invName);
        TextView inv_Count = (TextView)v.findViewById(R.id.tv_invCount);

        inv_Name.setText(InvList.get(i).getInv_name());
        inv_Count.setText(InvList.get(i).getInv_count());

        return v;

    }
}
