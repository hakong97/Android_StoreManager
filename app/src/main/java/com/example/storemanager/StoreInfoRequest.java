package com.example.storemanager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class StoreInfoRequest extends StringRequest {
    //Server URL Setting
    final static private String url = "http://118.67.143.82/CheckStore.php";
    private Map<String, String> parameters;

    public StoreInfoRequest(String userID, Response.Listener<String> listener)
    {
        super(Request.Method.POST, url, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}
