package themoviedb.privalia.views;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import themoviedb.privalia.R;
import themoviedb.privalia.models.Movie;
import themoviedb.privalia.network.MdbStatics;
import themoviedb.privalia.utils.DateUtils;

public class MovieActivityAdapter extends RecyclerView.Adapter<MovieActivityAdapter.ItemViewHolder> {

    List<Movie> movieList;
    Context context;


    public MovieActivityAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_movie, parent, false);
        ItemViewHolder ivh = new ItemViewHolder(v);
        return ivh;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {

        //Text sizes
        holder.mTvName.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources()
                .getDisplayMetrics().widthPixels / 20);

        holder.mTvReleaseYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources()
                .getDisplayMetrics().widthPixels / 28);

        holder.mTvOverview.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources()
                .getDisplayMetrics().widthPixels / 24);



        if(null != movieList.get(position).getTitle()){
            holder.mTvName.setText(movieList.get(position).getTitle());
        }

        if(null != movieList.get(position).getReleaseDate()){
            holder.mTvReleaseYear.setText(DateUtils.getYearFromDate(movieList.get(position).getReleaseDate()));
        }

        if(null !=movieList.get(position).getOverview() ){
            holder.mTvOverview.setText(movieList.get(position).getOverview());
        }

        if(null !=  movieList.get(position).getPosterPath()){
            Glide.with(context)
                    .load(MdbStatics.IMAGE_URL + movieList.get(position).getPosterPath())
                    .thumbnail(0.5f)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.mAvLoading.show();
                            holder.mIvPoster.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.mAvLoading.hide();
                            holder.mIvPoster.setVisibility(View.VISIBLE);
                            return false;
                        }
                    }).into(holder.mIvPoster);
        }


    }

    @Override
    public int getItemCount() {
            return movieList.size();
    }

    public void refreshMovies(List<Movie> mList) {
        int curSize = this.getItemCount();
        movieList.addAll(mList);
        notifyItemRangeInserted(curSize, movieList.size() - 1);
        notifyDataSetChanged();

    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.loading)
        AVLoadingIndicatorView mAvLoading;

        @BindView(R.id.iv_poster)
        ImageView mIvPoster;

        @BindView(R.id.tv_name)
        TextView mTvName;

        @BindView(R.id.tv_release_year)
        TextView mTvReleaseYear;

        @BindView(R.id.tv_overview)
        TextView mTvOverview;

        ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }

}
