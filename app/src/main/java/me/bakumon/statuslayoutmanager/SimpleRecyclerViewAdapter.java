package me.bakumon.statuslayoutmanager;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * 列表适配器
 *
 * @author Bakumon
 * @date 2017/12/22
 */

public class SimpleRecyclerViewAdapter extends RecyclerView.Adapter<SimpleRecyclerViewAdapter.ViewHolder> {

    private List<SimpleData.Song> mSongs;

    static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView mImageView;
        final TextView mTextView;

        ViewHolder(View view) {
            super(view);
            mImageView = view.findViewById(R.id.avatar);
            mTextView = view.findViewById(android.R.id.text1);
        }
    }

    public SimpleRecyclerViewAdapter() {
        mSongs = SimpleData.getRandomSonList(30);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mTextView.setText(mSongs.get(position).songName);
        Glide.with(holder.mImageView.getContext())
                .load(mSongs.get(position).drawableResID)
                .fitCenter()
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }
}
