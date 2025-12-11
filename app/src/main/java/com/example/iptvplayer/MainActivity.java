package com.example.iptvplayer;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.iptvplayer.adapter.ChannelAdapter;
import com.example.iptvplayer.model.Channel;
import com.example.iptvplayer.utils.ChannelManager;
import com.google.android.material.tabs.TabLayout;
import java.util.List;

public class MainActivity extends AppCompatActivity implements 
        ChannelAdapter.OnChannelClickListener, 
        ChannelAdapter.OnFavoriteClickListener {

    private RecyclerView recyclerView;
    private ChannelAdapter channelAdapter;
    private ChannelManager channelManager;
    private TabLayout tabLayout;
    private SearchView searchView;
    private List<Channel> allChannels;
    private List<Channel> favoriteChannels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupRecyclerView();
        setupTabLayout();
        setupSearchView();
        loadChannels();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        tabLayout = findViewById(R.id.tabLayout);
        searchView = findViewById(R.id.searchView);
        channelManager = new ChannelManager(this);
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        channelAdapter = new ChannelAdapter(this, allChannels);
        channelAdapter.setOnChannelClickListener(this);
        channelAdapter.setOnFavoriteClickListener(this);
        recyclerView.setAdapter(channelAdapter);
    }

    private void setupTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText("全部频道"));
        tabLayout.addTab(tabLayout.newTab().setText("收藏频道"));
        
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    showAllChannels();
                } else {
                    showFavoriteChannels();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchChannels(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    // 当前在哪个标签页就显示哪个标签页的内容
                    if (tabLayout.getSelectedTabPosition() == 0) {
                        showAllChannels();
                    } else {
                        showFavoriteChannels();
                    }
                } else {
                    searchChannels(newText);
                }
                return true;
            }
        });
    }

    private void loadChannels() {
        allChannels = channelManager.getChannels();
        favoriteChannels = channelManager.getFavoriteChannels();
        showAllChannels();
    }

    private void showAllChannels() {
        if (allChannels != null) {
            channelAdapter.updateChannels(allChannels);
        }
    }

    private void showFavoriteChannels() {
        if (favoriteChannels != null) {
            channelAdapter.updateChannels(favoriteChannels);
        }
    }

    private void searchChannels(String query) {
        List<Channel> searchResults = channelManager.searchChannels(query);
        channelAdapter.updateChannels(searchResults);
    }

    @Override
    public void onChannelClick(Channel channel) {
        Intent intent = new Intent(this, PlayerActivity.class);
        intent.putExtra("channel_name", channel.getName());
        intent.putExtra("channel_url", channel.getUrl());
        intent.putExtra("channel_logo", channel.getLogo());
        startActivity(intent);
    }

    @Override
    public void onFavoriteClick(Channel channel) {
        channelManager.toggleFavorite(channel.getId());
        loadChannels(); // 重新加载数据
        Toast.makeText(this, 
            channel.isFavorite() ? "已添加到收藏" : "已取消收藏", 
            Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        
        if (id == R.id.action_refresh) {
            loadChannels();
            Toast.makeText(this, "频道列表已刷新", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_settings) {
            // 设置功能可以在这里实现
            Toast.makeText(this, "设置功能开发中", Toast.LENGTH_SHORT).show();
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
}