# NewsApp

一个基于原生 Android 技术实现的新闻客户端应用。

## 技术栈与插件

- **开发语言**：Java
- **依赖管理**：Gradle
- **常用依赖插件**：
  - [Glide](https://github.com/bumptech/glide)：用于高效加载和缓存新闻图片
  - [Gson](https://github.com/google/gson)：对象与 JSON 数据互转，主要用于收藏新闻本地存储
  - [Material Components for Android](https://github.com/material-components/material-components-android)：提供现代化的 UI 组件
- **网络请求**：原生 `HttpURLConnection` 实现新闻数据接口访问

## 数据存储方式

- **本地存储**：项目采用 Android 原生的 `SharedPreferences` 进行数据持久化，封装在 `SharedPrefsUtils` 工具类中。
  - 存储内容包括：用户登录状态、用户名、密码、收藏新闻、夜间模式设置等。
  - 收藏新闻列表通过 `Gson` 序列化为 JSON 字符串保存。
- **无数据库**：未使用 SQLite、Room 等数据库存储方案，适合轻量级的本地数据管理。

## 界面设计

- **页面结构**：
  - 启动页（SplashActivity）：根据登录状态/夜间模式自动跳转主界面或登录页面
  - 登录页（LoginActivity）/注册页（RegisterActivity）：支持普通登录、游客登录与注册
  - 主界面（MainActivity）：采用底部导航栏切换新闻、收藏、设置等模块
  - 新闻详情页（NewsDetailActivity）：展示新闻全文、图片等信息
- **UI设计**：
  - 遵循 Material Design 风格，使用 CardView、BottomNavigationView、MaterialButton 等现代化控件
  - 支持夜间模式（Dark Mode），动态切换界面风格
  - 列表页采用 RecyclerView + Adapter 实现高性能新闻流展示

## 模块设计

- `activity`：页面与用户交互逻辑（如登录、注册、主界面、新闻详情等）
- `fragment`：功能模块化的页面片段（如新闻列表、收藏列表、设置页等）
- `adapter`：适配器类，负责将数据填充至 RecyclerView 列表
- `model`：数据结构模型（如新闻、用户等）
- `utils`：工具类，包含本地存储、网络请求等封装

## 运行截图

（此处可上传主界面、新闻详情、收藏、登录等页面截图）

## 如何运行

1. 下载源码并用 Android Studio 打开
2. 配置好 Android 环境（JDK 1.8+，Gradle 7+）
3. 连接 Android 设备或模拟器，点击运行即可体验

## 主要文件结构

```
app/
└── src/
    └── main/
        ├── java/com/example/newsapp/
        │   ├── activity/   // 各页面Activity
        │   ├── fragment/   // 各功能Fragment
        │   ├── adapter/    // RecyclerView适配器
        │   ├── model/      // 数据模型
        │   └── utils/      // 工具类
        ├── res/            // 资源文件
        └── AndroidManifest.xml
```

## 交流与反馈

如在使用过程中有任何问题或建议，欢迎提交 Issue 或 Pull Request！
