package app.codekiller.com.newsapp.UI.settings;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.preference.Preference;

import com.bumptech.glide.Glide;

import app.codekiller.com.newsapp.db.DatabaseHelper;
import app.codekiller.com.newsapp.service.DayThemeReceiver;
import app.codekiller.com.newsapp.service.NightThemeReceiver;

/**
 * Created by R2D2 on 2017/12/27.
 */

public class SettingsPresenter implements SettingsContract.Presenter {
    private Context context;
    private SettingsContract.View view;
    private AutoThemeDialog dialog;
    private AlarmManager alarmManager;

    private PendingIntent dayPI;
    private PendingIntent nightPI;

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public static final int CLEAR_GLIDE_CACHE_DONE = 1;
    public static final int AUTO_THEME_DAY_FLAG = 0;
    public static final int AUTO_THEME_NIGHT_FLAG = 1;
    public static final int AUTO_THEME_DAY_REQUEST = 0;
    public static final int AUTO_THEME_NIGHT_REQUEST = 1;

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
        dialog = new AutoThemeDialog(context, this);
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        databaseHelper = DatabaseHelper.getInstance(context);
        database = databaseHelper.getWritableDatabase();
    }

    @Override
    public void start() {

    }

    @Override
    public void setAutoChangeTheme(Preference preference, boolean isOpen) {
        /*if (isOpen){
            dialog.show();
        } else {
            if (dayPI != null){
                alarmManager.cancel(dayPI);
            }
            if (nightPI != null){
                alarmManager.cancel(nightPI);
            }
        }*/
        SharedPreferences sharedPreferences = context.getSharedPreferences("ThemeMode", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (isOpen){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
            editor.putInt("night_mode", AppCompatDelegate.MODE_NIGHT_AUTO);
            editor.apply();
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            editor.putInt("night_mode", AppCompatDelegate.MODE_NIGHT_NO);
            editor.apply();
        }

        view.autoThemeSetted();
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

    @Override
    public void autoThemeTimePicked(long dayTime, long nightTime) {
        Intent intentDay = new Intent(DayThemeReceiver.AUTO_THEME_DAY_ACTION);
        dayPI = PendingIntent.getBroadcast(context, AUTO_THEME_DAY_REQUEST, intentDay, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent intentNight = new Intent(NightThemeReceiver.AUTO_THEME_NIGHT_ACTION);
        nightPI = PendingIntent.getBroadcast(context, AUTO_THEME_NIGHT_REQUEST, intentNight, PendingIntent.FLAG_UPDATE_CURRENT);
        long intervelMillis = 24*60*60*1000;
        alarmManager.setRepeating(AlarmManager.RTC, dayTime, intervelMillis, dayPI);
        alarmManager.setRepeating(AlarmManager.RTC, nightTime, intervelMillis, nightPI);
        dialog.dismiss();
    }

    @Override
    public void clearCache() {
        database.delete("Zhihu", "favorite=?", new String[]{"0"});
        database.delete("Guokr", "favorite=?", new String[]{"0"});
        database.delete("Douban", "favorite=?", new String[]{"0"});
        view.showCacheClearDone();
    }
}
