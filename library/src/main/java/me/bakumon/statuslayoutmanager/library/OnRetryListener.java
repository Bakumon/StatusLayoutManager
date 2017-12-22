package me.bakumon.statuslayoutmanager.library;

import android.view.View;

/**
 * 状态布局中 view 的点击事件
 * https://bakumon.me
 *
 * @author Baumon
 * @date 2017/12/19
 */

public interface OnRetryListener {
    /**
     * 状态布局中重试按钮的点击事件
     *
     * @param state 布局状态
     * @param view  重试 view
     */
    void onClick(@StatusLayoutManager.LAYOUT_STATE int state, View view);
}
