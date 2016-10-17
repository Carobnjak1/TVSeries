package mario.android.tvseries;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import mario.android.tvseries.model.show.TVShow;


public class ShowsAdapter extends RecyclerView.Adapter<ShowsAdapter.TVSeriesViewHolder> {

    private List<TVShow> mShowList;
    private Callback mCallback;
    private LikeCallback mLikeCallback;

    public ShowsAdapter() {
        this.mShowList = Collections.emptyList();
    }

    public void setShowList(List<TVShow> showList){
        this.mShowList = showList;
    }

    public void setShowLike(int position, boolean like){
        mShowList.get(position).getShow().setLiked(like);
    }

    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    public void setLikeCallback(LikeCallback likeCallback) {
        mLikeCallback = likeCallback;
    }

    @Override
    public TVSeriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_row, parent, false);
        final TVSeriesViewHolder viewHolder = new TVSeriesViewHolder(itemView);

        viewHolder.contentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCallback != null){
                    mCallback.onItemClick(viewHolder.mTVShow);
                }
            }
        });

        viewHolder.likeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mLikeCallback != null){
                    final int position = viewHolder.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mLikeCallback.onLikeClick(viewHolder.mTVShow, position);
                    }

                }
            }
        });

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(TVSeriesViewHolder holder, int position) {
        TVShow tvShow = mShowList.get(position);
        Context context = holder.titleTextView.getContext();
        holder.mTVShow = tvShow;

        if (tvShow.getShow().getImage() != null){
            Picasso.with(context).load(tvShow.getShow().getImage().getMedium())
                    .placeholder(R.drawable.placeholder)
                    .fit()
                    .into(holder.showImageView);
        }else {
            holder.showImageView.setImageResource(R.drawable.placeholder);
        }


        holder.titleTextView.setText(tvShow.getShow().getName());
        holder.statusTextView.setText(tvShow.getShow().getStatus());

        if (tvShow.getShow().isLiked()){
            int color = Color.parseColor("#FF0000"); //The color u want
            holder.likeImageView.setColorFilter(color);
        }else{
            holder.likeImageView.setColorFilter(null);
        }


    }

    @Override
    public int getItemCount() {
        return mShowList.size();
    }

    public static class TVSeriesViewHolder extends RecyclerView.ViewHolder {

        public View contentLayout;
        public ImageView showImageView;
        public TextView titleTextView;
        public TextView statusTextView;
        public ImageView likeImageView;


        public TVShow mTVShow;

        public TVSeriesViewHolder(View itemView) {
            super(itemView);
            contentLayout = itemView.findViewById(R.id.layout_content);
            showImageView = (ImageView) itemView.findViewById(R.id.show_image);
            titleTextView = (TextView) itemView.findViewById(R.id.text_series_title);
            statusTextView = (TextView) itemView.findViewById(R.id.text_series_status);
            likeImageView = (ImageView) itemView.findViewById(R.id.show_like);
        }
    }

    public interface Callback{
        void onItemClick(TVShow tvShow);
    }

    public interface LikeCallback{
        void onLikeClick(TVShow tvShow, int position);
    }
}
