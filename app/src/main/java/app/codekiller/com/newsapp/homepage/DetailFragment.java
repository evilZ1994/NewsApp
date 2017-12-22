package app.codekiller.com.newsapp.homepage;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import app.codekiller.com.newsapp.R;
import app.codekiller.com.newsapp.util.MyWebViewClient;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment implements DetailContract.View{

    private CoordinatorLayout coordinatorLayout;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;
    private ImageView cover;
    private SwipeRefreshLayout refreshLayout;
    private NestedScrollView nestedScrollView;
    private WebView webView;

    private DetailContract.Presenter presenter;

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance(){
        return new DetailFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.detail_read_layout, container, false);

       initViews(view);

       setHasOptionsMenu(true);

       presenter.requestData();

        return view;
    }

    @Override
    public void setPresenter(DetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void initViews(View view) {
        coordinatorLayout = view.findViewById(R.id.coordinator_layout);
        collapsingToolbarLayout = view.findViewById(R.id.toolbar_layout);
        toolbar = view.findViewById(R.id.toolbar);
        cover = view.findViewById(R.id.image_view);
        refreshLayout = view.findViewById(R.id.refresh_layout);
        nestedScrollView = view.findViewById(R.id.nested_scroll_view);
        webView = view.findViewById(R.id.web_view);

        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.requestData();
            }
        });

        DetailActivity activity = (DetailActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        //显示左上角返回按钮
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //能够和js交互
        webView.getSettings().setJavaScriptEnabled(true);
        //去掉缩放按钮,设置为不能缩放可以防止页面上出现放大和缩小的图标
        webView.getSettings().setBuiltInZoomControls(false);
        //缓存
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        //开启DOM storage API功能
        webView.getSettings().setDomStorageEnabled(true);
        //开启application Cache功能
        webView.getSettings().setAppCacheEnabled(false);

        webView.setWebViewClient(new MyWebViewClient());
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
    public void showLoadingError() {

    }

    @Override
    public void showSharingError() {

    }

    @Override
    public void showResult(String result) {
        webView.loadDataWithBaseURL("x-data://base", result, "text/html", "utf-8", null);
    }

    @Override
    public void showResultWithoutBody(String url) {

    }

    @Override
    public void showCover(String url) {
        Glide.with(getContext())
                .load(url)
                .asBitmap()
                .placeholder(R.drawable.placeholder)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(R.drawable.placeholder)
                .centerCrop()
                .into(cover);
    }

    @Override
    public void setTitle(String title) {
        collapsingToolbarLayout.setTitle(title);
    }

    @Override
    public void setImageMode(boolean showImage) {
        //WebView提供了是否显示图片的方法
        webView.getSettings().setBlockNetworkImage(showImage);
    }

    @Override
    public void showBrowserNotFoundError() {

    }

    @Override
    public void showTextCopied() {

    }

    @Override
    public void showCopyTextError() {

    }

    @Override
    public void showAddedToBookmarks() {

    }

    @Override
    public void showDeletedFromBookmarks() {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detail_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            getActivity().onBackPressed();
            Log.i("test", "back pressed");
        }

        return true;
    }
}
