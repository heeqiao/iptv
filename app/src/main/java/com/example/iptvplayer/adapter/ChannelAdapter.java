package com.example.iptvplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.iptvplayer.R;
import com.example.iptvplayer.model.Channel;
import com.squareup.picasso.Picasso;
import java.util.List;

public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ChannelViewHolder> {
    
    private List<Channel> channels;
    private Context context;
    private OnChannelClickListener listener;
    private OnFavoriteClickListener favoriteListener;

    public interface OnChannelClickListener {
        void onChannelClick(Channel channel);
    }

    public interface OnFavoriteClickListener {
        void onFavoriteClick(Channel channel);
    }

    public ChannelAdapter(Context context, List<Channel> channels) {
        this.context = context;
        this.channels = channels;
    }

    public void setOnChannelClickListener(OnChannelClickListener listener) {
        this.listener = listener;
    }

    public void setOnFavoriteClickListener(OnFavoriteClickListener listener) {
        this.favoriteListener = listener;
    }

    public void updateChannels(List<Channel> newChannels) {
        this.channels = newChannels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChannelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_channel, parent, false);
        return new ChannelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChannelViewHolder holder, int position) {
        Channel channel = channels.get(position);
        
        holder.tvChannelName.setText(channel.getName());
        holder.tvChannelGroup.setText(channel.getGroup());
        
        // 加载频道图标
        if (channel.getLogo() != null && !channel.getLogo().isEmpty()) {
            Picasso.get()
                .load(channel.getLogo())
                .placeholder(R.drawable.ic_tv_placeholder)
                .error(R.drawable.ic_tv_placeholder)
                .into(holder.ivChannelLogo);
        } else {
            holder.ivChannelLogo.setImageResource(R.drawable.ic_tv_placeholder);
        }
        
        // 设置收藏状态
        if (channel.isFavorite()) {
            holder.ivFavorite.setImageResource(R.drawable.ic_favorite_filled);
        } else {
            holder.ivFavorite.setImageResource(R.drawable.ic_favorite_border);
        }
        
        // 设置点击事件
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onChannelClick(channel);
            }
        });
        
        holder.ivFavorite.setOnClickListener(v -> {
            if (favoriteListener != null) {
                favoriteListener.onFavoriteClick(channel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return channels.size();
    }

    public static class ChannelViewHolder extends RecyclerView.ViewHolder {
        ImageView ivChannelLogo;
        TextView tvChannelName;
        TextView tvChannelGroup;
        ImageView ivFavorite;

        public ChannelViewHolder(@NonNull View itemView) {
            super(itemView);
            ivChannelLogo = itemView.findViewById(R.id.ivChannelLogo);
            tvChannelName = itemView.findViewById(R.id.tvChannelName);
            tvChannelGroup = itemView.findViewById(R.id.tvChannelGroup);
            ivFavorite = itemView.findViewById(R.id.ivFavorite);
        }
    }
}