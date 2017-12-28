package app.codekiller.com.newsapp.app;

import android.app.Application;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatDelegate;

/**
 * Created by R2D2 on 2017/12/28.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        setNightTheme();
    }

    private void setNightTheme() {
        SharedPreferences sharedPreferences = getSharedPreferences("ThemeMode", MODE_PRIVATE);
        int nightMode = sharedPreferences.getInt("night_mode", AppCompatDelegate.MODE_NIGHT_NO);

        AppCompatDelegate.setDefaultNightMode(nightMode);
    }
}
