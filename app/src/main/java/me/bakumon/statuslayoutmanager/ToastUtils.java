package me.bakumon.statuslayoutmanager;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast 工具类
 *
 * @author mafei
 * @date 2017/12/22
 */

public class ToastUtils {
    private static Toast mToast;

    public static void show(Context context, String message) {
        if (mToast == null) {
            mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(message);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }
}
