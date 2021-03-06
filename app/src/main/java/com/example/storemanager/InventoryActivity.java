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
            public boolean onNavigationItemSelected(@NonNull MenuItem item) { //??????????????? ????????? ?????? ?????????
                item.setChecked(true);
                drawerLayout.closeDrawer(GravityCompat.START);

                int id = item.getItemId();
                String title = item.getTitle().toString();

                if(id == R.id.item_home) {
                    new InventoryActivity.BackgroundTask().execute(userID);
                }
                else if(id == R.id.item_inventory){
                    Toast.makeText(getApplicationContext(),"?????? ???????????????.",Toast.LENGTH_SHORT).show();
                }
                else if(id == R.id.item_predict){

                }
                else if(id == R.id.item_logout){
                    Intent intent = new Intent(InventoryActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP); //?????? Activity ?????? ??????
                    Toast.makeText(getApplicationContext(),"???????????? ???????????????.",Toast.LENGTH_SHORT).show();
                    InventoryActivity.this.startActivity(intent);

                }
                return true;
            }
        }); // ??????????????? ????????? ?????? ?????? ????????? ???

        // List Activity
        listView = (ListView)findViewById(R.id.Listview_Inventory); //listView => Listview_Inventory
        inventoryList = new ArrayList<Inventory>();

        //Adapter ?????????
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
            case android.R.id.home:{ // ?????? ?????? ?????? ????????? ???
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() { //???????????? ?????? ???
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    class BackgroundTask extends AsyncTask<String, Void, String> { //???????????? ????????? ?????????
        String target;
        String userID;
        @Override
        protected void onPreExecute() {
            //List.php??? ???????????? ????????? ????????????
            target = "http://118.67.143.82/StoreList.php";
        }

        @Override
        protected String doInBackground(String... userid) {
            try{
                userID = userid[0];
                URL url = new URL(target);//URL ?????? ??????

                //URL??? ???????????? ??????????????? ???????????? ??????
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();

                //??????????????? ??????????????? ?????? ????????? httpURLConnection
                InputStream inputStream = httpURLConnection.getInputStream();

                //???????????? ???????????? ????????? ?????? ????????? ?????? ????????? ??? ?????????
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;

                //????????? ????????? ??? ????????? ?????? ?????? StringBuilder???????????? ?????????
                StringBuilder stringBuilder = new StringBuilder();

                //????????? ????????? stringBuilder??? ?????????
                while((temp = bufferedReader.readLine()) != null){
                    stringBuilder.append(temp + "\n");//stringBuilder??? ?????????
                }

                //???????????? ?????? ??? ?????????
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();//trim??? ????????? ????????? ?????????

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
            intent.putExtra("shopList", result);//????????? ?????? ?????????
            InventoryActivity.this.startActivity(intent);//MainActivity ??? ?????????

        }
    } //BackgroundTask ???
}