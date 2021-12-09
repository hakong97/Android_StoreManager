package com.example.storemanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private EditText login_id, login_password;
    private Button btn_login, btn_reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.login_toolbar);
        setSupportActionBar(toolbar);

        login_id = findViewById(R.id.login_id);
        login_password = findViewById(R.id.login_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_reg =(Button) findViewById(R.id.btn_reg);

        btn_reg.setOnClickListener(new View.OnClickListener() { //회원가입 Button Click
            @Override
            public void onClick(View view) { //onClick 시작
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            } //onClick 끝
        }); //btn_reg.setOnClickListener 끝

        btn_login.setOnClickListener(new View.OnClickListener() { //로그인 Button Click
            @Override
            public void onClick(View view) {
                String userID = login_id.getText().toString();
                String userPassword = login_password.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success) //로그인 성공
                            {
                                String userID = jsonObject.getString("userID");
                                String userPassword = jsonObject.getString("userPassword");

                                Toast.makeText(getApplicationContext(),"로그인 성공!",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("userID", userID);
                                startActivity(intent);
                            }
                            else //로그인 실패
                            {
                                Toast.makeText(getApplicationContext(),"로그인 실패!",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } //onResponse 끝
                }; //Response.Listener<String> responseListener 끝

                LoginRequest loginRequest = new LoginRequest(userID,userPassword, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            } //onClick 끝
        }); //btn_login.setOnClickListener 끝

    }
}