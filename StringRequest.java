package com.example.ksvcem;

import com.android.volley.Response;
import com.android.volley.RetryPolicy;

import java.util.Map;

public class StringRequest {
    public StringRequest(int post, String url, Response.Listener<String> stringListener, Response.ErrorListener errorListener) {
    }

    protected Map<String, String> getParams() {
        return null;
    }

    public static void setRetryPolicy(RetryPolicy retryPolicy) {
    }
}
