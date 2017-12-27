package app.codekiller.com.newsapp.UI.zhihu;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import app.codekiller.com.newsapp.R;
import app.codekiller.com.newsapp.UI.douban.DoubanMomentFragment;
import app.codekiller.com.newsapp.adapter.MainPagerAdapter;
import app.codekiller.com.newsapp.adapter.NewsRecyclerAdapter;
import app.codekiller.com.newsapp.bean.BaseBean;
import app.codekiller.com.newsapp.interfaze.OnRecyclerViewItemClickListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZhihuDailyFragment extends Fragment implements ZhihuDailyContract.View{

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private FloatingActionButton fab;
    private TabLayout tabLayout;

    private NewsRecyclerAdapter adapter;
    private ZhihuDailyContract.Presenter presenter;

    private int mYear = Calendar.getInstance().get(Calendar.YEAR);
    private int mMonth = Calendar.getInstance().get(Calendar.MONTH);
    private int mDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);


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
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // 获取最后一个完全显示的item position
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();

                    // 判断是否滚动到底部
                    if (lastVisibleItem == (totalItemCount - 1)) {
                        Calendar c = Calendar.getInstance();
                        c.set(mYear, mMonth, --mDay);
                        presenter.loadMore(c.getTimeInMillis());
                    }
                }

                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadPosts(new Date().getTime(), true);
            }
        });

        tabLayout = getActivity().findViewById(R.id.tab_layout);
        fab = getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tabLayout.getSelectedTabPosition() == 0) {
                    Calendar now = Calendar.getInstance();
                    now.set(mYear, mMonth, mDay);
                    DatePickerDialog dialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                            mYear = year;
                            mMonth = monthOfYear;
                            mDay = dayOfMonth;
                            Calendar temp = Calendar.getInstance();
                            temp.clear();
                            temp.set(year, monthOfYear, dayOfMonth);
                            presenter.loadPosts(temp.getTimeInMillis(), true);
                        }
                    }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));

                    dialog.setMaxDate(Calendar.getInstance());
                    Calendar minDate = Calendar.getInstance();
                    // 2013.5.20是知乎日报api首次上线
                    minDate.set(2013, 5, 20);
                    dialog.setMinDate(minDate);
                    dialog.vibrate(false);
                    dialog.setAccentColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));

                    dialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
                } else if (tabLayout.getSelectedTabPosition() == 2) {
                    ViewPager p = getActivity().findViewById(R.id.view_pager);
                    MainPagerAdapter ad = (MainPagerAdapter) p.getAdapter();
                    ((DoubanMomentFragment)ad.getFragments().get(2)).showPickDialog();
                }
            }
        });
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
            adapter = new NewsRecyclerAdapter(getContext(), list, NewsRecyclerAdapter.ZHIHU);
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
