package app.codekiller.com.newsapp.UI.detail;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.Html;
import android.webkit.WebView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.ArrayList;

import app.codekiller.com.newsapp.R;
import app.codekiller.com.newsapp.bean.BeanType;
import app.codekiller.com.newsapp.bean.DoubanStory;
import app.codekiller.com.newsapp.Model.StringModelImpl;
import app.codekiller.com.newsapp.bean.ZhihuDailyStory;
import app.codekiller.com.newsapp.db.DatabaseHelper;
import app.codekiller.com.newsapp.interfaze.OnStringListener;
import app.codekiller.com.newsapp.util.Api;
import app.codekiller.com.newsapp.util.NetworkState;

import static android.content.Context.CLIPBOARD_SERVICE;

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
    private String guokrStory;
    private DoubanStory doubanStory;
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
        Intent intent = new Intent(Intent.ACTION_VIEW);
        switch (type) {
            case TYPE_ZHIHU:
                intent.setData(Uri.parse(zhihuDailyStory.getShare_url()));
                break;
            case TYPE_GUOKR:
                intent.setData(Uri.parse(Api.GUOKR_ARTICLE_LINK_V1 + id));
                break;
            case TYPE_DOUBAN:
                intent.setData(Uri.parse(doubanStory.getShort_url()));
        }

        context.startActivity(intent);
    }

    @Override
    public void shareAsText() {
        Intent intent = new Intent().setAction(Intent.ACTION_SEND).setType("text/plain");
        String shareText = title +" ";

        switch (type) {
            case TYPE_ZHIHU:
                shareText += zhihuDailyStory.getShare_url();
                break;
            case TYPE_GUOKR:
                shareText += Api.GUOKR_ARTICLE_LINK_V1 + id;
                break;
            case TYPE_DOUBAN:
                shareText += doubanStory.getShort_url();
        }
        shareText = shareText + "\t\t\t" + context.getString(R.string.share_extra);

        intent.putExtra(Intent.EXTRA_TEXT,shareText);
        context.startActivity(Intent.createChooser(intent,context.getString(R.string.share_to)));
    }

    @Override
    public void openUrl(WebView webView, String url) {

    }

    @Override
    public void copyText() {
        ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = null;
        switch (type){
            case TYPE_ZHIHU:
                clipData = ClipData.newPlainText("text", Html.fromHtml(title + "\n" + zhihuDailyStory.getBody().toString()));
                break;
            case TYPE_GUOKR:
                clipData = ClipData.newPlainText("text", Html.fromHtml(guokrStory).toString());
                break;
            case TYPE_DOUBAN:
                clipData = ClipData.newPlainText("text", Html.fromHtml(title + "\n" + doubanStory.getContent()).toString());
        }
        manager.setPrimaryClip(clipData);
        view.showTextCopied();
    }

    @Override
    public void copyLink() {
        ClipboardManager manager = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        ClipData clipData = null;
        switch (type) {
            case TYPE_ZHIHU:
                clipData = ClipData.newPlainText("text", Html.fromHtml(zhihuDailyStory.getShare_url()).toString());
                break;
            case TYPE_GUOKR:
                clipData = ClipData.newPlainText("text", Html.fromHtml(Api.GUOKR_ARTICLE_LINK_V1 + id).toString());
                break;
            case TYPE_DOUBAN:
                clipData = ClipData.newPlainText("text", Html.fromHtml(doubanStory.getOriginal_url()).toString());
        }
        manager.setPrimaryClip(clipData);
        view.showTextCopied();
    }

    @Override
    public void addToOrDeleteFromBookmarks() {
        String tmpTable = "";
        String tmpId = "";
        switch (type) {
            case TYPE_ZHIHU:
                tmpTable = "Zhihu";
                tmpId = "zhihu_id";
                break;
            case TYPE_GUOKR:
                tmpTable = "Guokr";
                tmpId = "guokr_id";
                break;
            case TYPE_DOUBAN:
                tmpTable = "Douban";
                tmpId = "douban_id";
                break;
            default:
                break;
        }

        if (queryIfIsBookmarked()) {
            // delete
            // update Zhihu set favorite = 0 where zhihu_id = id
            ContentValues values = new ContentValues();
            values.put("favorite", 0);
            helper.getWritableDatabase().update(tmpTable, values, tmpId + " = ?", new String[]{String.valueOf(id)});
            values.clear();

            view.showDeletedFromBookmarks();
        } else {
            // add
            // update Zhihu set favorite = 1 where zhihu_id = id

            ContentValues values = new ContentValues();
            values.put("favorite", 1);
            helper.getWritableDatabase().update(tmpTable, values, tmpId + " = ?", new String[]{String.valueOf(id)});
            values.clear();

            view.showAddedToBookmarks();
        }
    }

    @Override
    public boolean queryIfIsBookmarked() {
        if (id == 0 || type == null) {
            view.showLoadingError();
            return false;
        }

        String tempTable = "";
        String tempId = "";

        switch (type) {
            case TYPE_ZHIHU:
                tempTable = "Zhihu";
                tempId = "zhihu_id";
                break;
            case TYPE_GUOKR:
                tempTable = "Guokr";
                tempId = "guokr_id";
                break;
            case TYPE_DOUBAN:
                tempTable = "Douban";
                tempId = "douban_id";
                break;
            default:
                break;
        }

        String sql = "select * from " + tempTable + " where " + tempId + " = ?";
        Cursor cursor = helper.getReadableDatabase()
                .rawQuery(sql, new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            do {
                int isBookmarked = cursor.getInt(cursor.getColumnIndex("favorite"));
                if (isBookmarked == 1) {
                    return true;
                }
            } while (cursor.moveToNext());
        }

        cursor.close();

        return false;
    }

    @Override
    public void requestData() {
        view.showLoading();
        view.showCover(coverUrl);
        view.setTitle(title);

        switch (type){
            case TYPE_ZHIHU:
                if (NetworkState.networkConnected(context)){
                    model.load(Api.ZHIHU_NEWS + id, new OnStringListener() {
                        @Override
                        public void onSuccess(String result) {
                            zhihuDailyStory = gson.fromJson(result, ZhihuDailyStory.class);
                            if (zhihuDailyStory.getBody() == null){
                                view.showResultWithoutBody(zhihuDailyStory.getShare_url());
                            } else {
                                view.showResult(convertZhihuContent(zhihuDailyStory.getBody()));
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
                    cursor.moveToFirst();
                    String content = cursor.getString(cursor.getColumnIndex("zhihu_content"));
                    zhihuDailyStory = gson.fromJson(content, ZhihuDailyStory.class);
                    view.showResult(convertZhihuContent(zhihuDailyStory.getBody()));
                    cursor.close();
                }
                break;
            case TYPE_GUOKR:
                if (NetworkState.networkConnected(context)){
                    model.load(Api.GUOKR_ARTICLE_LINK_V1 + id, new OnStringListener() {
                        @Override
                        public void onSuccess(String result) {
                            convertGuokrContent(result);
                            view.showResult(guokrStory);
                            view.stopLoading();
                        }

                        @Override
                        public void onError(VolleyError error) {
                            view.stopLoading();
                        }
                    });
                }else {
                    Cursor cursor = database.query("Guokr", null, "guokr_id=?", new String[]{String.valueOf(id)}, null, null, null);
                    cursor.moveToFirst();
                    String content = cursor.getString(cursor.getColumnIndex("guokr_content"));
                    convertGuokrContent(content);
                    view.showResult(content);
                    cursor.close();
                }
                break;
            case TYPE_DOUBAN:
                if (NetworkState.networkConnected(context)){
                    model.load(Api.DOUBAN_ARTICLE_DETAIL + id, new OnStringListener() {
                        @Override
                        public void onSuccess(String result) {
                            doubanStory = gson.fromJson(result, DoubanStory.class);
                            String content = convertDoubanContent();
                            view.showResult(content);
                            view.stopLoading();
                        }

                        @Override
                        public void onError(VolleyError error) {
                            view.stopLoading();
                        }
                    });
                }else {
                    Cursor cursor = database.query("Douban", null, "douban_id=?", new String[]{String.valueOf(id)}, null, null, null);
                    cursor.moveToFirst();
                    String content = cursor.getString(cursor.getColumnIndex("douban_content"));
                    doubanStory = gson.fromJson(content, DoubanStory.class);
                    view.showResult(convertDoubanContent());
                    cursor.close();
                }
                break;
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
        if ((context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
                == Configuration.UI_MODE_NIGHT_YES){
            theme = "<body className=\"\" onload=\"onLoaded()\" class=\"night\">";
        }
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

    private void convertGuokrContent(String content) {
        // 简单粗暴的去掉下载的div部分
        this.guokrStory = content.replace("<div class=\"down\" id=\"down-footer\">\n" +
                "        <img src=\"http://static.guokr.com/apps/handpick/images/c324536d.jingxuan-logo.png\" class=\"jingxuan-img\">\n" +
                "        <p class=\"jingxuan-txt\">\n" +
                "            <span class=\"jingxuan-title\">果壳精选</span>\n" +
                "            <span class=\"jingxuan-label\">早晚给你好看</span>\n" +
                "        </p>\n" +
                "        <a href=\"\" class=\"app-down\" id=\"app-down-footer\">下载</a>\n" +
                "    </div>\n" +
                "\n" +
                "    <div class=\"down-pc\" id=\"down-pc\">\n" +
                "        <img src=\"http://static.guokr.com/apps/handpick/images/c324536d.jingxuan-logo.png\" class=\"jingxuan-img\">\n" +
                "        <p class=\"jingxuan-txt\">\n" +
                "            <span class=\"jingxuan-title\">果壳精选</span>\n" +
                "            <span class=\"jingxuan-label\">早晚给你好看</span>\n" +
                "        </p>\n" +
                "        <a href=\"http://www.guokr.com/mobile/\" class=\"app-down\">下载</a>\n" +
                "    </div>", "");

        // 替换css文件为本地文件
        guokrStory = guokrStory.replace("<link rel=\"stylesheet\" href=\"http://static.guokr.com/apps/handpick/styles/d48b771f.article.css\" />",
                "<link rel=\"stylesheet\" href=\"file:///android_asset/guokr.article.css\" />");

        // 替换js文件为本地文件
        guokrStory = guokrStory.replace("<script src=\"http://static.guokr.com/apps/handpick/scripts/9c661fc7.base.js\"></script>",
                "<script src=\"file:///android_asset/guokr.base.js\"></script>");
        if ((context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
                == Configuration.UI_MODE_NIGHT_YES){
            guokrStory = guokrStory.replace("<div class=\"article\" id=\"contentMain\">",
                    "<div class=\"article \" id=\"contentMain\" style=\"background-color:#212b30; color:#878787\">");
        }
    }

    private String convertDoubanContent() {

        if (doubanStory.getContent() == null) {
            return null;
        }
        String css;
        if ((context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
                == Configuration.UI_MODE_NIGHT_YES) {
            css = "<link rel=\"stylesheet\" href=\"file:///android_asset/douban_dark.css\" type=\"text/css\">";
        } else {
            css = "<link rel=\"stylesheet\" href=\"file:///android_asset/douban_light.css\" type=\"text/css\">";
        }
        String content = doubanStory.getContent();
        ArrayList<DoubanStory.PhotosBean> imageList = doubanStory.getPhotos();
        for (int i = 0; i < imageList.size(); i++) {
            String old = "<img id=\"" + imageList.get(i).getTag_name() + "\" />";
            String newStr = "<img id=\"" + imageList.get(i).getTag_name() + "\" "
                    + "src=\"" + imageList.get(i).getMedium().getUrl() + "\"/>";
            content = content.replace(old, newStr);
        }
        StringBuilder builder = new StringBuilder();
        builder.append("<!DOCTYPE html>\n");
        builder.append("<html lang=\"ZH-CN\" xmlns=\"http://www.w3.org/1999/xhtml\">\n");
        builder.append("<head>\n<meta charset=\"utf-8\" />\n");
        builder.append(css);
        builder.append("\n</head>\n<body>\n");
        builder.append("<div class=\"container bs-docs-container\">\n");
        builder.append("<div class=\"post-container\">\n");
        builder.append(content);
        builder.append("</div>\n</div>\n</body>\n</html>");

        return builder.toString();
    }
}
