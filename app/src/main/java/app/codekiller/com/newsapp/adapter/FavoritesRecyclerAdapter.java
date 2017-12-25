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
import app.codekiller.com.newsapp.bean.Douban;
import app.codekiller.com.newsapp.bean.GuokrNews;
import app.codekiller.com.newsapp.bean.ZhihuDailyNews;
import app.codekiller.com.newsapp.interfaze.OnRecyclerViewItemClickListener;

/**
 * Created by R2D2 on 2017/12/25.
 */

public class FavoritesRecyclerAdapter extends RecyclerView.Adapter{

    private Context context;
    private LayoutInflater layoutInflater;
    private List<ZhihuDailyNews.Question> zhihuList;
    private List<GuokrNews.ResultBean> guokrList;
    private List<Douban.PostsBean> doubanList;
    private List<Integer> types;

    private OnRecyclerViewItemClickListener listener;

    public static final int TYPE_ZHIHU_NORMAL = 0;
    public static final int TYPE_ZHIHU_HEADER = 1;
    public static final int TYPE_GUOKR_NORMAL = 2;
    public static final int TYPE_GUOKR_HEADER = 3;
    public static final int TYPE_DOUBAN_NORMAL = 4;
    public static final int TYPE_DOUBAN_HEADER = 5;

    public FavoritesRecyclerAdapter(Context context, ArrayList<ZhihuDailyNews.Question> zhihuList,
                                    ArrayList<GuokrNews.ResultBean> guokrList,
                                    ArrayList<Douban.PostsBean> doubanList,
                                    ArrayList<Integer> types){
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.zhihuList = zhihuList;
        this.guokrList = guokrList;
        this.doubanList = doubanList;
        this.types = types;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ZHIHU_NORMAL || viewType == TYPE_GUOKR_NORMAL || viewType == TYPE_DOUBAN_NORMAL){
            return new NormalViewHolder(layoutInflater.inflate(R.layout.home_list_item_layout, parent, false), this.listener);
        }
        return new TypeViewHolder(layoutInflater.inflate(R.layout.favorite_header, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (types.get(position)){
            case TYPE_ZHIHU_HEADER:
                ((TypeViewHolder)holder).textView.setText(context.getResources().getString(R.string.zhihu_daily));
                break;
            case TYPE_ZHIHU_NORMAL:
                ZhihuDailyNews.Question question = zhihuList.get(position - 1);
                Glide.with(context)
                        .load(question.getImages().get(0))
                        .placeholder(R.drawable.placeholder)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .error(R.drawable.placeholder)
                        .centerCrop()
                        .into(((NormalViewHolder)holder).imageView);
                ((NormalViewHolder)holder).textView.setText(question.getTitle());
                break;
            case TYPE_GUOKR_HEADER:
                ((TypeViewHolder)holder).textView.setText(context.getResources().getString(R.string.guokr_handpick));
                break;
            case TYPE_GUOKR_NORMAL:
                GuokrNews.ResultBean resultBean = guokrList.get(position- zhihuList.size() - 2);
                Glide.with(context)
                        .load(resultBean.getHeadline_img_tb())
                        .placeholder(R.drawable.placeholder)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .error(R.drawable.placeholder)
                        .centerCrop()
                        .into(((NormalViewHolder)holder).imageView);
                ((NormalViewHolder)holder).textView.setText(resultBean.getTitle());
                break;
            case TYPE_DOUBAN_HEADER:
                ((TypeViewHolder)holder).textView.setText(context.getResources().getString(R.string.douban_moment));
                break;
            case TYPE_DOUBAN_NORMAL:
                Douban.PostsBean postsBean = doubanList.get(position - zhihuList.size() - guokrList.size() - 3);
                List<Douban.PostsBean.ThumbsBean> thumbsBean = postsBean.getThumbs();
                String image = "";
                if (thumbsBean.size() > 0){
                    image = thumbsBean.get(0).getMedium().getUrl();
                }
                Glide.with(context)
                        .load(image)
                        .placeholder(R.drawable.placeholder)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .error(R.drawable.placeholder)
                        .centerCrop()
                        .into(((NormalViewHolder)holder).imageView);
                ((NormalViewHolder)holder).textView.setText(postsBean.getTitle());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return types.size();
    }

    @Override
    public int getItemViewType(int position) {
        return types.get(position);
    }

    public void setOnRecyclerItemClickListener(OnRecyclerViewItemClickListener listener){
        this.listener = listener;
    }

    public class NormalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        TextView textView;
        OnRecyclerViewItemClickListener listener;

        public NormalViewHolder(View itemView, OnRecyclerViewItemClickListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view_cover);
            textView = itemView.findViewById(R.id.text_view_title);
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

    public class TypeViewHolder extends RecyclerView.ViewHolder{
        TextView textView;

        public TypeViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_view_type);
        }
    }
}
