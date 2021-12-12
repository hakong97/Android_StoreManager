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
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class InventoryActivity extends AppCompatActivity {
    private String userID;
    private ListView listView;
    private InvListAdapter adapter;
    private List<Inventory> inventoryList;

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");

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
                    new InventoryActivity.BackgroundTask().execute(userID);
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
        listView = (ListView)findViewById(R.id.Listview_Inventory); //listView => Listview_Inventory
        inventoryList = new ArrayList<Inventory>();

        //Adapter 초기화
        adapter = new InvListAdapter(getApplicationContext(),inventoryList);
        listView.setAdapter(adapter);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("response");
                    int count = 0;

                    String inventoryName, inventoryCount;

                    while(count<jsonArray.length()) {
                        JSONObject object = jsonArray.getJSONObject(count);
                        inventoryName = object.getString("I_NAME");
                        inventoryCount = object.getString("I_CURRENT");


                        Inventory inventory = new Inventory(inventoryName, inventoryCount);
                        inventoryList.add(inventory);
                        count++;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        InventoryRequest inventoryRequest = new InventoryRequest(userID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(InventoryActivity.this);
        queue.add(inventoryRequest);

    }
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

    class BackgroundTask extends AsyncTask<String, Void, String> { //매장정보 리스트 쓰레드
        String target;
        String userID;
        @Override
        protected void onPreExecute() {
            //List.php은 파싱으로 가져올 웹페이지
            target = "http://118.67.143.82/StoreList.php";
        }

        @Override
        protected String doInBackground(String... userid) {
            try{
                userID = userid[0];
                URL url = new URL(target);//URL 객체 생성

                //URL을 이용해서 웹페이지에 연결하는 부분
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();

                //바이트단위 입력스트림 생성 소스는 httpURLConnection
                InputStream inputStream = httpURLConnection.getInputStream();

                //웹페이지 출력물을 버퍼로 받음 버퍼로 하면 속도가 더 빨라짐
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;

                //문자열 처리를 더 빠르게 하기 위해 StringBuilder클래스를 사용함
                StringBuilder stringBuilder = new StringBuilder();

                //한줄씩 읽어서 stringBuilder에 저장함
                while((temp = bufferedReader.readLine()) != null){
                    stringBuilder.append(temp + "\n");//stringBuilder에 넣어줌
                }

                //사용했던 것도 다 닫아줌
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();//trim은 앞뒤의 공백을 제거함

            }catch(Exception e){
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            Intent intent = new Intent(InventoryActivity.this, MainActivity.class);
            intent.putExtra("userID", userID);
            intent.putExtra("shopList", result);//파싱한 값을 넘겨줌
            InventoryActivity.this.startActivity(intent);//MainActivity 로 넘어감

        }
    } //BackgroundTask 끝
}