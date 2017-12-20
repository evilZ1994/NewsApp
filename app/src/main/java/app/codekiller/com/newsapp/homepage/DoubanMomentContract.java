package app.codekiller.com.newsapp.homepage;

import app.codekiller.com.newsapp.BasePresenter;
import app.codekiller.com.newsapp.BaseView;

/**
 * Created by R2D2 on 2017/12/20.
 */

public interface DoubanMomentContract {
    interface View extends BaseView<Presenter>{

    }

    interface Presenter extends BasePresenter{

        //随便看看
        void feelLucky();
    }
}
