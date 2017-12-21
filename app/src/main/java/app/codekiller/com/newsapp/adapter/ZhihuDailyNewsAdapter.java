package app.codekiller.com.newsapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import app.codekiller.com.newsapp.R;
import app.codekiller.com.newsapp.bean.ZhihuDailyNews;
import app.codekiller.com.newsapp.interfaze.OnRecyclerViewItemClickListener;

/**
 * Created by R2D2 on 2017/12/21.
 */

public class ZhihuDailyNewsAdapter extends RecyclerView.Adapter {

    private Context context;
    private LayoutInflater inflater;
    private List<ZhihuDailyNews.Question> list = new ArrayList<>();
    private OnRecyclerViewItemClickListener mListener;

    //文字+图片
    private static final int TYPE_NORMAL = 0;
    //footer加载更多
    private static final int TYPE_FOOTER = 1;

    public ZhihuDailyNewsAdapter(Context context, List<ZhihuDailyNews.Question> list){
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_NORMAL:
                return new NormalViewHolder(inflater.inflate(R.layout.home_list_item_layout, parent, false), mListener);
            case TYPE_FOOTER:
                return new FooterViewHolder(inflater.inflate(R.layout.list_footer, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NormalViewHolder){
            ZhihuDailyNews.Question item = list.get(position);
            if (item.getImages().get(0) == null){
                ((NormalViewHolder)holder).itemImg.setImageResource(R.drawable.placeholder);
            } else {
                Glide.with(context)
                        .load(item.getImages().get(0))
                        .asBitmap()
                        .placeholder(R.drawable.placeholder)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .error(R.drawable.placeholder)
                        .centerCrop()
                        .into(((NormalViewHolder)holder).itemImg);
            }
            ((NormalViewHolder)holder).newsTitle.setText(item.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        //加上footer
        return list.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == list.size()){
            return ZhihuDailyNewsAdapter.TYPE_FOOTER;
        }
        return ZhihuDailyNewsAdapter.TYPE_NORMAL;
    }

    public class NormalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView itemImg;
        private TextView newsTitle;
        private OnRecyclerViewItemClickListener listener;

        public NormalViewHolder(View itemView, OnRecyclerViewItemClickListener listener) {
            super(itemView);
            itemImg = itemView.findViewById(R.id.image_view_cover);
            newsTitle = itemView.findViewById(R.id.text_view_title);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener != null){
                listener.onItemClick(view, getLayoutPosition());
            }
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener){
        this.mListener = listener;
    }
}
