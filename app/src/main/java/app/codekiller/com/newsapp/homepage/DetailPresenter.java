package app.codekiller.com.newsapp.homepage;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.webkit.WebView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import app.codekiller.com.newsapp.bean.BeanType;
import app.codekiller.com.newsapp.bean.StringModelImpl;
import app.codekiller.com.newsapp.bean.ZhihuDailyStory;
import app.codekiller.com.newsapp.db.DatabaseHelper;
import app.codekiller.com.newsapp.interfaze.OnStringListener;
import app.codekiller.com.newsapp.util.Api;
import app.codekiller.com.newsapp.util.NetworkState;

/**
 * Created by Lollipop on 2017/12/21.
 */

public class DetailPresenter implements DetailContract.Presenter {

    private DetailContract.View view;
    private Context context;

    private BeanType type;
    private int id;
    private String title;
    private String coverUrl;

    private StringModelImpl model;
    private Gson gson = new Gson();
    private ZhihuDailyStory zhihuDailyStory;
    private SQLiteDatabase database;
    private DatabaseHelper helper;

    public DetailPresenter(Context context, DetailContract.View view){
        this.view = view;
        this.context = context;
        this.view.setPresenter(this);
        model = new StringModelImpl(context);
        helper = DatabaseHelper.getInstance(context);
        database = helper.getWritableDatabase();
    }

    public void setType(BeanType type) {
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    @Override
    public void start() {

    }

    @Override
    public void openInBrowser() {

    }

    @Override
    public void shareAsText() {

    }

    @Override
    public void openUrl(WebView webView, String url) {

    }

    @Override
    public void copyText() {

    }

    @Override
    public void copyLink() {

    }

    @Override
    public void addToOrDeleteFromBookmarks() {

    }

    @Override
    public boolean queryIfIsBookmarked() {
        return false;
    }

    @Override
    public void requestData() {
        view.showLoading();
        view.showCover(coverUrl);
        view.setTitle(title);

        if (NetworkState.networkConnected(context)){
            model.load(Api.ZHIHU_NEWS + id, new OnStringListener() {
                @Override
                public void onSuccess(String result) {
                    zhihuDailyStory = gson.fromJson(result, ZhihuDailyStory.class);
                    if (zhihuDailyStory.getBody() == null){
                        view.showResultWithoutBody(zhihuDailyStory.getShare_url());
                    } else {
                        view.showResult(zhihuDailyStory.getBody());
                    }

                    view.stopLoading();
                }

                @Override
                public void onError(VolleyError error) {
                    view.stopLoading();
                    view.showLoadingError();
                }
            });
        } else {
            Cursor cursor = database.query("Zhihu", null, "zhihu_id=?", new String[]{String.valueOf(id)}, null, null, null);
            String content = cursor.getString(cursor.getColumnIndex("zhihu_content"));
            zhihuDailyStory = gson.fromJson(content, ZhihuDailyStory.class);
            view.showResult(convertZhihuContent(zhihuDailyStory.getBody()));
        }
    }

    private String convertZhihuContent(String preResult){
        preResult = preResult.replace("<div class=\"img-place-holder\">", "");
        preResult = preResult.replace("<div class=\"headline\">", "");

        // 在api中，css的地址是以一个数组的形式给出，这里需要设置
        // api中还有js的部分，这里不再解析js
        // 不再选择加载网络css，而是加载本地assets文件夹中的css
        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/zhihu_daily.css\" type=\"text/css\">";
        String theme = "<body className=\"\" onload=\"onLoaded()\">";
        return new StringBuilder()
                .append("<!DOCTYPE html>\n")
                .append("<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\n")
                .append("<head>\n")
                .append("\t<meta charset=\"utf-8\" />")
                .append(css)
                .append("\n</head>\n")
                .append(theme)
                .append(preResult)
                .append("</body></html>").toString();
    }
}
