package app.codekiller.com.newsapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import app.codekiller.com.newsapp.app.AppManager;

/**
 * Created by R2D2 on 2017/12/28.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化时将Activity压入管理栈
        AppManager.getInstance().addActivity(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AppManager.getInstance().finishActivity(this);
    }
}