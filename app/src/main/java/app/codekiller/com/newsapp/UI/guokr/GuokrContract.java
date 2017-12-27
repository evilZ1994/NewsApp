package app.codekiller.com.newsapp.UI.guokr;

import java.util.ArrayList;

import app.codekiller.com.newsapp.BasePresenter;
import app.codekiller.com.newsapp.BaseView;
import app.codekiller.com.newsapp.bean.BaseBean;

/**
 * Created by R2D2 on 2017/12/20.
 */

public interface GuokrContract {
    interface View extends BaseView<Presenter>{
        //显示或加载其他类型的错误
        void showError();
        //显示正在加载
        void showLoading();
        //显示停止正在加载
        void stopLoading();
        //成功获取到数据后在界面中显示
        void showResults(ArrayList<BaseBean> list);
    }

    interface Presenter extends BasePresenter {
        //请求数据
        void loadPosts(boolean clearing);
        //刷新数据
        void refresh();
        //加载更多文章
        void loadMore(long date);
        //显示详情
        void startReading(int position);
        //随便看看
        void feelLucky();
    }
}
