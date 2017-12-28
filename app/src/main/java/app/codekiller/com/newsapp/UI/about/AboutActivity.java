package app.codekiller.com.newsapp.UI.about;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import app.codekiller.com.newsapp.R;
import app.codekiller.com.newsapp.bean.InfoBean;
import app.codekiller.com.newsapp.interfaze.OnCodeViewClickListener;

public class AboutActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private AboutRecyclerAdapter adapter;
    private List<InfoBean> infoBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initInfo();

        initViews();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recycler_view);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new AboutRecyclerAdapter(this, infoBeans);
        adapter.setOnCodeViewClickListener(new OnCodeViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                DCodeDialog dCodeDialog = new DCodeDialog(AboutActivity.this, infoBeans.get(position).getCodeImg());
                dCodeDialog.show();
                dCodeDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void initInfo() {
        int[] names = new int[]{
                R.string.name_fly, R.string.name_lei, R.string.name_zhu
        };
        int[] images = new int[]{
                R.drawable.fly_img, R.drawable.lei_img, R.drawable.zhu_img
        };
        int[] codeImages = new int[]{
                R.drawable.code_fly, R.drawable.code_fly, R.drawable.code_fly
        };
        infoBeans = new ArrayList<>();

        for (int i=0; i<names.length; i++){
            InfoBean infoBean = new InfoBean();
            infoBean.setNameInfo(getResources().getString(names[i]));
            infoBean.setSchool(getResources().getString(R.string.our_school));
            infoBean.setImg(images[i]);
            infoBean.setCodeImg(codeImages[i]);
            infoBeans.add(infoBean);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
