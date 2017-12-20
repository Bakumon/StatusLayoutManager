package me.bakumon.statuslayoutmanager;

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

    private StatusLayoutManager manager;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView lLRoot = findViewById(R.id.textview);

        manager = new StatusLayoutManager.Builder(lLRoot)
                // 自定义布局
//                .setLoadingLayout(inflate(R.layout.layout_loading))
//                .setEmptyLayout(inflate(R.layout.layout_empty))
//                .setErrorLayout(inflate(R.layout.layout_error))

                .setLoadingLayout(R.layout.layout_loading)
//                .setEmptyLayout(R.layout.layout_empty)
                .setErrorLayout(R.layout.layout_error)
//                .setEmptyRetryID(R.id.tv_empty)
                .setErrorRetryID(R.id.tv_error)
                .setOnRetryListener(new OnRetryListener() {
                    @Override
                    public void onClick(View view, int state) {
                        String stateStr;
                        if (state == StatusLayoutManager.STATE_EMPTY) {
                            stateStr = "空数据状态";
                        } else if (state == StatusLayoutManager.STATE_ERROR) {
                            stateStr = "出错状态";
                        } else {
                            stateStr = "其他状态";
                        }
                        Toast.makeText(MainActivity.this, "state=" + stateStr + ",id=" + view.getId(), Toast.LENGTH_SHORT).show();
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
                manager.showLoadingLayout();
                break;
            case R.id.menu_status_empty:
                // 空数据
                manager.showEmptyLayout();
                break;
            case R.id.menu_status_error:
                // 加载失败
                manager.showErrorLayout();
                break;
            case R.id.menu_status_success:
                // 加载成功
                manager.showSuccessLayout();
                break;
            case R.id.menu_status_customer:
                // 自定义状态布局
                manager.showCustomLayout(R.layout.layout_custome);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
