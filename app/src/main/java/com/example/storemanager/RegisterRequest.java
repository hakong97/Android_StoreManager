package com.example.storemanager;


import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    //Server URL Setting
    final static private String url = "http://118.67.143.82/Register.php";
    private Map<String, String> parameters;

    public RegisterRequest(String userID, String userPassword, String userName, String userEmail, String userPhone, Response.Listener<String> listener)
    {
        super(Method.POST, url, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
        parameters.put("userName", userName);
        parameters.put("userEmail",userEmail);
        parameters.put("userPhone",userPhone);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError
    {
        return parameters;
    }
}
