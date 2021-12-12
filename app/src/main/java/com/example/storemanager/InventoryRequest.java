package com.example.storemanager;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class InventoryRequest extends StringRequest {

    // http://118.67.143.82/InventoryList.php
    //Server URL Setting
    final static private String url = "http://118.67.143.82/InventoryList.php";
    private Map<String, String> parameters;

    public InventoryRequest(String userID,  Response.Listener<String> listener)
    {
        super(Method.POST, url, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}
