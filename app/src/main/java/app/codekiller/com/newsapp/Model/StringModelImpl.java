package app.codekiller.com.newsapp.Model;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import app.codekiller.com.newsapp.app.VolleySingleton;
import app.codekiller.com.newsapp.interfaze.OnStringListener;

/**
 * Created by Lollipop on 2017/12/19.
 */

public class StringModelImpl {
    private Context context;

    public StringModelImpl(Context context){
        this.context = context;
    }

    public void load(String url, final OnStringListener listener){
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error);
            }
        });
        VolleySingleton.getVolleySingleton(context).addToRequestQueue(request);
    }
}
