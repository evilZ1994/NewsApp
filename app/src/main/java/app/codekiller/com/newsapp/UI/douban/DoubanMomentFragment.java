package app.codekiller.com.newsapp.UI.douban;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
import app.codekiller.com.newsapp.adapter.NewsRecyclerAdapter;
import app.codekiller.com.newsapp.bean.BaseBean;
import app.codekiller.com.newsapp.interfaze.OnRecyclerViewItemClickListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoubanMomentFragment extends Fragment implements DoubanMomentContract.View{
    private DoubanMomentContract.Presenter presenter;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private NewsRecyclerAdapter adapter;

    private int mYear = Calendar.getInstance().get(Calendar.YEAR);
    private int mMonth = Calendar.getInstance().get(Calendar.MONTH);
    private int mDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

    public DoubanMomentFragment() {
        // Required empty public constructor
    }

    public static DoubanMomentFragment newInstance(){
        return new DoubanMomentFragment();
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
    public void setPresenter(DoubanMomentContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void initViews(View view) {
        refreshLayout = view.findViewById(R.id.refresh_layout);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition();
                int totalCount = layoutManager.getItemCount();
                if (lastVisibleItem == (totalCount-1)){
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(mYear, mMonth, --mDay);
                    presenter.loadMore(calendar.getTimeInMillis());
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

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
    public void showLoadingError() {

    }

    @Override
    public void showResults(ArrayList<BaseBean> list) {
        if (adapter == null){
            adapter = new NewsRecyclerAdapter(getContext(), list, NewsRecyclerAdapter.DOUBAN);
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

    public void showPickDialog(){
        Calendar now = Calendar.getInstance();
        now.set(mYear, mMonth, mDay);
        DatePickerDialog dialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                Calendar temp = Calendar.getInstance();
                temp.clear();
                temp.set(year, monthOfYear, dayOfMonth);
                mYear = year;
                mMonth = monthOfYear;
                mDay = dayOfMonth;
                presenter.loadPosts(temp.getTimeInMillis(), true);
            }
        }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));

        dialog.setMaxDate(Calendar.getInstance());
        Calendar minDate = Calendar.getInstance();
        minDate.set(2014, 5, 12);
        dialog.setMinDate(minDate);
        // set the dialog not vibrate when date change, default value is true
        dialog.vibrate(false);
        dialog.setAccentColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));

        dialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
    }
}
