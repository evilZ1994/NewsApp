package app.codekiller.com.newsapp.interfaze;

import com.android.volley.VolleyError;

/**
 * Created by Lollipop on 2017/12/19.
 */

public interface OnStringListener {
    /**
     * 请求成功时回调
     * @param result
     */
    void onSuccess(String result);

    /**
     * 请求失败时回调
     * @param error
     */
    void onErro(VolleyError error);
}
