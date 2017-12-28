package app.codekiller.com.newsapp.UI.about;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import app.codekiller.com.newsapp.R;

/**
 * Created by Lollipop on 2017/12/28.
 */

public class DCodeDialog extends AlertDialog {
    private int codeImg;
    private Context context;
    private ImageView imageView;

    protected DCodeDialog(@NonNull Context context, int codeImg) {
        super(context);
        this.context = context;
        this.codeImg = codeImg;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.d_code_dialog, null);
        setContentView(view);
        initViews(view);
    }

    private void initViews(View view) {
        imageView = view.findViewById(R.id.code_img);
        Glide.with(context)
                .load(codeImg)
                .placeholder(R.drawable.placeholder)
                .into(imageView);
    }
}
