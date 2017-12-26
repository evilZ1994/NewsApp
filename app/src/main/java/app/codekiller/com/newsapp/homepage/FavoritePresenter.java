package app.codekiller.com.newsapp.homepage;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import app.codekiller.com.newsapp.adapter.FavoritesRecyclerAdapter;
import app.codekiller.com.newsapp.bean.BeanType;
import app.codekiller.com.newsapp.bean.Douban;
import app.codekiller.com.newsapp.bean.GuokrNews;
import app.codekiller.com.newsapp.bean.ZhihuDailyNews;
import app.codekiller.com.newsapp.db.DatabaseHelper;

/**
 * Created by R2D2 on 2017/12/25.
 */

public class FavoritePresenter implements FavoriteConstract.Presenter {
    private Context context;
    private FavoriteConstract.View view;
    private Gson gson;
    private SQLiteDatabase database;
    private DatabaseHelper helper;

    private ArrayList<ZhihuDailyNews.Question> zhihuList;
    private ArrayList<GuokrNews.ResultBean> guokrList;
    private ArrayList<Douban.PostsBean> doubanList;
    private ArrayList<Integer> types;

    public FavoritePresenter(Context context, FavoriteConstract.View view){
        this.context = context;
        this.view = view;
        this.view.setPresenter(this);
        gson = new Gson();
        helper = DatabaseHelper.getInstance(context);
        database = helper.getWritableDatabase();

        zhihuList = new ArrayList<>();
        guokrList = new ArrayList<>();
        doubanList = new ArrayList<>();
        types = new ArrayList<>();
    }

    @Override
    public void loadData(boolean refresh) {
        if (refresh){
            zhihuList.clear();
            guokrList.clear();
            doubanList.clear();
            types.clear();
        }

        Cursor cursor = database.query("Zhihu", null, "favorite=?", new String[]{"1"}, null, null, null);
        if (cursor.getCount() > 0){
            types.add(FavoritesRecyclerAdapter.TYPE_ZHIHU_HEADER);
            if (cursor.moveToFirst()){
                do {
                    ZhihuDailyNews.Question question = gson.fromJson(cursor.getString(cursor.getColumnIndex("zhihu_news")), ZhihuDailyNews.Question.class);
                    zhihuList.add(question);
                    types.add(FavoritesRecyclerAdapter.TYPE_ZHIHU_NORMAL);
                } while (cursor.moveToNext());
            }
        }

        cursor = database.query("Guokr", null, "favorite=?", new String[]{"1"}, null, null, null);
        if (cursor.getCount() > 0){
            types.add(FavoritesRecyclerAdapter.TYPE_GUOKR_HEADER);
            if (cursor.moveToFirst()){
                do {
                    GuokrNews.ResultBean resultBean = gson.fromJson(cursor.getString(cursor.getColumnIndex("guokr_news")), GuokrNews.ResultBean.class);
                    guokrList.add(resultBean);
                    types.add(FavoritesRecyclerAdapter.TYPE_GUOKR_NORMAL);
                } while (cursor.moveToNext());
            }
        }

        cursor = database.query("Douban", null, "favorite=?", new String[]{"1"}, null, null, null);
        if (cursor.getCount() > 0){
            types.add(FavoritesRecyclerAdapter.TYPE_DOUBAN_HEADER);
            if (cursor.moveToFirst()){
                Douban.PostsBean postsBean = gson.fromJson(cursor.getString(cursor.getColumnIndex("douban_news")), Douban.PostsBean.class);
                doubanList.add(postsBean);
                types.add(FavoritesRecyclerAdapter.TYPE_DOUBAN_NORMAL);
            } while (cursor.moveToNext());
        }

        cursor.close();

        view.showResults(zhihuList, guokrList, doubanList, types);
        view.stopLoading();
    }

    @Override
    public void startReading(BeanType type, int position) {
        Intent intent = new Intent(context, DetailActivity.class);
        switch (type){
            case TYPE_ZHIHU:
                ZhihuDailyNews.Question question = zhihuList.get(position - 1);
                intent.putExtra("type", type);
                intent.putExtra("id", question.getId());
                intent.putExtra("title", question.getTitle());
                intent.putExtra("coverUrl", question.getImages().get(0));
                break;
            case TYPE_GUOKR:
                GuokrNews.ResultBean resultBean = guokrList.get(position - zhihuList.size() - 2);
                intent.putExtra("type", type);
                intent.putExtra("id", resultBean.getId());
                intent.putExtra("title", resultBean.getTitle());
                intent.putExtra("coverUrl", resultBean.getHeadline_img());
                break;
            case TYPE_DOUBAN:
                Douban.PostsBean postsBean = doubanList.get(position - zhihuList.size() - guokrList.size() - 3);
                intent.putExtra("type", type);
                intent.putExtra("id", postsBean.getId());
                intent.putExtra("title", postsBean.getTitle());
                if (postsBean.getThumbs().size() > 0){
                    intent.putExtra("coverUrl", postsBean.getThumbs().get(0).getLarge().getUrl());
                } else {
                    intent.putExtra("coverUrl", "");
                }
                break;
        }
        context.startActivity(intent);
    }

    @Override
    public void start() {

    }
}
