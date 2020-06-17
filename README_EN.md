# StatusLayoutManager

[![Release](https://jitpack.io/v/Bakumon/StatusLayoutManager.svg)](https://jitpack.io/#Bakumon/StatusLayoutManager)
[![API](https://img.shields.io/badge/API-11%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=11)
[![Gitads.io](https://img.shields.io/badge/GitAds-valid-brightgreen)](https://tracking.gitads.io/?campaign=gitads&repo=StatusLayoutManager&redirect=gitads.io)

[中文版](https://github.com/Bakumon/StatusLayoutManager/blob/master/README.md) | English Version

Switch between different data state layouts, including loading medium and empty data and error status.

## Feature

1. Does not increase the number of layout layers
2. Provide a configurable default state layout
3. Layout lazy loading
4. Retry button unified callback
5. Support custom state layout

## Demo

Download [demo](https://github.com/Bakumon/StatusLayoutManager/raw/master/apk/app-release.apk)

<a href='https://play.google.com/store/apps/details?id=me.bakumon.statuslayoutmanager'><img alt='Get it on Google Play' src='https://i.loli.net/2018/06/27/5b32eac49f930.png' height="60"/>

![status_layout_manager.gif](https://github.com/Bakumon/StatusLayoutManager/raw/master/gif/status_layout_manager.gif)

## Download

1. project `build.gradle`:

```
allprojects {
    repositories {
	    ...
	    maven { url 'https://jitpack.io' }
    }
}
```

2. app `build.gradle`:

```
dependencies {
    implementation 'com.github.Bakumon:StatusLayoutManager:1.0.4'
}
```

## Usage & Examples

### Getting started

Create a `StatusLayoutManager` object with `StatusLayoutManager#Builder`:

```java
statusLayoutManager = new StatusLayoutManager.Builder(recyclerView)
    .build();
```

Display the corresponding status layout at the right time:

```java
// Loading
statusLayoutManager.showLoadingLayout();
// Empty data
statusLayoutManager.showEmptyLayout();
// Error
statusLayoutManager.showErrorLayout();
// success
statusLayoutManager.showSuccessLayout();
```

The above can satisfy most scenarios.

### Configuring the default layout

The following APIs provide a way to modify the default layout.

```java
statusLayoutManager = new StatusLayoutManager.Builder(recyclerView)

    // Set the prompt text for the layout in the default load
    .setDefaultLoadingText("loading...")

    // Set the prompt text for the default empty data layout
    .setDefaultEmptyText("Empty")
    // Set the image of the default empty data layout
    .setDefaultEmptyImg(R.mipmap.ic_launcher)
    // Set the text of the default empty data layout retry button
    .setDefaultEmptyRetryText("retry")
    // Set the text color of the default empty data layout retry button
    .setDefaultEmptyRetryTextColor(getResources().getColor(R.color.colorAccent))
    // Set the default empty data layout retry button to display
    .setDefaultEmptyRetryVisible(false)

    // Set the prompt text for the default error layout
    .setDefaultErrorText(R.string.app_name)
    // Set the image of the default error layout
    .setDefaultErrorImg(R.mipmap.ic_launcher)
    // Set the text of the default error layout retry button
    .setDefaultErrorRetryText("Reload")
    // Set the text color of the default error layout retry button
    .setDefaultErrorRetryTextColor(getResources().getColor(R.color.colorPrimaryDark))
    // Set default error layout retry button to display
    .setDefaultErrorRetryVisible(true)

    // Set the default layout background, including loading medium and empty data and error layout
    .setDefaultLayoutsBackgroundColor(Color.WHITE)
    .build();
```

### Custom default layout

```java
statusLayoutManager = new StatusLayoutManager.Builder(recyclerView)
    // Set the layout in load
    .setLoadingLayout(inflate(R.layout.layout_loading))
    // Set empty data layout
    .setEmptyLayout(inflate(R.layout.layout_empty))
    // Set the error layout
    .setErrorLayout(inflate(R.layout.layout_error))

    // Set the layout in load
    .setLoadingLayout(R.layout.layout_loading)
    // Set empty data layout
    .setEmptyLayout(R.layout.layout_empty)
    // Set the error layout
    .setErrorLayout(R.layout.layout_error)

    // Set empty data layout retry button ID
    .setEmptyRetryID(R.id.tv_empty)
    // Set error layout retry button ID
    .setErrorRetryID(R.id.tv_error)
    .build();
```

### Show custom state layout

`statusLayoutManager#showCustomLayout()`There are several overloading methods. The following takes the most parameters as an example:

```java
/**
 * Show custom state layout
 *
 * @param customLayoutID Custom layout ID
 * @param clickViewID        Retry button ID
 * @return Custom state layout
 */
statusLayoutManager.showCustomLayout(R.layout.layout_custome, R.id.tv_customer, R.id.tv_customer1);
```

The `clickViewID` parameter indicates the id of the View to which you want to add a click event.

### onClick listener

```java
statusLayoutManager = new StatusLayoutManager.Builder(recyclerView)

    // Set retry event listener
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

You can also use the default implementation class of `OnStatusLayoutClickListener` , like this:

```java
statusLayoutManager = new StatusLayoutManager.Builder(recyclerView)
    // Set retry event listener
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

## Known issue

#### 1. StatusLayoutManager#Builder(View view)：parameter requirements

If you use `android.support.v4.widget.SwipeRefreshLayout`, it is recommended to pass the `SwipeRefreshLayout` object directly as the `view` to be replaced to the `Builder` constructor instead of its child view.

## License

[MIT License](https://github.com/Bakumon/StatusLayoutManager/blob/master/LICENSE)

## Sponsor

[![Gitads.io](https://i.loli.net/2020/06/17/2SiZPMuoEQzjh1k.png)](https://tracking.gitads.io/?campaign=gitads&repo=StatusLayoutManager&redirect=gitads.io)
