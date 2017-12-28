package app.codekiller.com.newsapp.UI.about;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import app.codekiller.com.newsapp.R;
import app.codekiller.com.newsapp.bean.InfoBean;

public class AboutActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AboutRecyclerAdapter adapter;
    private List<InfoBean> infoBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        recyclerView = findViewById(R.id.recycler_view);

        initInfo();

        adapter = new AboutRecyclerAdapter(this, infoBeans);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void initInfo() {
        int[] names = new int[]{
                R.string.name_fly, R.string.name_lei, R.string.name_zhu
        };
        int[] images = new int[]{
                R.drawable.fly_img, R.drawable.lei_img, R.drawable.zhu_img
        };
        infoBeans = new ArrayList<>();
        for (int i=0; i<names.length; i++){
            InfoBean infoBean = new InfoBean();
            infoBean.setNameInfo(getResources().getString(names[i]));
            infoBean.setSchool(getResources().getString(R.string.our_school));
            infoBean.setImg(images[i]);
        }
    }
}
