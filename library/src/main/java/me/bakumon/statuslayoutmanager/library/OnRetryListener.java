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
     * @param view  重试 view
     * @param state 重试 view 所在状态
     *              StatusLayoutManager#STATE_EMPTY：空数据；
     *              StatusLayoutManager#STATE_ERROR：出错；
     *              其他为自定义
     * @see StatusLayoutManager#STATE_EMPTY
     * @see StatusLayoutManager#STATE_ERROR
     */
    void onClick(View view, int state);
}
