package app.codekiller.com.newsapp.homepage;

import android.view.View;

import java.util.ArrayList;

import app.codekiller.com.newsapp.BasePresenter;
import app.codekiller.com.newsapp.BaseView;
import app.codekiller.com.newsapp.bean.BeanType;
import app.codekiller.com.newsapp.bean.Douban;
import app.codekiller.com.newsapp.bean.GuokrNews;
import app.codekiller.com.newsapp.bean.ZhihuDailyNews;

/**
 * Created by R2D2 on 2017/12/25.
 */

public interface FavoriteConstract {
    interface View extends BaseView<Presenter>{
        void showResults(ArrayList<ZhihuDailyNews.Question> zhihuList,
                         ArrayList<GuokrNews.ResultBean> guokrList,
                         ArrayList<Douban.PostsBean> doubanList,
                         ArrayList<Integer> types);

        void stopLoading();
    }

    interface Presenter extends BasePresenter{
        void loadData(boolean refresh);

        void startReading(BeanType type, int position);
    }
}
