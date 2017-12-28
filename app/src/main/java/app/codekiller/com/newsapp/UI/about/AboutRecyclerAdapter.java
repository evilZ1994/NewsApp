package app.codekiller.com.newsapp.UI.about;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import app.codekiller.com.newsapp.R;
import app.codekiller.com.newsapp.bean.InfoBean;
import app.codekiller.com.newsapp.interfaze.OnCodeViewClickListener;

/**
 * Created by R2D2 on 2017/12/28.
 */

public class AboutRecyclerAdapter extends RecyclerView.Adapter<AboutRecyclerAdapter.ViewHolder> {
    private Context context;
    private List<InfoBean> infoBeans;
    private OnCodeViewClickListener listener;

    public AboutRecyclerAdapter(Context context, List<InfoBean> infoBeans){
        this.context = context;
        this.infoBeans = infoBeans;
    }

    public void setOnCodeViewClickListener(OnCodeViewClickListener listener){
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.about_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        InfoBean infoBean = infoBeans.get(position);
        Glide.with(context)
                .load(infoBean.getImg())
                .asBitmap()
                .placeholder(R.drawable.placeholder)
                .centerCrop()
                .into(holder.headImgView);
        holder.nameInfoText.setText(infoBean.getNameInfo());
        holder.schoolText.setText(infoBean.getSchool());
        holder.codeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(view, holder.getLayoutPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return infoBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView headImgView;
        private TextView nameInfoText;
        private TextView schoolText;
        private ImageView codeView;

        public ViewHolder(View itemView) {
            super(itemView);
            headImgView = itemView.findViewById(R.id.head_img);
            nameInfoText = itemView.findViewById(R.id.info_text);
            schoolText = itemView.findViewById(R.id.school_text);
            codeView = itemView.findViewById(R.id.d_code);
        }
    }
}
