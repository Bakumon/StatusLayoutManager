package me.bakumon.statuslayoutmanager.library;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;

/**
 * 状态布局管理器
 * https://bakumon.me
 *
 * @author Bakumon
 * @date 2017/12/18
 */

public class StatusLayoutManager {

    public static final int STATE_EMPTY = -9738;
    public static final int STATE_ERROR = -9737;

    /**
     * 正常状态显示的 view
     */
    private View contentLayout;

    /**
     * 加载中 view
     */
    @LayoutRes
    private int loadingLayoutID;
    private View loadingLayout;

    /**
     * 空数据 view
     */
    @IdRes
    private int emptyRetryViewId;
    @LayoutRes
    private int emptyLayoutID;
    private View emptyLayout;

    /**
     * 出错 view
     */
    @IdRes
    private int errorRetryViewId;
    @LayoutRes
    private int errorLayoutID;
    private View errorLayout;

    private OnRetryListener onRetryListener;

    private LayoutInflater inflater;
    private ReplaceLayoutHelper replaceLayoutHelper;

    private StatusLayoutManager(Builder builder) {
        this.contentLayout = builder.contentLayout;

        this.loadingLayoutID = builder.loadingLayoutID;
        this.loadingLayout = builder.loadingLayout;

        this.emptyLayoutID = builder.emptyLayoutID;
        this.emptyLayout = builder.emptyLayout;
        this.emptyRetryViewId = builder.emptyRetryViewId;

        this.errorLayoutID = builder.errorLayoutID;
        this.errorLayout = builder.errorLayout;
        this.errorRetryViewId = builder.errorRetryViewId;

        this.onRetryListener = builder.onRetryListener;

        replaceLayoutHelper = new ReplaceLayoutHelper(contentLayout);
    }

    public View getContentLayout() {
        return contentLayout;
    }

    public View getLoadingLayout() {
        createLoadingLayout();
        return loadingLayout;
    }

    public View getEmptyLayout() {
        createEmptyLayout();
        return emptyLayout;
    }

    public View getErrorLayout() {
        createErrorLayout();
        return errorLayout;
    }

    private View inflate(@LayoutRes int resource) {
        if (inflater == null) {
            inflater = LayoutInflater.from(contentLayout.getContext());
        }
        return inflater.inflate(resource, null);
    }

    /**
     * 创建加载中布局
     */
    private void createLoadingLayout() {
        if (loadingLayout == null) {
            loadingLayout = inflate(loadingLayoutID);
        }
    }

    /**
     * 显示加载中布局
     */
    public void showLoadingLayout() {
        createLoadingLayout();
        replaceLayoutHelper.showStatusLayout(loadingLayout);
    }

    /**
     * 创建空白布局
     */
    private void createEmptyLayout() {
        if (emptyLayout == null) {
            emptyLayout = inflate(emptyLayoutID);
        }
        if (onRetryListener == null) {
            return;
        }

        View view = emptyLayout.findViewById(emptyRetryViewId);
        if (view == null) {
            return;
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRetryListener.onClick(view, STATE_EMPTY);
            }
        });
    }

    /**
     * 显示空数据布局
     */
    public void showEmptyLayout() {
        createEmptyLayout();
        replaceLayoutHelper.showStatusLayout(emptyLayout);
    }

    private void createErrorLayout() {
        if (errorLayout == null) {
            errorLayout = inflate(errorLayoutID);
        }
        if (onRetryListener == null) {
            return;
        }

        View view = errorLayout.findViewById(errorRetryViewId);
        if (view == null) {
            return;
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRetryListener.onClick(view, STATE_ERROR);
            }
        });
    }

    /**
     * 显示加载错误布局
     */
    public void showErrorLayout() {
        createErrorLayout();
        replaceLayoutHelper.showStatusLayout(errorLayout);
    }

    /**
     * 显示原有布局
     */
    public void showSuccessLayout() {
        replaceLayoutHelper.restoreLayout();
    }

    // TODO: 2017/12/20 自定义布局应该放在 build 中
    /**
     * 自定义状态布局
     *
     * @param customLayout 自定义布局
     */
    public void showCustomLayout(@NonNull View customLayout) {
        replaceLayoutHelper.showStatusLayout(customLayout);
    }

    /**
     * 自定义状态布局
     *
     * @param customLayoutID 自定义状态布局 ID
     */
    public void showCustomLayout(@LayoutRes int customLayoutID) {
        replaceLayoutHelper.showStatusLayout(inflate(customLayoutID));
    }

    /**
     * 自定义状态布局
     *
     * @param customLayout  自定义布局
     * @param retryID       重试按钮 ID
     * @param customerState 自定义布局状态，必须是除 StatusLayoutManager#STATE_ERROR StatusLayoutManager#STATE_EMPTY 以外的值
     * @see StatusLayoutManager#STATE_ERROR
     * @see StatusLayoutManager#STATE_EMPTY
     */
    public void showCustomLayout(@NonNull View customLayout, @IdRes int retryID, int customerState) {

    }

    /**
     * 自定义状态布局
     *
     * @param customLayoutID 自定义布局 ID
     * @param retryID        重试按钮 ID
     * @param customerState  自定义布局状态，必须是除 StatusLayoutManager#STATE_ERROR StatusLayoutManager#STATE_EMPTY 以外的值
     * @see StatusLayoutManager#STATE_ERROR
     * @see StatusLayoutManager#STATE_EMPTY
     */
    public void showCustomLayout(@LayoutRes int customLayoutID, @IdRes int retryID, int customerState) {

    }

    public static final class Builder {

        private View contentLayout;

        @LayoutRes
        private int loadingLayoutID;
        private View loadingLayout;

        @IdRes
        private int emptyRetryViewId;
        @LayoutRes
        private int emptyLayoutID;
        private View emptyLayout;

        @IdRes
        private int errorRetryViewId;
        @LayoutRes
        private int errorLayoutID;
        private View errorLayout;

        private OnRetryListener onRetryListener;

        public Builder(@NonNull View contentLayout) {
            this.contentLayout = contentLayout;
            // 设置默认布局
            this.loadingLayoutID = R.layout.layout_status_layout_manager_loading;
            this.emptyLayoutID = R.layout.layout_status_layout_manager_empty;
            this.errorLayoutID = R.layout.layout_status_layout_manager_error;
            // 设置默认重试点击view id
            this.emptyRetryViewId = R.id.bt_status_empty_retry;
            this.errorRetryViewId = R.id.bt_status_error_retry;
        }

        public Builder setLoadingLayout(@LayoutRes int loadingLayoutID) {
            this.loadingLayoutID = loadingLayoutID;
            return this;
        }

        public Builder setLoadingLayout(@NonNull View loadingLayout) {
            this.loadingLayout = loadingLayout;
            return this;
        }

        public Builder setEmptyLayout(@LayoutRes int emptyLayoutResId) {
            this.emptyLayoutID = emptyLayoutResId;
            return this;
        }

        public Builder setEmptyLayout(@NonNull View emptyLayout) {
            this.emptyLayout = emptyLayout;
            return this;
        }

        public Builder setEmptyRetryID(@IdRes int emptyRetryResId) {
            this.emptyRetryViewId = emptyRetryResId;
            return this;
        }

        public Builder setErrorLayout(@LayoutRes int errorLayoutResId) {
            this.errorLayoutID = errorLayoutResId;
            return this;
        }

        public Builder setErrorLayout(@NonNull View errorLayout) {
            this.errorLayout = errorLayout;
            return this;
        }

        public Builder setErrorRetryID(@IdRes int errorRetryResId) {
            this.errorRetryViewId = errorRetryResId;
            return this;
        }

        public Builder setOnRetryListener(OnRetryListener listener) {
            this.onRetryListener = listener;
            return this;
        }

        @NonNull
        public StatusLayoutManager build() {
            return new StatusLayoutManager(this);
        }

    }
}
