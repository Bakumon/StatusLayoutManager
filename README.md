# StatusLayoutManager

[![Release](https://jitpack.io/v/Bakumon/StatusLayoutManager.svg)](https://jitpack.io/#Bakumon/StatusLayoutManager)
[![API](https://img.shields.io/badge/API-11%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=11)

切换不同的数据状态布局，包含加载中、空数据和出错状态。

## 预览

![status_layout_manager.gif](https://github.com/Bakumon/StatusLayoutManager/raw/master/gif/status_layout_manager.gif)

下载 [demo](https://github.com/Bakumon/StatusLayoutManager/raw/master/apk/StatusLayoutManager.apk) 体验

## 特征

1. 不会增加布局层数
2. 提供一套可配置的默认状态布局
3. 布局懒加载
4. 重试按钮统一回调
5. 支持自定义状态布局

## 下载

1. 在项目的 `build.gradle` 中添加：

```
allprojects {
    repositories {
	    ...
	    maven { url 'https://jitpack.io' }
    }
}
```

2. 添加依赖

```
dependencies {
    compile 'com.github.Bakumon:StatusLayoutManager:1.0.1'
}
```

## 使用

### 快速使用

创建 `StatusLayoutManager` 对象:

```java
statusLayoutManager = new StatusLayoutManager.Builder(recyclerView)
    // 设置重试事件监听器
    .setOnStatusChildClickListener(new DefaultOnStatusChildClickListener() {
        @Override
        public void onEmptyChildClick(View view) {

        }

        @Override
        public void onErrorChildClick(View view) {

        }
    })
    .build();
```

在合适的时机显示对应的状态布局：

```java
// 加载中
statusLayoutManager.showLoadingLayout();
// 空数据
statusLayoutManager.showEmptyLayout();
// 加载失败
statusLayoutManager.showErrorLayout();
// 加载成功，显示原布局
statusLayoutManager.showSuccessLayout();
```

以上可以满足大多数场景。

### 配置默认布局

以下 API 提供修改默认布局的方法，具体说明见注释。

```java
statusLayoutManager = new StatusLayoutManager.Builder(recyclerView)

    // 设置默认加载中布局的提示文本
    .setDefaultLoadingText("l拼命加载中...")

    // 设置默认空数据布局的提示文本
    .setDefaultEmptyText("空白了，哈哈哈哈")
    // 设置默认空数据布局的图片
    .setDefaultEmptyImg(R.mipmap.ic_launcher)
    // 设置默认空数据布局重试按钮的文本
    .setDefaultEmptyRetryText("retry")
    // 设置默认空数据布局重试按钮的文本颜色
    .setDefaultEmptyRetryTextColor(getResources().getColor(R.color.colorAccent))
    // 设置默认空数据布局重试按钮是否显示
    .setDefaultEmptyRetryVisible(false)

    // 设置默认出错布局的提示文本
    .setDefaultErrorText(R.string.app_name)
    // 设置默认出错布局的图片
    .setDefaultErrorImg(R.mipmap.ic_launcher)
    // 设置默认出错布局重试按钮的文本
    .setDefaultErrorRetryText("重试一波")
    // 设置默认出错布局重试按钮的文本颜色
    .setDefaultErrorRetryTextColor(getResources().getColor(R.color.colorPrimaryDark))
    // 设置默认出错布局重试按钮是否显示
    .setDefaultErrorRetryVisible(true)

    // 设置布局背景，包括加载中、空数据和出错布局
    .setDefaultLayoutsBackgroundColor(Color.WHITE)
    .build();
```

## 自定义默认布局

```java
statusLayoutManager = new StatusLayoutManager.Builder(recyclerView)
    // 设置加载中布局
    .setLoadingLayout(inflate(R.layout.layout_loading))
    // 设置空数据布局
    .setEmptyLayout(inflate(R.layout.layout_empty))
    // 设置出错布局
    .setErrorLayout(inflate(R.layout.layout_error))

    // 设置加载中布局
    .setLoadingLayout(R.layout.layout_loading)
    // 设置空数据布局
    .setEmptyLayout(R.layout.layout_empty)
    // 设置出错布局
    .setErrorLayout(R.layout.layout_error)

    // 设置空数据布局重试按钮 ID
    .setEmptyRetryID(R.id.tv_empty)
    // 设置出错布局重试按钮 ID
    .setErrorRetryID(R.id.tv_error)
    .build();
```

## 显示自定义状态布局

`statusLayoutManager#showCustomLayout()`有几个重载方法，下面以参数最多的为例介绍：

```java
/**
 * 显示自定义状态布局
 *
 * @param customLayoutID 自定义布局 ID
 * @param clickViewID        重试按钮 ID
 * @return 自定义状态布局
 */
statusLayoutManager.showCustomLayout(R.layout.layout_custome, R.id.tv_customer, R.id.tv_customer1);
```

其中 `clickViewID` 参数，表示想要添加点击事件的 View 的 id。

## 点击监听

```java
statusLayoutManager = new StatusLayoutManager.Builder(recyclerView)

    // 设置重试事件监听器
    .setOnStatusLayoutClickListener(new OnStatusLayoutClickListener() {
            @Override
            public void onEmptyChildClick(View view) {

            }

            @Override
            public void onErrorChildClick(View view) {

            }

            @Override
            public void onCustomerChildClick(View view) {

            }
        }
    })
    .build();
```

也可以使用 `OnStatusLayoutClickListener` 默认的实现类，像下面这样：

```java
statusLayoutManager = new StatusLayoutManager.Builder(recyclerView)
    // 设置重试事件监听器
    .setOnStatusChildClickListener(new DefaultOnStatusChildClickListener() {
        @Override
        public void onEmptyChildClick(View view) {

        }

        @Override
        public void onErrorChildClick(View view) {

        }
    })
    .build();
```