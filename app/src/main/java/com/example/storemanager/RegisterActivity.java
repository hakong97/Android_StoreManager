package com.example.storemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar; //setSupportActionBar

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private EditText input_id, input_password, input_name, input_email, input_phone;
    private Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) { //Activity 시작되고 처음으로 실행되는 부분
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.register_toolbar);
        setSupportActionBar(toolbar);

        //각 Component id값 찾아주기
        input_id = findViewById(R.id.input_id);
        input_password = findViewById(R.id.input_password);
        input_name = findViewById(R.id.input_name);
        input_email = findViewById(R.id.input_email);
        input_phone = findViewById(R.id.input_phone);

        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) //회원가입 완료 Button Click
            {

                String userID = input_id.getText().toString();
                String userPassword = input_password.getText().toString();
                String userName = input_name.getText().toString();
                String userEmail = input_email.getText().toString();
                String userPhone = input_phone.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success) //회원등록 성공
                            {
                                Toast.makeText(getApplicationContext(),"회원등록 성공!",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                            else //회원등록 실패
                            {
                                Toast.makeText(getApplicationContext(),"회원등록 실패!",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                //서버로 Volley 이용하여 요청
                RegisterRequest registerRequest = new RegisterRequest(userID, userPassword, userName, userEmail, userPhone, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            } //onClick 끝

        });

    }
}