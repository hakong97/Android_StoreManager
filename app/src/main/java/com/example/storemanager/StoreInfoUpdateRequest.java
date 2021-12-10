package com.example.storemanager;


import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class StoreInfoUpdateRequest extends StringRequest {

    // 서버 url 설정 (php 파일 연동)
    final static private String URL = "http://118.67.143.82/StoreInfoUpdate.php";
    private Map<String, String> parameters;


    public StoreInfoUpdateRequest(String typeNum,String userID, String storeName, String storeAddr, String storeRegNum, String storeOpentime, String storeClosetime, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        parameters = new HashMap<>();
        parameters.put("storeType", typeNum);
        parameters.put("ownerID", userID);
        parameters.put("storeName", storeName);
        parameters.put("storeAddress", storeAddr);
        parameters.put("storeRegNum", storeRegNum);
        parameters.put("storeOpentime", storeOpentime);
        parameters.put("storeClosetime", storeClosetime);
    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError{
        return parameters;
    }

}
