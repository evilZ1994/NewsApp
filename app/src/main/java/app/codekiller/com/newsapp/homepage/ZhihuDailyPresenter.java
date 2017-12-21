package app.codekiller.com.newsapp.homepage;

import android.content.Context;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.ArrayList;

import app.codekiller.com.newsapp.bean.StringModelImpl;
import app.codekiller.com.newsapp.bean.ZhihuDailyNews;
import app.codekiller.com.newsapp.interfaze.OnStringListener;
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

    private ArrayList<ZhihuDailyNews.Question> list = new ArrayList<>();

    public ZhihuDailyPresenter(Context context, ZhihuDailyContract.View view){
        this.context = context;
        this.view = view;
        this.view.setPresenter(this);
        model = new StringModelImpl(context);
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

                    ZhihuDailyNews post = gson.fromJson(result, ZhihuDailyNews.class);
                    if (clearing) {
                        list.clear();
                    }

                    for (ZhihuDailyNews.Question item : post.getStories()) {
                        list.add(item);
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

    }

    @Override
    public void feelLucky() {

    }
}
