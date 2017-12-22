package app.codekiller.com.newsapp.util;

import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by R2D2 on 2017/12/22.
 */

public class MyWebViewClient extends WebViewClient {

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        imgReset(view);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        view.loadUrl(request.getUrl().toString());
        return true;
    }

    private void imgReset(WebView view) {
        //用js修改img的宽度，图片大小超过200像素则调整适应屏幕，否则不变（如头像等小图）
        view.loadUrl("javascript:(function(){"
                + "var objs = document.getElementsByTagName('img'); "
                + "for(var i=0;i<objs.length;i++)  " + "{"
                + "var img = objs[i];   "
                + "var width = img.width;"
                + "if (width > 200) {"
                + "    img.style.width = '100%';   "
                + "    img.style.height = 'auto';   "
                + "}"
                + "}" + "})()");
    }
}
