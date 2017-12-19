package app.codekiller.com.newsapp.homepage;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import app.codekiller.com.newsapp.R;
import app.codekiller.com.newsapp.bean.ZhihuDailyNews;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZhihuDailyFragment extends Fragment implements ZhihuDailyContract.View{


    public ZhihuDailyFragment() {
        // Required empty public constructor
    }

    public static ZhihuDailyFragment newInstance(){
        return new ZhihuDailyFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_zhihu_daily, container, false);
    }

    @Override
    public void setPresenter(ZhihuDailyContract.Presenter presenter) {

    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showResults(ArrayList<ZhihuDailyNews.Question> list) {

    }

    @Override
    public void showPickDialog() {

    }
}
