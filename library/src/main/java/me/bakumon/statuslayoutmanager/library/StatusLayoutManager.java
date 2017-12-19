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
    private View loadingLayout;
    /**
     * 空数据 view
     */
    private View emptyLayout;
    /**
     * 出错 view
     */
    private View errorLayout;

    private ReplaceLayoutHelper replaceLayoutHelper;

    private StatusLayoutManager(Builder builder) {
        this.contentLayout = builder.contentLayout;
        this.loadingLayout = builder.loadingLayout;
        this.emptyLayout = builder.emptyLayout;
        this.errorLayout = builder.errorLayout;
        replaceLayoutHelper = new ReplaceLayoutHelper(contentLayout);
    }

    public View getContentLayout() {
        return contentLayout;
    }

    public View getLoadingLayout() {
        return loadingLayout;
    }

    public View getEmptyLayout() {
        return emptyLayout;
    }

    public View getErrorLayout() {
        return errorLayout;
    }

    /**
     * 显示加载中布局
     */
    public void showLoadingLayout() {
        replaceLayoutHelper.showStatusLayout(loadingLayout);
    }

    /**
     * 显示空数据布局
     */
    public void showEmptyLayout() {
        replaceLayoutHelper.showStatusLayout(emptyLayout);
    }

    /**
     * 显示加载错误布局
     */
    public void showErrorLayout() {
        replaceLayoutHelper.showStatusLayout(errorLayout);
    }

    public void showSuccessLayout() {
        replaceLayoutHelper.restoreLayout();
    }

    public static final class Builder {

        private View contentLayout;
        private View loadingLayout;
        private View emptyLayout;
        private View errorLayout;

        private LayoutInflater inflater;

        public Builder(@NonNull View contentLayout) {
            this.contentLayout = contentLayout;
            // 设置默认布局
            this.loadingLayout = inflate(R.layout.layout_status_layout_manager_loading);
            this.emptyLayout = inflate(R.layout.layout_status_layout_manager_empty);
            this.errorLayout = inflate(R.layout.layout_status_layout_manager_error);
        }

        private View inflate(@LayoutRes int resource) {
            if (inflater == null) {
                inflater = LayoutInflater.from(contentLayout.getContext());
            }
            return inflater.inflate(resource, null);
        }

        public Builder setLoadingLayout(@NonNull View loadingLayout) {
            this.loadingLayout = loadingLayout;
            return this;
        }

        public Builder setLoadingLayout(@LayoutRes int loadingLayoutResId) {
            this.loadingLayout = inflate(loadingLayoutResId);
            return this;
        }

        public Builder setEmptyLayout(@NonNull View emptyLayout) {
            this.errorLayout = emptyLayout;
            return this;
        }

        public Builder setEmptyLayout(@LayoutRes int emptyLayoutResId) {
            this.emptyLayout = inflate(emptyLayoutResId);
            return this;
        }

        public Builder setErrorLayout(@NonNull View errorLayout) {
            this.errorLayout = errorLayout;
            return this;
        }

        public Builder setErrorLayout(@LayoutRes int errorLayoutResId) {
            this.errorLayout = inflate(errorLayoutResId);
            return this;
        }

        @NonNull
        public StatusLayoutManager build() {
            return new StatusLayoutManager(this);
        }

    }
}
