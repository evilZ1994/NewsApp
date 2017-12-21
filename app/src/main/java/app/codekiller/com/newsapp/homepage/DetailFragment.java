package app.codekiller.com.newsapp.homepage;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.codekiller.com.newsapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment implements DetailContract.View{


    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.detail_read_layout, container);


        return view;
    }

    @Override
    public void setPresenter(DetailContract.Presenter presenter) {

    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showLoadingError() {

    }

    @Override
    public void showSharingError() {

    }

    @Override
    public void showResult(String result) {

    }

    @Override
    public void showResultWithoutBody(String url) {

    }

    @Override
    public void showCover(String url) {

    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public void setImageMode(boolean showImage) {

    }

    @Override
    public void showBrowserNotFoundError() {

    }

    @Override
    public void showTextCopied() {

    }

    @Override
    public void showCopyTextError() {

    }

    @Override
    public void showAddedToBookmarks() {

    }

    @Override
    public void showDeletedFromBookmarks() {

    }
}
