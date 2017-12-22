package me.bakumon.statuslayoutmanager;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import me.bakumon.statuslayoutmanager.library.OnRetryListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

/**
 * @author Bakumon
 */
public class MainActivity extends AppCompatActivity {

    private StatusLayoutManager statusLayoutManager;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView contextView = findViewById(R.id.textview);

        statusLayoutManager = new StatusLayoutManager.Builder(contextView)
                // 自定义布局
//                .setLoadingLayout(inflate(R.layout.layout_loading))
//                .setEmptyLayout(inflate(R.layout.layout_empty))
//                .setErrorLayout(inflate(R.layout.layout_error))

//                .setLoadingLayout(R.layout.layout_loading)
//                .setEmptyLayout(R.layout.layout_empty)
//                .setErrorLayout(R.layout.layout_error)

//                .setEmptyRetryID(R.id.tv_empty)
//                .setErrorRetryID(R.id.tv_error)

//                .setDefaultEmptyText("空白了，哈哈哈哈")
//                .setDefaultEmptyImg(R.mipmap.ic_launcher)
//                .setDefaultErrorImg(R.mipmap.ic_launcher)
//                .setDefaultErrorText(R.string.app_name)
//                .setDefaultEmptyRetryText("retry")
//                .setDefaultEmptyRetryTextColor(getResources().getColor(R.color.colorAccent))
                .setDefaultEmptyRetryVisible(false)

                .setDefaultErrorRetryText("重试一波")
                .setDefaultErrorRetryTextColor(getResources().getColor(R.color.colorAccent))
                .setDefaultErrorRetryVisible(true)

                .setDefaultLayoutsBackgroundColor(Color.WHITE)

                .setOnRetryListener(new OnRetryListener() {
                    @Override
                    public void onClick(int state, View view) {
                        Toast.makeText(MainActivity.this, "state=" + state + ",id=" + view.getId(), Toast.LENGTH_SHORT).show();
                    }
                })
                .build();
    }

    private View inflate(@LayoutRes int resource) {
        if (inflater == null) {
            inflater = LayoutInflater.from(this);
        }
        return inflater.inflate(resource, null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.status, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_status_loading:
                // 加载中
                statusLayoutManager.showLoadingLayout();
                break;
            case R.id.menu_status_empty:
                // 空数据
                statusLayoutManager.showEmptyLayout();
                break;
            case R.id.menu_status_error:
                // 加载失败
                statusLayoutManager.showErrorLayout();
                break;
            case R.id.menu_status_success:
                // 加载成功，显示原布局
                statusLayoutManager.showSuccessLayout();
                break;
            case R.id.menu_status_customer:
                // 自定义状态布局
                statusLayoutManager.showCustomLayout(R.layout.layout_custome);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
