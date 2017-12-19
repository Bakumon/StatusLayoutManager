package me.bakumon.statuslayoutmanager.library;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;

/**
 * 状态布局管理器
 * https://bakumon.me
 * Created by Bakumon on 2017/12/18.
 */

public class StatusLayoutManager {
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
    @LayoutRes
    private int emptyLayoutID;
    private View emptyLayout;

    /**
     * 出错 view
     */
    @LayoutRes
    private int errorLayoutID;
    private View errorLayout;

    private LayoutInflater inflater;

    private ReplaceLayoutHelper replaceLayoutHelper;

    private StatusLayoutManager(Builder builder) {
        this.contentLayout = builder.contentLayout;

        this.loadingLayoutID = builder.loadingLayoutID;
        this.loadingLayout = builder.loadingLayout;

        this.emptyLayoutID = builder.emptyLayoutID;
        this.emptyLayout = builder.emptyLayout;

        this.errorLayoutID = builder.errorLayoutID;
        this.errorLayout = builder.errorLayout;

        replaceLayoutHelper = new ReplaceLayoutHelper(contentLayout);
    }

    public View getContentLayout() {
        return contentLayout;
    }

    public View getLoadingLayout() {
        if (loadingLayout == null) {
            loadingLayout = inflate(loadingLayoutID);
        }
        return loadingLayout;
    }

    public View getEmptyLayout() {
        if (emptyLayout == null) {
            emptyLayout = inflate(emptyLayoutID);
        }
        return emptyLayout;
    }

    public View getErrorLayout() {
        if (errorLayout == null) {
            errorLayout = inflate(errorLayoutID);
        }
        return errorLayout;
    }

    private View inflate(@LayoutRes int resource) {
        if (inflater == null) {
            inflater = LayoutInflater.from(contentLayout.getContext());
        }
        return inflater.inflate(resource, null);
    }

    /**
     * 显示加载中布局
     */
    public void showLoadingLayout() {
        if (loadingLayout == null) {
            loadingLayout = inflate(loadingLayoutID);
        }
        replaceLayoutHelper.showStatusLayout(loadingLayout);
    }

    /**
     * 显示空数据布局
     */
    public void showEmptyLayout() {
        if (emptyLayout == null) {
            emptyLayout = inflate(emptyLayoutID);
        }
        replaceLayoutHelper.showStatusLayout(emptyLayout);
    }

    /**
     * 显示加载错误布局
     */
    public void showErrorLayout() {
        if (errorLayout == null) {
            errorLayout = inflate(errorLayoutID);
        }
        replaceLayoutHelper.showStatusLayout(errorLayout);
    }

    /**
     * 显示原有布局
     */
    public void showSuccessLayout() {
        replaceLayoutHelper.restoreLayout();
    }

    public static final class Builder {

        private View contentLayout;

        @LayoutRes
        private int loadingLayoutID;
        private View loadingLayout;

        @LayoutRes
        private int emptyLayoutID;
        private View emptyLayout;

        @LayoutRes
        private int errorLayoutID;
        private View errorLayout;


        public Builder(@NonNull View contentLayout) {
            this.contentLayout = contentLayout;
            // 设置默认布局
            this.loadingLayoutID = R.layout.layout_status_layout_manager_loading;
            this.emptyLayoutID = R.layout.layout_status_layout_manager_empty;
            this.errorLayoutID = R.layout.layout_status_layout_manager_error;
        }

        public Builder setLoadingLayout(@LayoutRes int loadingLayoutResId) {
            this.loadingLayoutID = loadingLayoutResId;
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

        public Builder setErrorLayout(@LayoutRes int errorLayoutResId) {
            this.errorLayoutID = errorLayoutResId;
            return this;
        }

        public Builder setErrorLayout(@NonNull View errorLayout) {
            this.errorLayout = errorLayout;
            return this;
        }

        @NonNull
        public StatusLayoutManager build() {
            return new StatusLayoutManager(this);
        }

    }
}
