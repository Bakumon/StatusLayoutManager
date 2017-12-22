package me.bakumon.statuslayoutmanager.library;

import android.view.View;

/**
 * 状态布局中 view 的点击事件
 * https://bakumon.me
 *
 * @author Baumon
 * @date 2017/12/19
 */

public interface OnStatusChildClickListener {

    /**
     * 空数据布局子 View 被点击
     *
     * @param view 被点击的 View
     */
    void onEmptyChildClick(View view);

    /**
     * 出错布局子 View 被点击
     *
     * @param view 被点击的 View
     */
    void onErrorChildClick(View view);

    /**
     * 自定义状态布局布局子 View 被点击
     *
     * @param view 被点击的 View
     */
    void onCustomerChildClick(View view);
}
