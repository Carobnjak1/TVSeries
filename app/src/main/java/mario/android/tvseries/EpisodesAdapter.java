package mario.android.tvseries;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import mario.android.tvseries.model.episodes.Episodes;


public class EpisodesAdapter extends RecyclerView.Adapter<EpisodesAdapter.EpisodesViewHolder> {

    private List<Episodes> mEpisodesList;
    private CheckedCallback mCheckedCallback;

    public EpisodesAdapter() {
        this.mEpisodesList = Collections.emptyList();
    }


    public void setEpisodesList(List<Episodes> episodesList) {
        mEpisodesList = episodesList;
    }


    public void setCheckedCallback(CheckedCallback checkedCallback) {
        mCheckedCallback = checkedCallback;
    }

    public void setEpisodeWatched(int position, boolean watched){
        mEpisodesList.get(position).setWatched(watched);
    }

    @Override
    public EpisodesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.episodes_row, parent, false);
        final EpisodesViewHolder viewHolder = new EpisodesViewHolder(itemView);


        viewHolder.watchedCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCheckedCallback != null){
                    final int position = viewHolder.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mCheckedCallback.onCheckBoxClick(viewHolder.mEpisodes, position);
                    }

                }
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EpisodesViewHolder holder, int position) {
        Episodes mEpisodes = mEpisodesList.get(position);
        Context context = holder.titleTextView.getContext();
        holder.mEpisodes = mEpisodes;

        if (mEpisodes.getImage() != null){
            Picasso.with(context).load(mEpisodes.getImage().getMedium())
                    .placeholder(R.drawable.placeholder)
                    .fit()
                    .into(holder.episodeImageView);
        }else {
            holder.episodeImageView.setImageResource(R.drawable.placeholder);
        }

        holder.numberTextView.setText("S"+mEpisodes.getSeason()+ " "+"E"+mEpisodes.getNumber());
        holder.titleTextView.setText(mEpisodes.getName());
        holder.airedTextView.setText(mEpisodes.getAirdate());

        if (mEpisodes.isWatched()){
            holder.watchedCheckBox.setChecked(true);
        }else {
            holder.watchedCheckBox.setChecked(false);
        }

    }

    @Override
    public int getItemCount() {
        return mEpisodesList.size();
    }

    public static class EpisodesViewHolder extends RecyclerView.ViewHolder {

        public View contentLayout;
        public ImageView episodeImageView;
        public TextView titleTextView;
        public TextView numberTextView;
        public TextView airedTextView;
        public CheckBox watchedCheckBox;

        public Episodes mEpisodes;

        public EpisodesViewHolder(View itemView) {
            super(itemView);
            contentLayout = itemView.findViewById(R.id.episode_layout_content);
            episodeImageView = (ImageView) itemView.findViewById(R.id.episode_image);
            titleTextView = (TextView) itemView.findViewById(R.id.text_episode_title);
            numberTextView = (TextView) itemView.findViewById(R.id.text_episode_number);
            airedTextView = (TextView) itemView.findViewById(R.id.text_episode_aired);
            watchedCheckBox = (CheckBox) itemView.findViewById(R.id.episode_check);
        }

    }

    public interface CheckedCallback{
        void onCheckBoxClick(Episodes episode, int position);
    }
}
