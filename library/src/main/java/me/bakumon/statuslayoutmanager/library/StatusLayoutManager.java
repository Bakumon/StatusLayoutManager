package me.bakumon.statuslayoutmanager.library;

import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 状态布局管理器
 * https://bakumon.me
 *
 * @author Bakumon
 * @date 2017/12/18
 */

public class StatusLayoutManager {

    private View contentLayout;

    @LayoutRes
    private int loadingLayoutID;
    private View loadingLayout;
    private String loadingText;

    @IdRes
    private int emptyClickViewId;
    @LayoutRes
    private int emptyLayoutID;
    private View emptyLayout;
    private String emptyText;
    private String emptyClickViewText;
    private int emptyClickViewTextColor;
    private boolean isEmptyClickViewVisible;
    @DrawableRes
    private int emptyImgID;

    @IdRes
    private int errorClickViewId;
    @LayoutRes
    private int errorLayoutID;
    private View errorLayout;
    private String errorText;
    private String errorClickViewText;
    private int errorClickViewTextColor;
    private boolean isErrorClickViewVisible;
    @DrawableRes
    private int errorImgID;

    private int defaultBackgroundColor;

    private OnStatusChildClickListener onStatusChildClickListener;

    private ReplaceLayoutHelper replaceLayoutHelper;

    private LayoutInflater inflater;

    private StatusLayoutManager(Builder builder) {
        this.contentLayout = builder.contentLayout;

        this.loadingLayoutID = builder.loadingLayoutID;
        this.loadingLayout = builder.loadingLayout;
        this.loadingText = builder.loadingText;

        this.emptyClickViewId = builder.emptyClickViewId;
        this.emptyLayoutID = builder.emptyLayoutID;
        this.emptyLayout = builder.emptyLayout;
        this.emptyText = builder.emptyText;
        this.emptyClickViewText = builder.emptyClickViewText;
        this.emptyClickViewTextColor = builder.emptyClickViewTextColor;
        this.isEmptyClickViewVisible = builder.isEmptyClickViewVisible;
        this.emptyImgID = builder.emptyImgID;

        this.errorClickViewId = builder.errorClickViewId;
        this.errorLayoutID = builder.errorLayoutID;
        this.errorLayout = builder.errorLayout;
        this.errorText = builder.errorText;
        this.errorClickViewText = builder.errorClickViewText;
        this.errorClickViewTextColor = builder.errorClickViewTextColor;
        this.isErrorClickViewVisible = builder.isErrorClickViewVisible;
        this.errorImgID = builder.errorImgID;

        this.defaultBackgroundColor = builder.defaultBackgroundColor;

        this.onStatusChildClickListener = builder.onStatusChildClickListener;

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

        if (onStatusChildClickListener == null) {
            return;
        }

        View view = emptyLayout.findViewById(emptyClickViewId);
        if (view == null) {
            return;
        }

        // 设置点击按钮点击时事件回调
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onStatusChildClickListener.onEmptyChildClick(view);
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

        TextView emptyClickViewTextView = emptyLayout.findViewById(R.id.bt_status_empty_click);
        if (emptyClickViewTextView == null) {
            return;
        }
        // 设置点击按钮的文本和可见性
        if (isEmptyClickViewVisible) {
            emptyClickViewTextView.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(emptyClickViewText)) {
                emptyClickViewTextView.setText(emptyClickViewText);
            }
            emptyClickViewTextView.setTextColor(emptyClickViewTextColor);
        } else {
            emptyClickViewTextView.setVisibility(View.GONE);
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

        if (onStatusChildClickListener == null) {
            return;
        }

        View view = errorLayout.findViewById(errorClickViewId);
        if (view == null) {
            return;
        }

        // 设置点击按钮点击时事件回调
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onStatusChildClickListener.onErrorChildClick(view);
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

        TextView errorClickViewTextView = errorLayout.findViewById(R.id.bt_status_error_click);
        if (errorClickViewTextView == null) {
            return;
        }
        // 设置点击按钮的文本和可见性
        if (isErrorClickViewVisible) {
            errorClickViewTextView.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(errorClickViewText)) {
                errorClickViewTextView.setText(errorClickViewText);
            }
            errorClickViewTextView.setTextColor(errorClickViewTextColor);
        } else {
            errorClickViewTextView.setVisibility(View.GONE);
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
     * @param clickViewID  可点击 View ID
     */
    public void showCustomLayout(@NonNull View customLayout, @IdRes int... clickViewID) {
        replaceLayoutHelper.showStatusLayout(customLayout);
        if (onStatusChildClickListener == null) {
            return;
        }

        for (int aClickViewID : clickViewID) {
            View clickView = customLayout.findViewById(aClickViewID);
            if (clickView == null) {
                return;
            }

            // 设置点击按钮点击时事件回调
            clickView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onStatusChildClickListener.onCustomerChildClick(view);
                }
            });
        }
    }

    /**
     * 显示自定义状态布局
     *
     * @param customLayoutID 自定义布局 ID
     * @param clickViewID    点击按钮 ID
     */
    public View showCustomLayout(@LayoutRes int customLayoutID, @IdRes int... clickViewID) {
        View customLayout = inflate(customLayoutID);
        showCustomLayout(customLayout, clickViewID);
        return customLayout;
    }

    public static final class Builder {

        private View contentLayout;

        @LayoutRes
        private int loadingLayoutID;
        private View loadingLayout;
        private String loadingText;

        @IdRes
        private int emptyClickViewId;
        @LayoutRes
        private int emptyLayoutID;
        private View emptyLayout;
        private String emptyText;
        private String emptyClickViewText;
        private int emptyClickViewTextColor;
        private boolean isEmptyClickViewVisible;
        @DrawableRes
        private int emptyImgID;

        @IdRes
        private int errorClickViewId;
        @LayoutRes
        private int errorLayoutID;
        private View errorLayout;
        private String errorText;
        private String errorClickViewText;
        private int errorClickViewTextColor;
        private boolean isErrorClickViewVisible;
        @DrawableRes
        private int errorImgID;

        private int defaultBackgroundColor;

        private OnStatusChildClickListener onStatusChildClickListener;

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
            // 设置默认点击点击view id
            this.emptyClickViewId = R.id.bt_status_empty_click;
            this.errorClickViewId = R.id.bt_status_error_click;
            // 设置默认点击按钮属性
            this.isEmptyClickViewVisible = true;
            this.emptyClickViewTextColor = contentLayout.getContext().getResources().getColor(R.color.status_layout_click_view_text_color);
            this.isErrorClickViewVisible = true;
            this.errorClickViewTextColor = contentLayout.getContext().getResources().getColor(R.color.status_layout_click_view_text_color);
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
         * 设置空数据布局点击按钮 ID
         *
         * @param emptyClickViewResId 空数据布局点击按钮 ID
         * @return 状态布局 Build 对象
         */
        public Builder setEmptyClickViewID(@IdRes int emptyClickViewResId) {
            this.emptyClickViewId = emptyClickViewResId;
            return this;
        }

        /**
         * 设置默认空数据布局点击按钮文本
         *
         * @param emptyClickViewText 点击按钮文本
         * @return 状态布局 Build 对象
         */
        public Builder setDefaultEmptyClickViewText(String emptyClickViewText) {
            this.emptyClickViewText = emptyClickViewText;
            return this;
        }

        /**
         * 设置默认空数据布局点击按钮文本
         *
         * @param emptyClickViewTextID 点击按钮文本 ID
         * @return 状态布局 Build 对象
         */
        public Builder setDefaultEmptyClickViewText(@StringRes int emptyClickViewTextID) {
            this.emptyClickViewText = contentLayout.getContext().getResources().getString(emptyClickViewTextID);
            return this;
        }

        /**
         * 设置默认空数据布局点击按钮文本颜色
         *
         * @param emptyClickViewTextColor 点击按钮文本颜色
         * @return 状态布局 Build 对象
         */
        public Builder setDefaultEmptyClickViewTextColor(int emptyClickViewTextColor) {
            this.emptyClickViewTextColor = emptyClickViewTextColor;
            return this;
        }

        /**
         * 设置默认空数据布局点击按钮是否可见
         *
         * @param isEmptyClickViewVisible true：可见 false：不可见
         * @return 状态布局 Build 对象
         */
        public Builder setDefaultEmptyClickViewVisible(boolean isEmptyClickViewVisible) {
            this.isEmptyClickViewVisible = isEmptyClickViewVisible;
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
         * 设置出错布局点击按钮 ID
         *
         * @param errorClickViewResId 出错布局点击按钮 ID
         * @return 状态布局 Build 对象
         */
        public Builder setErrorClickViewID(@IdRes int errorClickViewResId) {
            this.errorClickViewId = errorClickViewResId;
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
         * 设置默认出错布局点击按钮文本
         *
         * @param errorClickViewText 点击按钮文本
         * @return 状态布局 Build 对象
         */
        public Builder setDefaultErrorClickViewText(String errorClickViewText) {
            this.errorClickViewText = errorClickViewText;
            return this;
        }

        /**
         * 设置默认出错布局点击按钮文本
         *
         * @param errorClickViewTextID 点击按钮文本 ID
         * @return 状态布局 Build 对象
         */
        public Builder setDefaultErrorClickViewText(@StringRes int errorClickViewTextID) {
            this.errorClickViewText = contentLayout.getContext().getResources().getString(errorClickViewTextID);
            return this;
        }

        /**
         * 设置默认出错布局点击按钮文本颜色
         *
         * @param errorClickViewTextColor 点击按钮文本颜色
         * @return 状态布局 Build 对象
         */
        public Builder setDefaultErrorClickViewTextColor(int errorClickViewTextColor) {
            this.errorClickViewTextColor = errorClickViewTextColor;
            return this;
        }

        /**
         * 设置出错布局点击按钮可见行
         *
         * @param isErrorClickViewVisible true：可见 false：不可见
         * @return 状态布局 Build 对象
         */
        public Builder setDefaultErrorClickViewVisible(boolean isErrorClickViewVisible) {
            this.isErrorClickViewVisible = isErrorClickViewVisible;
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
         * 设置点击事件监听器
         *
         * @param listener 点击事件监听器
         * @return 状态布局 Build 对象
         */
        public Builder setOnStatusChildClickListener(OnStatusChildClickListener listener) {
            this.onStatusChildClickListener = listener;
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
