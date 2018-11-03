package me.bakumon.statuslayoutmanager;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

/**
 * 示例界面
 *
 * @author Bakumon https://bakumon.me
 * @date 2017/12/22
 */
public class MainActivity extends AppCompatActivity {

    private StatusLayoutManager statusLayoutManager;
    private LayoutInflater inflater;

    private RecyclerView recyclerView;
    private LinearLayout llRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llRoot = findViewById(R.id.ll_root);
        recyclerView = findViewById(R.id.rv_content);

        setupRecyclerView();

        setupStatusLayoutManager();

        statusLayoutManager.showLoadingLayout();
        getData(1500);
    }

    private void setupStatusLayoutManager() {
        statusLayoutManager = new StatusLayoutManager.Builder(recyclerView)

                // 设置默认布局属性
//                .setDefaultEmptyText("空白了，哈哈哈哈")
//                .setDefaultEmptyImg(R.mipmap.ic_launcher)
//                .setDefaultEmptyClickViewText("retry")
//                .setDefaultEmptyClickViewTextColor(getResources().getColor(R.color.colorAccent))
//                .setDefaultEmptyClickViewVisible(false)
//
//                .setDefaultErrorText(R.string.app_name)
//                .setDefaultErrorImg(R.mipmap.ic_launcher)
//                .setDefaultErrorClickViewText("重试一波")
//                .setDefaultErrorClickViewTextColor(getResources().getColor(R.color.colorPrimaryDark))
//                .setDefaultErrorClickViewVisible(true)
//
//                .setDefaultLayoutsBackgroundColor(Color.WHITE)

                // 自定义布局
//                .setLoadingLayout(inflate(R.layout.layout_loading))
//                .setEmptyLayout(inflate(R.layout.layout_empty))
//                .setErrorLayout(inflate(R.layout.layout_error))
//
//                .setLoadingLayout(R.layout.layout_loading)
//                .setEmptyLayout(R.layout.layout_empty)
//                .setErrorLayout(R.layout.layout_error)
//
//                .setEmptyClickViewID(R.id.tv_empty)
//                .setErrorClickViewID(R.id.tv_error)

                // 设置重试事件监听器
                .setOnStatusChildClickListener(new OnStatusChildClickListener() {
                    @Override
                    public void onEmptyChildClick(View view) {
                        Toast.makeText(MainActivity.this, R.string.reload_empty, Toast.LENGTH_SHORT).show();
                        statusLayoutManager.showLoadingLayout();
                        getData(1000);
                    }

                    @Override
                    public void onErrorChildClick(View view) {
                        Toast.makeText(MainActivity.this, R.string.reload_error, Toast.LENGTH_SHORT).show();
                        statusLayoutManager.showLoadingLayout();
                        getData(1000);
                    }

                    @Override
                    public void onCustomerChildClick(View view) {
                        if (view.getId() == R.id.tv_customer) {
                            Toast.makeText(MainActivity.this, R.string.request_access, Toast.LENGTH_SHORT).show();
                        } else if (view.getId() == R.id.tv_customer1) {
                            Toast.makeText(MainActivity.this, R.string.switch_account, Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .build();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
    }

    private void getData(long time) {
        llRoot.postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView.setAdapter(new SimpleRecyclerViewAdapter());
                statusLayoutManager.showSuccessLayout();
            }
        }, time);
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
                statusLayoutManager.showCustomLayout(R.layout.layout_custome, R.id.tv_customer, R.id.tv_customer1);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
