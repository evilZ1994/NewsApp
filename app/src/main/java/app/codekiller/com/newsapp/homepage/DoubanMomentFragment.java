package app.codekiller.com.newsapp.homepage;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.codekiller.com.newsapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoubanMomentFragment extends Fragment implements DoubanMomentContract.View{


    public DoubanMomentFragment() {
        // Required empty public constructor
    }

    public static DoubanMomentFragment newInstance(){
        return new DoubanMomentFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_douban_moment, container, false);
    }

    @Override
    public void setPresenter(DoubanMomentContract.Presenter presenter) {

    }

    @Override
    public void initViews(View view) {

    }
}
