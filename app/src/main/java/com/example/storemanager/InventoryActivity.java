package com.example.storemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class InventoryActivity extends AppCompatActivity {

    private ListView listView;
    private InventoryAdapter adapter;
    private List<Inventory> inventoryList;

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        //Home Menu Button on Toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);

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
                    Intent intent = new Intent(InventoryActivity.this, MainActivity.class);
                    InventoryActivity.this.startActivity(intent);
                }
                else if(id == R.id.item_inventory){
                    Toast.makeText(getApplicationContext(),"현재 화면입니다.",Toast.LENGTH_SHORT).show();
                }
                else if(id == R.id.item_predict){

                }
                else if(id == R.id.item_logout){
                    Intent intent = new Intent(InventoryActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP); //기존 Activity 전부 종료
                    Toast.makeText(getApplicationContext(),"로그아웃 되었습니다.",Toast.LENGTH_SHORT).show();
                    InventoryActivity.this.startActivity(intent);

                }
                return true;
            }
        }); // 네비게이션 드로어 메뉴 선택 리스너 끝

        // List Activity
        listView = (ListView) findViewById(R.id.Listview_store); //listView => Listview_store
        inventoryList  = new ArrayList<Inventory>();
        Intent intent = getIntent();

        //Adapter 초기화
        adapter = new InventoryAdapter(getApplicationContext(),inventoryList);
        listView.setAdapter(adapter);

        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("inventoryList"));

            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;

            String inventoryName, inventoryCount;

            while(count<jsonArray.length()){
                JSONObject object = jsonArray.getJSONObject(count);
                inventoryName = object.getString("inv_Name");
                inventoryCount = object.getString("inv_Count");


                Inventory inventory = new Inventory(inventoryName, inventoryCount);
                inventoryList.add(inventory);
                count++;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}