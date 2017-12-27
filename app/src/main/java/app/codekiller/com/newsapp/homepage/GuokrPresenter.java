package app.codekiller.com.newsapp.homepage;

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

import app.codekiller.com.newsapp.bean.BaseBean;
import app.codekiller.com.newsapp.bean.BeanType;
import app.codekiller.com.newsapp.bean.GuokrNews;
import app.codekiller.com.newsapp.bean.StringModelImpl;
import app.codekiller.com.newsapp.db.DatabaseHelper;
import app.codekiller.com.newsapp.interfaze.OnStringListener;
import app.codekiller.com.newsapp.service.CacheService;
import app.codekiller.com.newsapp.util.Api;
import app.codekiller.com.newsapp.util.NetworkState;

/**
 * Created by R2D2 on 2017/12/20.
 */

public class GuokrPresenter implements GuokrContract.Presenter{
    private Context context;
    private GuokrContract.View view;
    private StringModelImpl model;

    private Gson gson = new Gson();
    private ArrayList<BaseBean> resultBeans;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public GuokrPresenter(Context context, GuokrContract.View view){
        this.context = context;
        this.view = view;
        view.setPresenter(this);
        model = new StringModelImpl(context);
        resultBeans = new ArrayList<>();
        databaseHelper = DatabaseHelper.getInstance(context);
        database = databaseHelper.getWritableDatabase();
    }

    @Override
    public void start() {

    }

    @Override
    public void loadPosts(boolean clearing) {
        view.showLoading();
        if (clearing){
            resultBeans.clear();
        }
        if (NetworkState.networkConnected(context)) {
            model.load(Api.GUOKR_ARTICLES, new OnStringListener() {
                @Override
                public void onSuccess(String result) {
                    GuokrNews guokrNews = gson.fromJson(result, GuokrNews.class);
                    for (GuokrNews.ResultBean bean : guokrNews.getResult()) {
                        resultBeans.add(bean);
                        if (!queryIfIdExists(bean.getId())) {
                            database.beginTransaction();
                            ContentValues values = new ContentValues();
                            values.put("guokr_id", bean.getId());
                            values.put("guokr_news", gson.toJson(bean));
                            values.put("guokr_content", "");
                            database.insert("Guokr", null, values);
                            values.clear();
                            database.setTransactionSuccessful();
                            database.endTransaction();

                            //发送广播，缓存主要内容
                            Intent intent = new Intent("LOCAL_BROADCAST");
                            intent.putExtra("type", CacheService.TYPE_GUOKR);
                            intent.putExtra("id", bean.getId());
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                        }
                    }
                    view.showResults(resultBeans);
                    view.stopLoading();
                }

                @Override
                public void onError(VolleyError error) {
                    view.stopLoading();
                }
            });
        }else {
            Cursor cursor = database.query("Guokr", null, null, null, null, null, null );
            if (cursor.moveToFirst()){
                do {
                    GuokrNews.ResultBean resultBean = gson.fromJson(cursor.getString(cursor.getColumnIndex("guokr_news")), GuokrNews.ResultBean.class);
                    resultBeans.add(resultBean);
                }while (cursor.moveToNext());
            }
            cursor.close();
            view.showResults(resultBeans);
            view.stopLoading();
        }
    }

    @Override
    public void refresh() {

    }

    @Override
    public void loadMore(long date) {

    }

    @Override
    public void startReading(int position) {
        Intent intent = new Intent(context, DetailActivity.class).putExtra("type", BeanType.TYPE_GUOKR)
                .putExtra("id", resultBeans.get(position).getId())
                .putExtra("title", resultBeans.get(position).getTitle())
                .putExtra("coverUrl", ((GuokrNews.ResultBean)resultBeans.get(position)).getHeadline_img());
        context.startActivity(intent);
    }

    @Override
    public void feelLucky() {
        if (resultBeans!=null && resultBeans.size()>0){
            startReading(new Random().nextInt(resultBeans.size()));
        }
    }

    private boolean queryIfIdExists(int id){
        Cursor cursor = database.query("Guokr", null, "guokr_id=?", new String[]{String.valueOf(id)}, null, null, null);
        while (cursor.moveToNext()){
            if (cursor.getInt(cursor.getColumnIndex("guokr_id")) == id){
                return true;
            }
        }

        return false;
    }
}
