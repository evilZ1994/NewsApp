package app.codekiller.com.newsapp.homepage;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import app.codekiller.com.newsapp.R;
import app.codekiller.com.newsapp.bean.BeanType;

public class DetailActivity extends AppCompatActivity {
    private DetailFragment detailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (savedInstanceState != null){
            detailFragment = (DetailFragment) getSupportFragmentManager().getFragment(savedInstanceState, "DetailFragment");
        } else {
            detailFragment = DetailFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, detailFragment)
                    .commit();
        }

        Intent intent = getIntent();
        DetailPresenter presenter = new DetailPresenter(this, detailFragment);
        presenter.setType((BeanType) intent.getSerializableExtra("type"));
        presenter.setId(intent.getIntExtra("id", 0));
        presenter.setTitle(intent.getStringExtra("title"));
        presenter.setCoverUrl(intent.getStringExtra("coverUrl"));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (detailFragment.isAdded()){
            getSupportFragmentManager().putFragment(outState, "DetailFragment", detailFragment);
        }
    }
}
