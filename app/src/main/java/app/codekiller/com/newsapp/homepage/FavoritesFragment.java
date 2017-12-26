package app.codekiller.com.newsapp.homepage;


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
import app.codekiller.com.newsapp.adapter.FavoritesRecyclerAdapter;
import app.codekiller.com.newsapp.bean.BeanType;
import app.codekiller.com.newsapp.bean.Douban;
import app.codekiller.com.newsapp.bean.GuokrNews;
import app.codekiller.com.newsapp.bean.ZhihuDailyNews;
import app.codekiller.com.newsapp.interfaze.OnRecyclerViewItemClickListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment implements FavoriteConstract.View{

    private FavoriteConstract.Presenter presenter;
    private RecyclerView recyclerView;
    private FavoritesRecyclerAdapter adapter;
    private SwipeRefreshLayout refreshLayout;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    public static FavoritesFragment newInstance(){
        return new FavoritesFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        initViews(view);

        presenter = new FavoritePresenter(getContext(), this);

        presenter.loadData(true);

        return view;
    }

    @Override
    public void showResults(ArrayList<ZhihuDailyNews.Question> zhihuList, ArrayList<GuokrNews.ResultBean> guokrList, ArrayList<Douban.PostsBean> doubanList, ArrayList<Integer> types) {
        if (adapter == null){
            adapter = new FavoritesRecyclerAdapter(getContext(), zhihuList, guokrList, doubanList, types);
            adapter.setOnRecyclerItemClickListener(new OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    int type = recyclerView.findViewHolderForLayoutPosition(position).getItemViewType();
                    if (type == FavoritesRecyclerAdapter.TYPE_ZHIHU_NORMAL){
                        presenter.startReading(BeanType.TYPE_ZHIHU, position);
                    } else if (type == FavoritesRecyclerAdapter.TYPE_GUOKR_NORMAL) {
                        presenter.startReading(BeanType.TYPE_GUOKR, position);
                    } else if (type == FavoritesRecyclerAdapter.TYPE_DOUBAN_NORMAL){
                        presenter.startReading(BeanType.TYPE_DOUBAN, position);
                    }
                }
            });
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void stopLoading() {
        if (refreshLayout.isRefreshing()){
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void setPresenter(FavoriteConstract.Presenter presenter) {
        if (presenter != null) {
            this.presenter = presenter;
        }
    }

    @Override
    public void initViews(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        refreshLayout = view.findViewById(R.id.refresh_layout);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadData(true);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadData(true);
    }
}
