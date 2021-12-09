package com.example.storemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ShopListAdapter adapter;
    private List<Shop> shopList;

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        //Home Menu Button on Toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        // 현재 메모리에 올라가있는 뷰는 activity_main의 최상위인 Drawable임.
        // 그렇기 때문에 메모리에 navi_header의 뷰를 올려놓아야함.
        //LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //int layoutId = R.layout.navi_header;
        //navi_header_view = inflater.inflate(layoutId, null);

        //if (navi_header_view != null) { //이후 Navigation header 의 이름 설정
        //    TextView tv_name = (TextView) navi_header_view.findViewById(R.id.tv_name);
        //    Intent intent = getIntent();
        //    String userID = intent.getStringExtra("userID");
         //   tv_name.setText(userID); }

        //매장정보 List
        Intent intent = getIntent();
        listView = (ListView) findViewById(R.id.Listview_store);
        shopList  = new ArrayList<Shop>();

        //Adapter 초기화
        adapter = new ShopListAdapter(getApplicationContext(),shopList);
        listView.setAdapter(adapter);

        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("shopList"));

            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;

            String ShopName, ShopLocation, ShopOpentime, ShopCloseTime;

            while(count<jsonArray.length()){
                JSONObject object = jsonArray.getJSONObject(count);
                ShopName = object.getString("S_NAME");
                ShopLocation = object.getString("S_LOCATION");
                ShopOpentime = object.getString("S_OPENTIME");
                ShopCloseTime = object.getString("S_CLOSETIME");

                Shop shop = new Shop(ShopName, ShopLocation, ShopOpentime, ShopCloseTime);
                shopList.add(shop);
                count++;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    } //onCreate 끝

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ // 왼쪽 상단 버튼 눌렀을 때
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() { //뒤로가기 했을 때
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}