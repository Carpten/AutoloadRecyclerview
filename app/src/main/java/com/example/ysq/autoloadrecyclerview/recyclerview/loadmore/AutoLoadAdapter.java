package com.example.ysq.autoloadrecyclerview.recyclerview.loadmore;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * @author: yangshuiqiang
 * Time:2017/12/31
 */

public abstract class AutoLoadAdapter extends RecyclerView.Adapter {

    private OnAutoLoadListener mOnAutoLoadListener;

    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        if (mOnAutoLoadListener != null) {
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    shouldLoad(recyclerView);
                }

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    shouldLoad(recyclerView);
                }
            });
        }
    }

    private void shouldLoad(RecyclerView recyclerView) {
        if (((LinearLayoutManager) recyclerView.getLayoutManager())
                .findLastVisibleItemPosition() >= getItemCount() - 5
                && getItemCount() >= Page.EVERY_PAGE_COUNT) {
            if (mOnAutoLoadListener != null) {
                mOnAutoLoadListener.autoLoad();
            }
        }
    }

    public void setOnAutoLoadListener(OnAutoLoadListener onAutoLoadListener) {
        mOnAutoLoadListener = onAutoLoadListener;
    }


    interface OnAutoLoadListener {
        void autoLoad();
    }
}