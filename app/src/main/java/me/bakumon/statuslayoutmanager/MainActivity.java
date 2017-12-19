package me.bakumon.statuslayoutmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

public class MainActivity extends AppCompatActivity {

    private StatusLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView lLRoot = findViewById(R.id.textview);

        manager = new StatusLayoutManager.Builder(lLRoot)
                // 自定义布局
//                .setLoadingLayout(R.layout.layout_loading)
//                .setEmptyLayout(R.layout.layout_empty)
//                .setErrorLayout(R.layout.layout_empty)
                .build();
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
        }
        return super.onOptionsItemSelected(item);
    }
}
