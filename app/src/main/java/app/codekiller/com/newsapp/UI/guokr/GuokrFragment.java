package app.codekiller.com.newsapp.UI.guokr;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import app.codekiller.com.newsapp.R;
import app.codekiller.com.newsapp.adapter.NewsRecyclerAdapter;
import app.codekiller.com.newsapp.bean.BaseBean;
import app.codekiller.com.newsapp.interfaze.OnRecyclerViewItemClickListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class GuokrFragment extends Fragment implements GuokrContract.View{
    private GuokrContract.Presenter presenter;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private NewsRecyclerAdapter adapter;

    public GuokrFragment() {
        // Required empty public constructor
    }

    public static GuokrFragment newInstance(){
        return new GuokrFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        initViews(view);

        presenter.loadPosts(true);

        return view;
    }

    @Override
    public void setPresenter(GuokrContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void initViews(View view) {
        refreshLayout = view.findViewById(R.id.refresh_layout);
        recyclerView = view.findViewById(R.id.recycler_view);

        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadPosts(true);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void showError() {

    }

    @Override
    public void showLoading() {
        if (!refreshLayout.isRefreshing()){
            refreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void stopLoading() {
        if (refreshLayout.isRefreshing()){
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showResults(ArrayList<BaseBean> list) {
        if (adapter == null){
            adapter = new NewsRecyclerAdapter(getContext(), list, NewsRecyclerAdapter.GUOKR);
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
}
