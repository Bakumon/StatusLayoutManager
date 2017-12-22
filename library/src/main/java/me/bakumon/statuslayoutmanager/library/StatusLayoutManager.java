package me.bakumon.statuslayoutmanager.library;

import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 状态布局管理器
 * https://bakumon.me
 *
 * @author Bakumon
 * @date 2017/12/18
 */

public class StatusLayoutManager {

    @IntDef({STATE_EMPTY, STATE_ERROR, STATE_CUSTOMER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LAYOUT_STATE {
    }

    /**
     * 当前状态为空数据状态
     */
    public static final int STATE_EMPTY = 0;
    /**
     * 当前状态为出错状态
     */
    public static final int STATE_ERROR = 1;
    /**
     * 当前状态为自定义布局状态
     */
    public static final int STATE_CUSTOMER = -1;

    private View contentLayout;

    @LayoutRes
    private int loadingLayoutID;
    private View loadingLayout;
    private String loadingText;

    @IdRes
    private int emptyRetryViewId;
    @LayoutRes
    private int emptyLayoutID;
    private View emptyLayout;
    private String emptyText;
    private String emptyRetryText;
    private int emptyRetryTextColor;
    private boolean isEmptyRetryVisible;
    @DrawableRes
    private int emptyImgID;

    @IdRes
    private int errorRetryViewId;
    @LayoutRes
    private int errorLayoutID;
    private View errorLayout;
    private String errorText;
    private String errorRetryText;
    private int errorRetryTextColor;
    private boolean isErrorRetryVisible;
    @DrawableRes
    private int errorImgID;

    private int defaultBackgroundColor;

    private OnRetryListener onRetryListener;

    private ReplaceLayoutHelper replaceLayoutHelper;

    private LayoutInflater inflater;

    private StatusLayoutManager(Builder builder) {
        this.contentLayout = builder.contentLayout;

        this.loadingLayoutID = builder.loadingLayoutID;
        this.loadingLayout = builder.loadingLayout;
        this.loadingText = builder.loadingText;

        this.emptyRetryViewId = builder.emptyRetryViewId;
        this.emptyLayoutID = builder.emptyLayoutID;
        this.emptyLayout = builder.emptyLayout;
        this.emptyText = builder.emptyText;
        this.emptyRetryText = builder.emptyRetryText;
        this.emptyRetryTextColor = builder.emptyRetryTextColor;
        this.isEmptyRetryVisible = builder.isEmptyRetryVisible;
        this.emptyImgID = builder.emptyImgID;

        this.errorRetryViewId = builder.errorRetryViewId;
        this.errorLayoutID = builder.errorLayoutID;
        this.errorLayout = builder.errorLayout;
        this.errorText = builder.errorText;
        this.errorRetryText = builder.errorRetryText;
        this.errorRetryTextColor = builder.errorRetryTextColor;
        this.isErrorRetryVisible = builder.isErrorRetryVisible;
        this.errorImgID = builder.errorImgID;

        this.defaultBackgroundColor = builder.defaultBackgroundColor;

        this.onRetryListener = builder.onRetryListener;

        this.replaceLayoutHelper = new ReplaceLayoutHelper(contentLayout);
    }

    private View inflate(@LayoutRes int resource) {
        if (inflater == null) {
            inflater = LayoutInflater.from(contentLayout.getContext());
        }
        return inflater.inflate(resource, null);
    }

    ///////////////////////////////////////////
    /////////////////原有布局////////////////////
    ///////////////////////////////////////////

    /**
     * 显示原有布局
     */
    public void showSuccessLayout() {
        replaceLayoutHelper.restoreLayout();
    }

    ///////////////////////////////////////////
    ////////////////加载中布局///////////////////
    ///////////////////////////////////////////

    /**
     * 创建加载中布局
     */
    private void createLoadingLayout() {
        if (loadingLayout == null) {
            loadingLayout = inflate(loadingLayoutID);
        }
        loadingLayout.setBackgroundColor(defaultBackgroundColor);
        if (!TextUtils.isEmpty(loadingText)) {
            TextView loadingTextView = loadingLayout.findViewById(R.id.tv_status_loading_content);
            if (loadingTextView != null) {
                loadingTextView.setText(loadingText);
            }
        }
    }

    /**
     * 获取加载中布局
     *
     * @return 加载中布局
     */
    public View getLoadingLayout() {
        createLoadingLayout();
        return loadingLayout;
    }

    /**
     * 显示加载中布局
     */
    public void showLoadingLayout() {
        createLoadingLayout();
        replaceLayoutHelper.showStatusLayout(loadingLayout);
    }

    ///////////////////////////////////////////
    ////////////////空数据布局///////////////////
    ///////////////////////////////////////////

    /**
     * 创建空数据布局
     */
    private void createEmptyLayout() {
        if (emptyLayout == null) {
            emptyLayout = inflate(emptyLayoutID);
        }
        emptyLayout.setBackgroundColor(defaultBackgroundColor);

        if (onRetryListener == null) {
            return;
        }

        View view = emptyLayout.findViewById(emptyRetryViewId);
        if (view == null) {
            return;
        }

        // 设置重试按钮点击时事件回调
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRetryListener.onClick(STATE_EMPTY, view);
            }
        });

        // 设置默认空数据布局的提示文本
        if (!TextUtils.isEmpty(emptyText)) {
            TextView emptyTextView = emptyLayout.findViewById(R.id.tv_status_empty_content);
            if (emptyTextView != null) {
                emptyTextView.setText(emptyText);
            }
        }

        // 设置默认空数据布局的图片
        if (emptyImgID > 0) {
            ImageView emptyImageView = emptyLayout.findViewById(R.id.iv_status_empty_img);
            if (emptyImageView != null) {
                emptyImageView.setImageResource(emptyImgID);
            }
        }

        TextView emptyRetryTextView = emptyLayout.findViewById(R.id.bt_status_empty_retry);
        if (emptyRetryTextView == null) {
            return;
        }
        // 设置重试按钮的文本和可见性
        if (isEmptyRetryVisible) {
            emptyRetryTextView.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(emptyRetryText)) {
                emptyRetryTextView.setText(emptyRetryText);
            }
            emptyRetryTextView.setTextColor(emptyRetryTextColor);
        } else {
            emptyRetryTextView.setVisibility(View.GONE);
        }
    }

    /**
     * 获取空数据布局
     *
     * @return 空数据布局
     */
    public View getEmptyLayout() {
        createEmptyLayout();
        return emptyLayout;
    }

    /**
     * 显示空数据布局
     */
    public void showEmptyLayout() {
        createEmptyLayout();
        replaceLayoutHelper.showStatusLayout(emptyLayout);
    }

    ///////////////////////////////////////////
    /////////////////出错布局////////////////////
    ///////////////////////////////////////////

    /**
     * 创建出错布局
     */
    private void createErrorLayout() {
        if (errorLayout == null) {
            errorLayout = inflate(errorLayoutID);
        }
        errorLayout.setBackgroundColor(defaultBackgroundColor);

        if (onRetryListener == null) {
            return;
        }

        View view = errorLayout.findViewById(errorRetryViewId);
        if (view == null) {
            return;
        }

        // 设置重试按钮点击时事件回调
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRetryListener.onClick(STATE_ERROR, view);
            }
        });

        // 设置默认出错布局的提示文本
        if (!TextUtils.isEmpty(errorText)) {
            TextView errorTextView = errorLayout.findViewById(R.id.tv_status_error_content);
            if (errorTextView != null) {
                errorTextView.setText(errorText);
            }
        }

        // 设置默认出错布局的图片
        if (errorImgID > 0) {
            ImageView errorImageView = errorLayout.findViewById(R.id.iv_status_error_image);
            if (errorImageView != null) {
                errorImageView.setImageResource(errorImgID);
            }
        }

        TextView errorRetryTextView = errorLayout.findViewById(R.id.bt_status_error_retry);
        if (errorRetryTextView == null) {
            return;
        }
        // 设置重试按钮的文本和可见性
        if (isErrorRetryVisible) {
            errorRetryTextView.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(errorRetryText)) {
                errorRetryTextView.setText(errorRetryText);
            }
            errorRetryTextView.setTextColor(errorRetryTextColor);
        } else {
            errorRetryTextView.setVisibility(View.GONE);
        }
    }

    /**
     * 获取出错布局
     *
     * @return 出错布局
     */
    public View getErrorLayout() {
        createErrorLayout();
        return errorLayout;
    }

    /**
     * 显示出错布局
     */
    public void showErrorLayout() {
        createErrorLayout();
        replaceLayoutHelper.showStatusLayout(errorLayout);
    }

    ///////////////////////////////////////////
    ////////////////自定义布局///////////////////
    ///////////////////////////////////////////

    /**
     * 显示自定义状态布局
     *
     * @param customLayout 自定义布局
     */
    public void showCustomLayout(@NonNull View customLayout) {
        replaceLayoutHelper.showStatusLayout(customLayout);
    }

    /**
     * 显示自定义状态布局
     *
     * @param customLayoutID 自定义状态布局 ID
     * @return 通过 customLayoutID 生成的 View
     */
    public View showCustomLayout(@LayoutRes int customLayoutID) {
        View customerView = inflate(customLayoutID);
        showCustomLayout(customerView);
        return customerView;
    }

    /**
     * 显示自定义状态布局
     *
     * @param customLayout 自定义布局
     * @param retryID      重试按钮 ID
     */
    public void showCustomLayout(@NonNull View customLayout, @IdRes int... retryID) {
        replaceLayoutHelper.showStatusLayout(customLayout);
        if (onRetryListener == null) {
            return;
        }

        for (int aRetryID : retryID) {
            View retryView = customLayout.findViewById(aRetryID);
            if (retryView == null) {
                return;
            }

            // 设置重试按钮点击时事件回调
            retryView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onRetryListener.onClick(STATE_CUSTOMER, view);
                }
            });
        }
    }

    /**
     * 显示自定义状态布局
     *
     * @param customLayoutID 自定义布局 ID
     * @param retryID        重试按钮 ID
     */
    public View showCustomLayout(@LayoutRes int customLayoutID, @IdRes int... retryID) {
        View customLayout = inflate(customLayoutID);
        showCustomLayout(customLayout, retryID);
        return customLayout;
    }

    public static final class Builder {

        private View contentLayout;

        @LayoutRes
        private int loadingLayoutID;
        private View loadingLayout;
        private String loadingText;

        @IdRes
        private int emptyRetryViewId;
        @LayoutRes
        private int emptyLayoutID;
        private View emptyLayout;
        private String emptyText;
        private String emptyRetryText;
        private int emptyRetryTextColor;
        private boolean isEmptyRetryVisible;
        @DrawableRes
        private int emptyImgID;

        @IdRes
        private int errorRetryViewId;
        @LayoutRes
        private int errorLayoutID;
        private View errorLayout;
        private String errorText;
        private String errorRetryText;
        private int errorRetryTextColor;
        private boolean isErrorRetryVisible;
        @DrawableRes
        private int errorImgID;

        private int defaultBackgroundColor;

        private OnRetryListener onRetryListener;

        /**
         * 创建状态布局 Build 对象
         *
         * @param contentLayout 原有布局，内容布局
         */
        public Builder(@NonNull View contentLayout) {
            this.contentLayout = contentLayout;
            // 设置默认布局
            this.loadingLayoutID = R.layout.layout_status_layout_manager_loading;
            this.emptyLayoutID = R.layout.layout_status_layout_manager_empty;
            this.errorLayoutID = R.layout.layout_status_layout_manager_error;
            // 设置默认重试点击view id
            this.emptyRetryViewId = R.id.bt_status_empty_retry;
            this.errorRetryViewId = R.id.bt_status_error_retry;
            // 设置默认重试按钮属性
            this.isEmptyRetryVisible = true;
            this.emptyRetryTextColor = contentLayout.getContext().getResources().getColor(R.color.status_layout_retry_text_color);
            this.isErrorRetryVisible = true;
            this.errorRetryTextColor = contentLayout.getContext().getResources().getColor(R.color.status_layout_retry_text_color);
            // 设置默认背景色
            this.defaultBackgroundColor = contentLayout.getContext().getResources().getColor(R.color.status_layout_background_color);
        }

        ///////////////////////////////////////////
        ////////////////加载中布局///////////////////
        ///////////////////////////////////////////

        /**
         * 设置加载中布局
         *
         * @param loadingLayoutID 加载中布局 ID
         * @return 状态布局 Build 对象
         */
        public Builder setLoadingLayout(@LayoutRes int loadingLayoutID) {
            this.loadingLayoutID = loadingLayoutID;
            return this;
        }

        /**
         * 设置加载中布局
         *
         * @param loadingLayout 加载中布局
         * @return 状态布局 Build 对象
         */
        public Builder setLoadingLayout(@NonNull View loadingLayout) {
            this.loadingLayout = loadingLayout;
            return this;
        }

        /**
         * 设置默认加载中布局提示文本
         *
         * @param loadingText 加载中布局提示文本
         * @return 状态布局 Build 对象
         */
        public Builder setDefaultLoadingText(String loadingText) {
            this.loadingText = loadingText;
            return this;
        }

        /**
         * 设置默认加载中布局提示文本
         *
         * @param loadingTextStrID 加载中布局提示文本 ID
         * @return 状态布局 Build 对象
         */
        public Builder setDefaultLoadingText(@StringRes int loadingTextStrID) {
            this.loadingText = contentLayout.getContext().getResources().getString(loadingTextStrID);
            return this;
        }


        ///////////////////////////////////////////
        ////////////////空数据布局///////////////////
        ///////////////////////////////////////////

        /**
         * 设置空数据布局
         *
         * @param emptyLayoutResId 空数据布局 ID
         * @return 状态布局 Build 对象
         */
        public Builder setEmptyLayout(@LayoutRes int emptyLayoutResId) {
            this.emptyLayoutID = emptyLayoutResId;
            return this;
        }

        /**
         * 设置空数据布局
         *
         * @param emptyLayout 空数据布局
         * @return 状态布局 Build 对象
         */
        public Builder setEmptyLayout(@NonNull View emptyLayout) {
            this.emptyLayout = emptyLayout;
            return this;
        }

        /**
         * 设置空数据布局重试按钮 ID
         *
         * @param emptyRetryResId 空数据布局重试按钮 ID
         * @return 状态布局 Build 对象
         */
        public Builder setEmptyRetryID(@IdRes int emptyRetryResId) {
            this.emptyRetryViewId = emptyRetryResId;
            return this;
        }

        /**
         * 设置默认空数据布局重试按钮文本
         *
         * @param emptyRetryText 重试按钮文本
         * @return 状态布局 Build 对象
         */
        public Builder setDefaultEmptyRetryText(String emptyRetryText) {
            this.emptyRetryText = emptyRetryText;
            return this;
        }

        /**
         * 设置默认空数据布局重试按钮文本
         *
         * @param emptyRetryTextID 重试按钮文本 ID
         * @return 状态布局 Build 对象
         */
        public Builder setDefaultEmptyRetryText(@StringRes int emptyRetryTextID) {
            this.emptyRetryText = contentLayout.getContext().getResources().getString(emptyRetryTextID);
            return this;
        }

        /**
         * 设置默认空数据布局重试按钮文本颜色
         *
         * @param emptyRetryTextColor 重试按钮文本颜色
         * @return 状态布局 Build 对象
         */
        public Builder setDefaultEmptyRetryTextColor(int emptyRetryTextColor) {
            this.emptyRetryTextColor = emptyRetryTextColor;
            return this;
        }

        /**
         * 设置默认空数据布局重试按钮是否可见
         *
         * @param isEmptyRetryVisible true：可见 false：不可见
         * @return 状态布局 Build 对象
         */
        public Builder setDefaultEmptyRetryVisible(boolean isEmptyRetryVisible) {
            this.isEmptyRetryVisible = isEmptyRetryVisible;
            return this;
        }

        /**
         * 设置空数据布局图片
         *
         * @param emptyImgID 空数据布局图片 ID
         * @return 状态布局 Build 对象
         */
        public Builder setDefaultEmptyImg(@DrawableRes int emptyImgID) {
            this.emptyImgID = emptyImgID;
            return this;
        }

        ///////////////////////////////////////////
        /////////////////出错布局////////////////////
        ///////////////////////////////////////////

        /**
         * 设置空数据布局提示文本
         *
         * @param emptyText 空数据布局提示文本
         * @return 状态布局 Build 对象
         */
        public Builder setDefaultEmptyText(String emptyText) {
            this.emptyText = emptyText;
            return this;
        }

        /**
         * 设置空数据布局提示文本
         *
         * @param emptyTextStrID 空数据布局提示文本 ID
         * @return 状态布局 Build 对象
         */
        public Builder setDefaultEmptyText(@StringRes int emptyTextStrID) {
            this.emptyText = contentLayout.getContext().getResources().getString(emptyTextStrID);
            return this;
        }


        /**
         * 设置出错布局
         *
         * @param errorLayoutResId 出错布局 ID
         * @return 状态布局 Build 对象
         */
        public Builder setErrorLayout(@LayoutRes int errorLayoutResId) {
            this.errorLayoutID = errorLayoutResId;
            return this;
        }

        /**
         * 设置出错布局
         *
         * @param errorLayout 出错布局
         * @return 状态布局 Build 对象
         */
        public Builder setErrorLayout(@NonNull View errorLayout) {
            this.errorLayout = errorLayout;
            return this;
        }

        /**
         * 设置出错布局重试按钮 ID
         *
         * @param errorRetryResId 出错布局重试按钮 ID
         * @return 状态布局 Build 对象
         */
        public Builder setErrorRetryID(@IdRes int errorRetryResId) {
            this.errorRetryViewId = errorRetryResId;
            return this;
        }

        /**
         * 设置出错布局提示文本
         *
         * @param errorText 出错布局提示文本
         * @return 状态布局 Build 对象
         */
        public Builder setDefaultErrorText(String errorText) {
            this.errorText = errorText;
            return this;
        }

        /**
         * 设置出错布局提示文本
         *
         * @param errorTextStrID 出错布局提示文本 ID
         * @return 状态布局 Build 对象
         */
        public Builder setDefaultErrorText(@StringRes int errorTextStrID) {
            this.errorText = contentLayout.getContext().getResources().getString(errorTextStrID);
            return this;
        }

        /**
         * 设置默认出错布局重试按钮文本
         *
         * @param errorRetryText 重试按钮文本
         * @return 状态布局 Build 对象
         */
        public Builder setDefaultErrorRetryText(String errorRetryText) {
            this.errorRetryText = errorRetryText;
            return this;
        }

        /**
         * 设置默认出错布局重试按钮文本
         *
         * @param errorRetryTextID 重试按钮文本 ID
         * @return 状态布局 Build 对象
         */
        public Builder setDefaultErrorRetryText(@StringRes int errorRetryTextID) {
            this.errorRetryText = contentLayout.getContext().getResources().getString(errorRetryTextID);
            return this;
        }

        /**
         * 设置默认出错布局重试按钮文本颜色
         *
         * @param errorRetryTextColor 重试按钮文本颜色
         * @return 状态布局 Build 对象
         */
        public Builder setDefaultErrorRetryTextColor(int errorRetryTextColor) {
            this.errorRetryTextColor = errorRetryTextColor;
            return this;
        }

        /**
         * 设置出错布局重试按钮可见行
         *
         * @param isErrorRetryVisible true：可见 false：不可见
         * @return 状态布局 Build 对象
         */
        public Builder setDefaultErrorRetryVisible(boolean isErrorRetryVisible) {
            this.isErrorRetryVisible = isErrorRetryVisible;
            return this;
        }

        /**
         * 设置出错布局图片
         *
         * @param errorImgID 出错布局图片 ID
         * @return 状态布局 Build 对象
         */
        public Builder setDefaultErrorImg(@DrawableRes int errorImgID) {
            this.errorImgID = errorImgID;
            return this;
        }

        /**
         * 设置默认布局的背景颜色，包括加载中、空数据和出错布局
         *
         * @param defaultBackgroundColor 默认布局的背景颜色
         * @return 状态布局 Build 对象
         */
        public Builder setDefaultLayoutsBackgroundColor(int defaultBackgroundColor) {
            this.defaultBackgroundColor = defaultBackgroundColor;
            return this;
        }

        /**
         * 设置重试事件监听器
         *
         * @param listener 重试事件监听器
         * @return 状态布局 Build 对象
         */
        public Builder setOnRetryListener(OnRetryListener listener) {
            this.onRetryListener = listener;
            return this;
        }

        /**
         * 创建状态布局管理器
         *
         * @return 状态布局管理器
         */
        @NonNull
        public StatusLayoutManager build() {
            return new StatusLayoutManager(this);
        }

    }
}
