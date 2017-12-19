package app.codekiller.com.newsapp;

import android.view.View;

/**
 * Created by Lollipop on 2017/12/19.
 */

public interface BaseView<T> {
    //为view设置presenter
    void setPresenter(T presenter);

    //初始化界面控件
    void initViews(View view);
}
