# StatusLayoutManager

切换不同的数据状态布局，包含加载中、空数据和出错状态。

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
    compile 'com.github.Bakumon:StatusLayoutManager:-SNAPSHOT'
    }
```

