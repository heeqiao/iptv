package com.example.iptvplayer.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.iptvplayer.model.Channel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;

public class ChannelManager {
    private static final String PREFS_NAME = "iptv_prefs";
    private static final String CHANNELS_KEY = "channels";
    private static final String FAVORITES_KEY = "favorites";
    
    private Context context;
    private SharedPreferences prefs;
    private Gson gson;

    public ChannelManager(Context context) {
        this.context = context;
        this.prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        this.gson = new Gson();
    }

    public void saveChannels(List<Channel> channels) {
        String json = gson.toJson(channels);
        prefs.edit().putString(CHANNELS_KEY, json).apply();
    }

    public List<Channel> getChannels() {
        String json = prefs.getString(CHANNELS_KEY, "");
        if (json.isEmpty()) {
            return getDefaultChannels();
        }
        return gson.fromJson(json, new TypeToken<List<Channel>>(){}.getType());
    }

    public List<Channel> getFavoriteChannels() {
        List<Channel> allChannels = getChannels();
        List<Channel> favorites = new ArrayList<>();
        for (Channel channel : allChannels) {
            if (channel.isFavorite()) {
                favorites.add(channel);
            }
        }
        return favorites;
    }

    public void toggleFavorite(String channelId) {
        List<Channel> channels = getChannels();
        for (Channel channel : channels) {
            if (channel.getId().equals(channelId)) {
                channel.setFavorite(!channel.isFavorite());
                break;
            }
        }
        saveChannels(channels);
    }

    private List<Channel> getDefaultChannels() {
        List<Channel> channels = new ArrayList<>();
        
        // 默认频道列表（示例）
        channels.add(new Channel("1", "CCTV-1", "http://[你的直播源地址]", "https://example.com/logo/cctv1.png", "央视"));
        channels.add(new Channel("2", "CCTV-2", "http://[你的直播源地址]", "https://example.com/logo/cctv2.png", "央视"));
        channels.add(new Channel("3", "CCTV-3", "http://[你的直播源地址]", "https://example.com/logo/cctv3.png", "央视"));
        channels.add(new Channel("4", "CCTV-4", "http://[你的直播源地址]", "https://example.com/logo/cctv4.png", "央视"));
        channels.add(new Channel("5", "CCTV-5", "http://[你的直播源地址]", "https://example.com/logo/cctv5.png", "央视"));
        channels.add(new Channel("6", "CCTV-6", "http://[你的直播源地址]", "https://example.com/logo/cctv6.png", "央视"));
        channels.add(new Channel("7", "湖南卫视", "http://[你的直播源地址]", "https://example.com/logo/hunan.png", "卫视"));
        channels.add(new Channel("8", "浙江卫视", "http://[你的直播源地址]", "https://example.com/logo/zhejiang.png", "卫视"));
        channels.add(new Channel("9", "江苏卫视", "http://[你的直播源地址]", "https://example.com/logo/jiangsu.png", "卫视"));
        channels.add(new Channel("10", "东方卫视", "http://[你的直播源地址]", "https://example.com/logo/dongfang.png", "卫视"));
        
        saveChannels(channels);
        return channels;
    }

    public List<Channel> searchChannels(String query) {
        List<Channel> allChannels = getChannels();
        List<Channel> results = new ArrayList<>();
        
        for (Channel channel : allChannels) {
            if (channel.getName().toLowerCase().contains(query.toLowerCase()) ||
                channel.getGroup().toLowerCase().contains(query.toLowerCase())) {
                results.add(channel);
            }
        }
        
        return results;
    }
}