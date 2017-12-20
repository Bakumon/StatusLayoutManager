# StatusLayoutManager
切换不同的数据状态布局，包含加载中、空数据和出错状态。

1. 替换布局，不会增加布局层数
2. 提供一套默认状态布局，可快速使用
3. 布局懒加载，使用 showXXXLayout() 或 getXXXLayout() 才开始inflate 布局
4. 重试按钮统一回调