package app.codekiller.com.newsapp.homepage;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import app.codekiller.com.newsapp.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private MainFragment mainFragment;
    private FavoritesFragment favoritesFragment;

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        //恢复fragment的状态
        if (savedInstanceState != null){
            mainFragment = (MainFragment) getSupportFragmentManager().getFragment(savedInstanceState, "MainFragment");
            favoritesFragment = (FavoritesFragment) getSupportFragmentManager().getFragment(savedInstanceState, "FavoritesFragment");
        } else {
            mainFragment = MainFragment.newInstance();
            favoritesFragment = FavoritesFragment.newInstance();
        }

        if (!mainFragment.isAdded()){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.layout_fragment, mainFragment, "MainFragment")
                    .commit();
        }
        if (!favoritesFragment.isAdded()){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.layout_fragment, favoritesFragment, "FavoritesFragment")
                    .commit();
        }

        showMainFragment();
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);

        int id = item.getItemId();
        switch (id){
            case R.id.nav_home:
                showMainFragment();
                break;
            case R.id.nav_favorites:
                showFavoritesFragment();
                break;
        }
        return true;
    }

    private void showMainFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.show(mainFragment);
        fragmentTransaction.hide(favoritesFragment);
        fragmentTransaction.commit();

        toolbar.setTitle(getResources().getString(R.string.app_name));
    }

    private void showFavoritesFragment(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.show(favoritesFragment);
        fragmentTransaction.hide(mainFragment);
        fragmentTransaction.commit();

        toolbar.setTitle(getResources().getString(R.string.favorites));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mainFragment.isAdded()){
            getSupportFragmentManager().putFragment(outState, "MainFragment", mainFragment);
        }
        if (favoritesFragment.isAdded()){
            getSupportFragmentManager().putFragment(outState, "FavoritesFragment", favoritesFragment);
        }
    }
}
