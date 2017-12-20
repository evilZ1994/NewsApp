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
public class GuokrFragment extends Fragment implements GuokrContract.View{


    public GuokrFragment() {
        // Required empty public constructor
    }

    public static GuokrFragment newInstance(){
        return new GuokrFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_guokr, container, false);
    }

    @Override
    public void setPresenter(GuokrContract.Presenter presenter) {

    }

    @Override
    public void initViews(View view) {

    }
}
