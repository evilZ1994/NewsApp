package app.codekiller.com.newsapp.homepage;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;

import app.codekiller.com.newsapp.R;
import app.codekiller.com.newsapp.adapter.ZhihuDailyNewsAdapter;
import app.codekiller.com.newsapp.bean.ZhihuDailyNews;
import app.codekiller.com.newsapp.interfaze.OnRecyclerViewItemClickListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZhihuDailyFragment extends Fragment implements ZhihuDailyContract.View{

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private ZhihuDailyNewsAdapter adapter;
    private ZhihuDailyContract.Presenter presenter;

    public ZhihuDailyFragment() {

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
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        initViews(view);

        presenter.loadPosts(new Date().getTime(), true);

        return view;
    }

    @Override
    public void setPresenter(ZhihuDailyContract.Presenter presenter) {
        if (presenter != null){
            this.presenter = presenter;
        }
    }

    @Override
    public void initViews(View view) {
        refreshLayout = view.findViewById(R.id.refresh_layout);
        recyclerView = view.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadPosts(new Date().getTime(), true);
            }
        });
    }

    @Override
    public void showError() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void stopLoading() {
        if (refreshLayout.isRefreshing()){
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showResults(ArrayList<ZhihuDailyNews.Question> list) {
        if (adapter == null){
            adapter = new ZhihuDailyNewsAdapter(getContext(), list);
            adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    presenter.startReading(position);
                }
            });
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showPickDialog() {

    }
}
