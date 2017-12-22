package me.bakumon.statuslayoutmanager.library;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

/**
 * 替换布局帮助类
 * https://bakumon.me
 *
 * @author Bakumon
 * @date 2017/12/18
 */
public class ReplaceLayoutHelper {

    /**
     * 需要替换的 View
     */
    private View contentLayout;
    /**
     * contentLayout 的布局参数
     */
    private ViewGroup.LayoutParams params;
    /**
     * contentLayout 的父 ViewGroup
     */
    private ViewGroup parentLayout;
    /**
     * contentLayout 在 parentLayout 中的位置
     */
    private int viewIndex;
    /**
     * 当前显示的 View
     */
    private View currentLayout;

    public ReplaceLayoutHelper(@NonNull View contentLayout) {
        this.contentLayout = contentLayout;
        getContentLayoutParams();
    }

    /**
     * 获取 contentLayout 的参数信息 LayoutParams、Parent
     */
    private void getContentLayoutParams() {
        this.params = contentLayout.getLayoutParams();
        if (contentLayout.getParent() != null) {
            this.parentLayout = (ViewGroup) contentLayout.getParent();
        } else {
            this.parentLayout = contentLayout.getRootView().findViewById(android.R.id.content);
        }
        int count = parentLayout.getChildCount();
        for (int index = 0; index < count; index++) {
            if (contentLayout == parentLayout.getChildAt(index)) {
                // 获取 contentLayout 在 parentLayout 中的位置
                this.viewIndex = index;
                break;
            }
        }
        this.currentLayout = this.contentLayout;
    }

    public boolean showStatusLayout(View view) {
        // 如果当前显示的布局不是 view，才进行替换
        if (view == null) {
            return false;
        }
        if (currentLayout != view) {
            currentLayout = view;
            ViewGroup parent = (ViewGroup) view.getParent();
            // 去除 view 的 父 view，才能添加到别的 ViewGroup 中
            if (parent != null) {
                parent.removeView(view);
            }
            // 替换 = 移除 + 添加
            parentLayout.removeViewAt(viewIndex);
            parentLayout.addView(view, viewIndex, params);
            return true;
        }
        return false;
    }

    public boolean restoreLayout() {
        return showStatusLayout(contentLayout);
    }

}
