# IPTV播放器 - 安卓应用

一个功能完整的安卓IPTV直播播放应用，支持多种视频流格式播放。

## 功能特性

- 📺 **直播播放** - 支持RTMP、HTTP、HLS等多种视频流格式
- 📋 **频道管理** - 频道列表展示、分组管理
- ⭐ **收藏功能** - 支持收藏喜欢的频道
- 🔍 **搜索功能** - 快速搜索频道名称和分组
- 🖼️ **界面美观** - Material Design风格界面
- 📱 **画中画** - 支持Android 7.0+的画中画模式
- 🌐 **横屏播放** - 自动横屏全屏播放

## 技术栈

- **开发语言**: Java
- **播放引擎**: ExoPlayer 2.19.1
- **网络请求**: OkHttp 4.12.0
- **JSON解析**: Gson 2.10.1
- **图片加载**: Picasso
- **UI框架**: Material Design Components
- **最低版本**: Android 5.0 (API 21)
- **目标版本**: Android 14 (API 34)

## 安装说明

### 环境要求
- Android Studio Arctic Fox 或更高版本
- Android SDK API 21+
- Java 8+

### 编译步骤
1. 克隆或下载项目到本地
2. 使用Android Studio打开项目
3. 等待Gradle同步完成
4. 连接Android设备或启动模拟器
5. 点击运行按钮编译安装

## 配置说明

### 添加频道源

编辑 `ChannelManager.java` 文件中的 `getDefaultChannels()` 方法，替换示例直播源地址：

```java
// 替换为实际的直播源地址
channels.add(new Channel("1", "CCTV-1", "https://your-live-stream-url.m3u8", "logo_url", "央视"));
```

支持的直播源格式：
- HLS: `https://example.com/stream.m3u8`
- RTMP: `rtmp://example.com/live/stream`
- HTTP: `http://example.com/stream.ts`
- 其他ExoPlayer支持的格式

### 权限配置

应用需要以下权限（已在AndroidManifest.xml中配置）：
- `INTERNET` - 网络访问权限
- `ACCESS_NETWORK_STATE` - 网络状态检查
- `android:usesCleartextTraffic="true"` - 支持HTTP明文传输

## 使用方法

1. **频道浏览** - 主界面显示所有可用频道
2. **切换标签** - 在"全部频道"和"收藏频道"间切换
3. **搜索频道** - 使用顶部搜索框快速查找
4. **开始播放** - 点击任意频道开始播放
5. **收藏管理** - 点击收藏按钮添加/移除收藏
6. **画中画** - 播放时按Home键进入画中画模式

## 项目结构

```
app/
├── src/main/
│   ├── java/com/example/iptvplayer/
│   │   ├── MainActivity.java              # 主界面
│   │   ├── PlayerActivity.java            # 播放界面
│   │   ├── model/
│   │   │   └── Channel.java               # 频道数据模型
│   │   ├── adapter/
│   │   │   └── ChannelAdapter.java        # 频道列表适配器
│   │   └── utils/
│   │       └── ChannelManager.java        # 频道管理工具
│   ├── res/
│   │   ├── layout/                        # 布局文件
│   │   ├── values/                        # 资源文件
│   │   ├── drawable/                      # 图标资源
│   │   └── menu/                          # 菜单资源
│   └── AndroidManifest.xml                # 应用清单
├── build.gradle                           # 应用构建配置
└── proguard-rules.pro                     # 混淆规则
```

## 注意事项

1. **直播源版权** - 请确保使用的直播源有合法版权
2. **网络权限** - 应用需要网络连接才能播放视频
3. **设备兼容性** - 部分老旧设备可能不支持某些视频格式
4. **播放性能** - 播放质量取决于网络状况和设备性能

## 开发计划

- [ ] 添加EPG节目单支持
- [ ] 支持M3U播放列表导入
- [ ] 添加播放历史记录
- [ ] 支持多种画质切换
- [ ] 添加弹幕功能
- [ ] 支持频道分类管理

## 技术支持

如有问题或建议，请通过以下方式联系：
- 提交Issue到项目仓库
- 邮件联系开发者

## 许可证

本项目仅供学习和研究使用，请勿用于商业用途。