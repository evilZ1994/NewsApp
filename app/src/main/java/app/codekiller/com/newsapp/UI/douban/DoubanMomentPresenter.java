package app.codekiller.com.newsapp.UI.douban;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.LocalBroadcastManager;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import app.codekiller.com.newsapp.UI.detail.DetailActivity;
import app.codekiller.com.newsapp.bean.BaseBean;
import app.codekiller.com.newsapp.bean.BeanType;
import app.codekiller.com.newsapp.bean.Douban;
import app.codekiller.com.newsapp.bean.StringModelImpl;
import app.codekiller.com.newsapp.db.DatabaseHelper;
import app.codekiller.com.newsapp.interfaze.OnStringListener;
import app.codekiller.com.newsapp.service.CacheService;
import app.codekiller.com.newsapp.util.Api;
import app.codekiller.com.newsapp.util.DateFormatter;
import app.codekiller.com.newsapp.util.NetworkState;

/**
 * Created by R2D2 on 2017/12/20.
 */

public class DoubanMomentPresenter implements DoubanMomentContract.Presenter {
    private Context context;
    private DoubanMomentContract.View view;
    private SQLiteDatabase database;
    private DatabaseHelper helper;
    private StringModelImpl model;
    private Gson gson;

    private ArrayList<BaseBean> beans;

    public DoubanMomentPresenter(Context context, DoubanMomentContract.View view){
        this.context = context;
        this.view = view;
        view.setPresenter(this);
        helper = DatabaseHelper.getInstance(context);
        database = helper.getWritableDatabase();
        model = new StringModelImpl(context);
        gson = new Gson();
        beans = new ArrayList<>();
    }

    @Override
    public void start() {

    }

    @Override
    public void startReading(int position) {
        BaseBean bean = beans.get(position);
        Intent intent = new Intent(context, DetailActivity.class).putExtra("id", bean.getId())
                .putExtra("type", BeanType.TYPE_DOUBAN)
                .putExtra("title", bean.getTitle());
        List<Douban.PostsBean.ThumbsBean> thumbs = ((Douban.PostsBean)bean).getThumbs();
        if (thumbs.size() > 0){
            intent.putExtra("coverUrl", thumbs.get(0).getLarge().getUrl());
        } else {
            intent.putExtra("coverUrl", "");
        }
        context.startActivity(intent);
    }

    @Override
    public void loadPosts(long date, boolean clearing) {
        view.showLoading();
        if (clearing){
            beans.clear();
        }
        if (NetworkState.networkConnected(context)) {
            model.load(Api.DOUBAN_MOMENT + new DateFormatter().DoubanDateFormat(date), new OnStringListener() {
                @Override
                public void onSuccess(String result) {
                    Douban douban = gson.fromJson(result, Douban.class);
                    for (Douban.PostsBean bean : douban.getPosts()) {
                        beans.add(bean);
                        if (!queryIfIdExists(bean.getId())) {
                            database.beginTransaction();
                            ContentValues values = new ContentValues();
                            values.put("douban_id", bean.getId());
                            values.put("douban_news", gson.toJson(bean));
                            values.put("douban_content", "");
                            database.insert("Douban", null, values);
                            values.clear();
                            database.setTransactionSuccessful();
                            database.endTransaction();

                            //发送广播，缓存主要内容
                            Intent intent = new Intent("LOCAL_BROADCAST");
                            intent.putExtra("type", CacheService.TYPE_DOUBAN);
                            intent.putExtra("id", bean.getId());
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                        }
                    }

                    view.showResults(beans);
                    view.stopLoading();
                }

                @Override
                public void onError(VolleyError error) {
                    view.stopLoading();
                }
            });
        } else {
            Cursor cursor = database.query("Douban", null, null, null, null, null, null);
            if (cursor.moveToFirst()){
                do {
                    Douban.PostsBean postsBean = gson.fromJson(cursor.getString(cursor.getColumnIndex("douban_news")), Douban.PostsBean.class);
                    beans.add(postsBean);
                } while (cursor.moveToNext());
            }
            cursor.close();
            view.showResults(beans);
            view.stopLoading();
        }
    }

    @Override
    public void refresh() {

    }

    @Override
    public void loadMore(long date) {
        loadPosts(date, false);
    }

    @Override
    public void feelLucky() {
        if (beans!=null && beans.size()>0){
            startReading(new Random().nextInt(beans.size()));
        }
    }

    private boolean queryIfIdExists(int id){
        Cursor cursor = database.query("Douban", null, "douban_id=?", new String[]{String.valueOf(id)}, null, null, null);
        while (cursor.moveToNext()){
            if (cursor.getInt(cursor.getColumnIndex("douban_id")) == id){
                return true;
            }
        }

        return false;
    }
}
