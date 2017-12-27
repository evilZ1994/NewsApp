package app.codekiller.com.newsapp.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;

/**
 * Created by Lollipop on 2017/12/28.
 */

public class NightThemeReceiver extends BroadcastReceiver {
    public static final String AUTO_THEME_NIGHT_ACTION = "auto.theme.night.action";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(AUTO_THEME_NIGHT_ACTION)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            Log.i("NightThemeReceiver", "received");
        }
    }
}
