package app.codekiller.com.newsapp.UI.douban;

import java.util.ArrayList;

import app.codekiller.com.newsapp.BasePresenter;
import app.codekiller.com.newsapp.BaseView;
import app.codekiller.com.newsapp.bean.BaseBean;

/**
 * Created by R2D2 on 2017/12/20.
 */

public interface DoubanMomentContract {
    interface View extends BaseView<Presenter> {

        void showLoading();

        void stopLoading();

        void showLoadingError();

        void showResults(ArrayList<BaseBean> list);

    }

    interface Presenter extends BasePresenter {

        void startReading(int position);

        void loadPosts(long date, boolean clearing);

        void refresh();

        void loadMore(long date);

        void feelLucky();

    }
}
