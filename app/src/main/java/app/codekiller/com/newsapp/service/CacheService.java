package app.codekiller.com.newsapp.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import app.codekiller.com.newsapp.app.VolleySingleton;
import app.codekiller.com.newsapp.bean.ZhihuDailyContent;
import app.codekiller.com.newsapp.db.DatabaseHelper;
import app.codekiller.com.newsapp.util.Api;

/**
 * Created by Lollipop on 2017/12/21.
 */

public class CacheService extends Service {
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    private static final String TAG = CacheService.class.getSimpleName();
    public static final int TYPE_ZHIHU = 0x00;
    public static final int TYPE_GUOKR = 0x01;
    public static final int TYPE_DOUBAN = 0x02;

    private Gson gson = new Gson();

    @Override
    public void onCreate() {
        super.onCreate();

        databaseHelper = DatabaseHelper.getInstance(this);
        database = databaseHelper.getWritableDatabase();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("LOCAL_BROADCAST");
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);
        manager.registerReceiver(new LocalReceiver(), intentFilter);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 网络请求id对应的知乎日报的内容主体
     * 当type为0时，存储body中的数据
     * 当type为1时，再次请求share url中的内容并存储
     * @param id 知乎日报内容对应的id
     */
    private void startZhihuCache(final int id){
        Cursor cursor = database.query("Zhihu", null, "id=?", new String[]{"1"}, null, null, null);
        while (cursor.moveToNext()){
            if (cursor.getInt(cursor.getColumnIndex("zhihu_id")) == id && cursor.getString(cursor.getColumnIndex("zhihu_content")).equals("")){
                StringRequest request = new StringRequest(Request.Method.GET, Api.ZHIHU_NEWS + id, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ZhihuDailyContent content = gson.fromJson(response, ZhihuDailyContent.class);
                        if (content.getType() == 1){
                            StringRequest request2 = new StringRequest(Request.Method.GET, content.getShare_url(), new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    database.beginTransaction();
                                    ContentValues values = new ContentValues();
                                    values.put("zhihu_content", response);
                                    database.update("Zhihu", values, "zhihu_id=?", new String[]{String.valueOf(id)});
                                    database.endTransaction();
                                    values.clear();
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            });
                            request2.setTag(TAG);
                            VolleySingleton.getVolleySingleton(CacheService.this).addToRequestQueue(request2);
                        } else {
                            database.beginTransaction();
                            ContentValues values = new ContentValues();
                            values.put("zhihu_content", response);
                            database.update("Zhihu", values, "zhihu_id=?", new String[]{String.valueOf(id)});
                            database.endTransaction();
                            values.clear();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                request.setTag(TAG);
                VolleySingleton.getVolleySingleton(this).addToRequestQueue(request);
            }
        }
        cursor.close();
    }

    private void startGuokrCache(final int id) {
        Cursor cursor = database.query("Guokr", null, "guokr_id=?", new String[]{String.valueOf(id)}, null, null, null);
        while (cursor.moveToNext()){
            if (cursor.getInt(cursor.getColumnIndex("guokr_id")) == id && cursor.getString(cursor.getColumnIndex("guokr_content")).equals("")){
                StringRequest request = new StringRequest(Request.Method.GET, Api.GUOKR_ARTICLES + id, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        database.beginTransaction();
                        ContentValues values = new ContentValues();
                        values.put("guokr_content", response);
                        database.update("Guokr", values, "guokr_id=?", new String[]{String.valueOf(id)});
                        values.clear();
                        database.endTransaction();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

                request.setTag(TAG);
                VolleySingleton.getVolleySingleton(this).addToRequestQueue(request);
            }
        }
        cursor.close();
    }

    private void startDoubanCache(final int id) {
        Cursor cursor = database.query("Douban", null, "douban_id=?", new String[]{String.valueOf(id)}, null, null, null);
        while (cursor.moveToNext()){
            if (cursor.getInt(cursor.getColumnIndex("douban_id")) == id && cursor.getString(cursor.getColumnIndex("douban_content")).equals("")){
                StringRequest request = new StringRequest(Request.Method.GET, Api.DOUBAN_ARTICLE_DETAIL + id, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        database.beginTransaction();
                        ContentValues values = new ContentValues();
                        values.put("douban_content", response);
                        database.update("Douban", values, "douban_id=?", new String[]{String.valueOf(id)});
                        values.clear();
                        database.endTransaction();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

                request.setTag(TAG);
                VolleySingleton.getVolleySingleton(this).addToRequestQueue(request);
            }
        }
        cursor.close();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        VolleySingleton.getVolleySingleton(this).getRequestQueue().cancelAll(TAG);
    }

    class LocalReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            int id = intent.getIntExtra("id", 0);
            switch (intent.getIntExtra("type", -1)){
                case TYPE_ZHIHU:
                    startZhihuCache(id);
                    break;
                case TYPE_GUOKR:
                    startGuokrCache(id);
                    break;
                case TYPE_DOUBAN:
                    startDoubanCache(id);
                    break;
                default:
                    break;
            }
        }
    }
}
