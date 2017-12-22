package app.codekiller.com.newsapp.homepage;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import app.codekiller.com.newsapp.bean.BeanType;
import app.codekiller.com.newsapp.bean.StringModelImpl;
import app.codekiller.com.newsapp.bean.ZhihuDailyNews;
import app.codekiller.com.newsapp.db.DatabaseHelper;
import app.codekiller.com.newsapp.interfaze.OnStringListener;
import app.codekiller.com.newsapp.service.CacheService;
import app.codekiller.com.newsapp.util.Api;
import app.codekiller.com.newsapp.util.DateFormatter;
import app.codekiller.com.newsapp.util.NetworkState;

/**
 * Created by Lollipop on 2017/12/19.
 */

public class ZhihuDailyPresenter implements ZhihuDailyContract.Presenter {
    private Context context;
    private ZhihuDailyContract.View view;
    private StringModelImpl model;

    private DateFormatter dateFormatter = new DateFormatter();
    private Gson gson = new Gson();

    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;

    private ArrayList<ZhihuDailyNews.Question> list = new ArrayList<>();

    public ZhihuDailyPresenter(Context context, ZhihuDailyContract.View view){
        this.context = context;
        this.view = view;
        this.view.setPresenter(this);
        model = new StringModelImpl(context);

        databaseHelper = DatabaseHelper.getInstance(context);
        database = databaseHelper.getWritableDatabase();
    }

    @Override
    public void start() {

    }

    @Override
    public void loadPosts(long date, final boolean clearing) {
        if (clearing){
            view.showLoading();
        }
        if (NetworkState.networkConnected(context)) {
            model.load(Api.ZHIHU_HISTORY + dateFormatter.ZhihuDailyDateFormat(date), new OnStringListener() {
                @Override
                public void onSuccess(String result) {
                    Log.i("result", result);

                    ZhihuDailyNews post = gson.fromJson(result, ZhihuDailyNews.class);
                    if (clearing) {
                        list.clear();
                    }

                    for (ZhihuDailyNews.Question item : post.getStories()) {
                        list.add(item);
                        if (!queryIfIdExist(item.getId())){
                            database.beginTransaction();

                            DateFormat format = new SimpleDateFormat("yyyyMMdd");
                            try {
                                Date date = format.parse(post.getDate());
                                ContentValues values = new ContentValues();
                                values.put("zhihu_id", item.getId());
                                values.put("zhihu_news", gson.toJson(item));
                                values.put("zhihu_content", "");
                                values.put("zhihu_time", date.getTime() / 1000);
                                database.insert("Zhihu", null, values);
                                values.clear();
                                database.setTransactionSuccessful();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            } finally {
                                database.endTransaction();
                            }

                            //发送广播，缓存主要内容
                            Intent intent = new Intent("LOCAL_BROADCAST");
                            intent.putExtra("type", CacheService.TYPE_ZHIHU);
                            intent.putExtra("id", item.getId());
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                        }
                    }
                    view.showResults(list);

                    view.stopLoading();
                }

                @Override
                public void onError(VolleyError error) {
                    view.stopLoading();
                    view.showError();
                }
            });
        }else {
            //读取数据库
            Cursor cursor = database.query("Zhihu", null, null, null, null, null, null);
            if (cursor.moveToFirst()){
                do {
                    ZhihuDailyNews.Question question = gson.fromJson(cursor.getString(cursor.getColumnIndex("zhihu_news")), ZhihuDailyNews.Question.class);
                    list.add(question);
                } while (cursor.moveToNext());
            }
            cursor.close();
            view.showResults(list);
            view.stopLoading();
        }
    }

    private boolean queryIfIdExist(int id){
        Cursor cursor = database.query("Zhihu", null, null, null, null, null, null);
        if (cursor.moveToFirst()){
            do {
                if (id == cursor.getInt(cursor.getColumnIndex("zhihu_id"))){
                    return true;
                }
            }while (cursor.moveToNext());
        }

        return false;
    }

    @Override
    public void refresh() {

    }

    @Override
    public void loadMore(long date) {

    }

    @Override
    public void startReading(int position) {
        context.startActivity(new Intent(context, DetailActivity.class)
                .putExtra("type", BeanType.TYPE_ZHIHU)
                .putExtra("id", list.get(position).getId())
                .putExtra("title", list.get(position).getTitle())
                .putExtra("coverUrl", list.get(position).getImages().get(0)));
    }

    @Override
    public void feelLucky() {

    }
}
