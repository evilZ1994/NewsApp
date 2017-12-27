package app.codekiller.com.newsapp.homepage;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.preference.Preference;

import com.bumptech.glide.Glide;

/**
 * Created by R2D2 on 2017/12/27.
 */

public class SettingsPresenter implements SettingsContract.Presenter {
    private Context context;
    private SettingsContract.View view;

    public static final int CLEAR_GLIDE_CACHE_DONE = 1;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (message.what == CLEAR_GLIDE_CACHE_DONE){
                view.showCleanGlideCacheDone();
            }
            return true;
        }
    });

    public SettingsPresenter(Context context, SettingsContract.View view){
        this.context = context;
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void setAutoChangeTheme(Preference preference) {

    }

    @Override
    public void cleanGlideCache() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(context).clearDiskCache();
                Message msg = new Message();
                msg.what = CLEAR_GLIDE_CACHE_DONE;
                handler.sendMessage(msg);
            }
        }).start();
        Glide.get(context).clearMemory();
    }
}
