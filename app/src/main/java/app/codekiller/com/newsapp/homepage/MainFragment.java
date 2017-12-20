package app.codekiller.com.newsapp.homepage;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import app.codekiller.com.newsapp.R;
import app.codekiller.com.newsapp.adapter.MainPagerAdapter;

public class MainFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private MainPagerAdapter pagerAdapter;
    private Context context;

    private ZhihuDailyFragment zhihuDailyFragment;
    private GuokrFragment guokrFragment;
    private DoubanMomentFragment doubanMomentFragment;

    private ZhihuDailyPresenter zhihuDailyPresenter;
    private GuokrPresenter guokrPresenter;
    private DoubanMomentPresenter doubanMomentPresenter;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getActivity();
        //Fragment状态恢复
        if (savedInstanceState != null){
            FragmentManager manager = getChildFragmentManager();
            zhihuDailyFragment = (ZhihuDailyFragment) manager.getFragment(savedInstanceState, "ZhihuDailyFragment");
            guokrFragment = (GuokrFragment) manager.getFragment(savedInstanceState, "GuokrFragment");
            doubanMomentFragment = (DoubanMomentFragment) manager.getFragment(savedInstanceState, "DoubanMomentFragment");
        }else {
            zhihuDailyFragment = ZhihuDailyFragment.newInstance();
            guokrFragment = GuokrFragment.newInstance();
            doubanMomentFragment = DoubanMomentFragment.newInstance();
        }
        //创建presenter实例
        zhihuDailyPresenter = new ZhihuDailyPresenter(context, zhihuDailyFragment);
        guokrPresenter = new GuokrPresenter(context, guokrFragment);
        doubanMomentPresenter = new DoubanMomentPresenter(context, doubanMomentFragment);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_main, container, false);
        initViews(view);

        //显示菜单
        setHasOptionsMenu(true);

        //当TabLayout位置为果壳精选时，隐藏fab
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                FloatingActionButton fab = getActivity().findViewById(R.id.fab);
                if (tab.getPosition() == 1){
                    fab.hide();
                } else {
                    fab.show();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }

    private void initViews(View view) {
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(3);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(zhihuDailyFragment);
        fragments.add(guokrFragment);
        fragments.add(doubanMomentFragment);
        List<String> titles = new ArrayList<>();
        titles.add(getResources().getString(R.string.zhihu_daily));
        titles.add(getResources().getString(R.string.guokr_handpick));
        titles.add(getResources().getString(R.string.douban_moment));
        pagerAdapter = new MainPagerAdapter(getChildFragmentManager(), context, fragments, titles);

        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_feel_lucky){
            feelLucky();
        }
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        FragmentManager manager = getChildFragmentManager();
        manager.putFragment(outState, "ZhihuDailyFragment", zhihuDailyFragment);
        manager.putFragment(outState, "GuokrFragment", guokrFragment);
        manager.putFragment(outState, "DoubanFragment", doubanMomentFragment);
    }

    private void feelLucky() {
        Random random = new Random();
        int type = random.nextInt(3);
        switch (type){
            case 0:
                zhihuDailyPresenter.feelLucky();
                break;
            case 1:
                guokrPresenter.feelLucky();
                break;
            case 2:
                doubanMomentPresenter.feelLucky();
                break;
        }
    }
}
