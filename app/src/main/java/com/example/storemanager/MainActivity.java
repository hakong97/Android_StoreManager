package com.example.storemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.InterruptedByTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private String userID;
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

        //매장정보가 있는지 Check
        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if(success == false) // 매장정보가 없다면
                    {
                        Toast.makeText(getApplicationContext(),"매장정보 등록이 필요합니다!",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, StoreInfoUpdateActivity.class);
                        intent.putExtra("userID", userID);
                        startActivity(intent);
                    }
                    else
                    {
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        StoreInfoRequest storeInfoRequest = new StoreInfoRequest(userID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(storeInfoRequest);

        //Variable for Navigation Drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        //Navigation Draw MenuItem ClickEvent
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) { //네비게이션 드로어 메뉴 선택시
                item.setChecked(true);
                drawerLayout.closeDrawer(GravityCompat.START);

                int id = item.getItemId();
                String title = item.getTitle().toString();

                if(id == R.id.item_home) {
                    Toast.makeText(getApplicationContext(),"현재 화면입니다.",Toast.LENGTH_SHORT).show();
                }
                else if(id == R.id.item_inventory) {
                    Intent inv_intent = new Intent(MainActivity.this, InventoryActivity.class);
                    inv_intent.putExtra("userID", userID);
                    MainActivity.this.startActivity(inv_intent);
                }
                else if(id == R.id.item_predict){

                }
                else if(id == R.id.item_logout){
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP); //기존 Activity 전부 종료
                    Toast.makeText(getApplicationContext(),"로그아웃 되었습니다.",Toast.LENGTH_SHORT).show();
                    MainActivity.this.startActivity(intent);

                }
                return true;
            }
        }); // 네비게이션 드로어 메뉴 선택 리스너 끝

        // List Activity
        listView = (ListView) findViewById(R.id.Listview_store); //listView => Listview_store
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
} //MainActivity 끝