package com.example.iptvplayer;

import android.app.PictureInPictureParams;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Rational;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.StyledPlayerView;

public class PlayerActivity extends AppCompatActivity {
    
    private StyledPlayerView playerView;
    private ExoPlayer player;
    private ProgressBar progressBar;
    private TextView tvChannelName;
    private String channelName;
    private String channelUrl;
    private String channelLogo;
    private boolean isFullscreen = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        // 获取传递的数据
        Intent intent = getIntent();
        channelName = intent.getStringExtra("channel_name");
        channelUrl = intent.getStringExtra("channel_url");
        channelLogo = intent.getStringExtra("channel_logo");

        initViews();
        setupPlayer();
        hideSystemUI();
    }

    private void initViews() {
        playerView = findViewById(R.id.playerView);
        progressBar = findViewById(R.id.progressBar);
        tvChannelName = findViewById(R.id.tvChannelName);

        if (channelName != null) {
            tvChannelName.setText(channelName);
        }

        // 设置控制器显示时间
        playerView.setControllerShowTimeoutMs(3000);
        playerView.setControllerAutoShow(false);
    }

    private void setupPlayer() {
        // 创建轨道选择器
        DefaultTrackSelector trackSelector = new DefaultTrackSelector(this);
        
        // 创建播放器
        player = new ExoPlayer.Builder(this)
                .setTrackSelector(trackSelector)
                .build();
        
        // 设置播放器到视图
        playerView.setPlayer(player);
        
        // 创建媒体项
        MediaItem mediaItem = MediaItem.fromUri(Uri.parse(channelUrl));
        
        // 设置媒体项
        player.setMediaItem(mediaItem);
        
        // 准备播放器
        player.prepare();
        
        // 自动播放
        player.setPlayWhenReady(true);
        
        // 监听播放状态
        player.addListener(new Player.Listener() {
            @Override
            public void onIsLoadingChanged(boolean isLoading) {
                progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playbackState == Player.STATE_READY) {
                    progressBar.setVisibility(View.GONE);
                } else if (playbackState == Player.STATE_ENDED) {
                    // 播放结束后重新播放
                    player.seekTo(0);
                    player.setPlayWhenReady(true);
                }
            }

            @Override
            public void onPlayerError(PlaybackException error) {
                Log.e("PlayerActivity", "播放错误: " + error.getMessage());
                Toast.makeText(PlayerActivity.this, "播放失败: " + error.getMessage(), Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void hideSystemUI() {
        // 设置全屏模式
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        
        // 隐藏导航栏和状态栏
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(uiOptions);
    }

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // 支持画中画模式
            if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)) {
                if (player != null && player.isPlaying()) {
                    enterPictureInPictureMode();
                    return;
                }
            }
        }
        super.onBackPressed();
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && player != null && player.isPlaying()) {
            enterPictureInPictureMode();
        }
    }

    @Override
    public void onPictureInPictureModeChanged(
            boolean isInPictureInPictureMode,
            Configuration newConfig) {
        if (isInPictureInPictureMode) {
            // 进入画中画模式时隐藏控制器
            playerView.hideController();
        } else {
            // 退出画中画模式时显示控制器
            playerView.showController();
        }
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player != null && !isInPictureInPictureMode()) {
            player.setPlayWhenReady(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null && !isInPictureInPictureMode()) {
            player.setPlayWhenReady(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
            player = null;
        }
    }
}