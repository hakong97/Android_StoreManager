package com.example.storemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

public class StoreInfoUpdateActivity extends AppCompatActivity {

    //MainActivity에서 받아오기 위한 변수
    String userID;

    //카테고리 드롭다운 변수 선언
    String[] items = {"카페", "한식", "중국음식", "패스트푸드", "양식", "치킨", "피자", "일식", "분식"};
    AutoCompleteTextView et_category;
    ArrayAdapter<String> adapterItems;
    int TypeNum;

    //매장정보 입력 변수 선언
    private TextInputEditText et_storeName, et_Addr, et_RegNum, et_openHour, et_openMin, et_closeHour, et_closeMin;
    private Button btn_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_info_update);

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        //카테고리 드롭다운
        et_category = findViewById(R.id.et_category);
        adapterItems = new ArrayAdapter<String>(this, R.layout.category_list_item,items);
        et_category.setAdapter(adapterItems);
        et_category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                switch (item) {
                    case "카페":
                        TypeNum = 1;
                        break;
                    case "한식":
                        TypeNum = 2;
                        break;
                    case "중국음식":
                        TypeNum = 3;
                        break;
                    case "패스트푸드":
                        TypeNum = 4;
                        break;
                    case "양식":
                        TypeNum = 5;
                        break;
                    case "치킨":
                        TypeNum = 6;
                        break;
                    case "피자":
                        TypeNum = 7;
                        break;
                    case "일식":
                        TypeNum = 8;
                        break;
                    case "분식":
                        TypeNum = 9;
                        break;
                }
            }
        });

       //Update Activity
        //각 Component 연결
        et_storeName = findViewById(R.id.et_storeName);
        et_Addr = findViewById(R.id.et_Addr);
        et_RegNum = findViewById(R.id.et_RegNum);
        et_openHour = findViewById(R.id.et_openHour);
        et_openMin = findViewById(R.id.et_openMin);
        et_closeHour = findViewById(R.id.et_closeHour);
        et_closeMin = findViewById(R.id.et_closeMin);
        btn_update = findViewById(R.id.btn_update);

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                userID = intent.getStringExtra("userID");
                String storeName = et_storeName.getText().toString();
                String storeAddr = et_Addr.getText().toString();
                String storeRegNum = et_RegNum.getText().toString();
                String storeOpentime = et_openHour.getText().toString() + ":" + et_openMin.getText().toString() + ":00";
                String storeClosetime = et_closeHour.getText().toString() + ":" + et_closeMin.getText().toString() + ":00";

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success) //매장등록 성공
                            {
                                Toast.makeText(getApplicationContext(),"매장정보 업데이트 완료 다시 로그인해주세요!",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(StoreInfoUpdateActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP); //기존 Activity 전부 종료
                                startActivity(intent);
                            }
                            else //매장등록 실패
                            {
                                Toast.makeText(getApplicationContext(),"매장정보 등록 실패!",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                //서버로 Volley 이용하여 요청
                StoreInfoUpdateRequest storeInfoUpdateRequest = new StoreInfoUpdateRequest(String.valueOf(TypeNum), userID, storeName, storeAddr, storeRegNum, storeOpentime, storeClosetime, responseListener);
                RequestQueue queue = Volley.newRequestQueue(StoreInfoUpdateActivity.this);
                queue.add(storeInfoUpdateRequest);
            }
        });
    } // onCreate 끝
}