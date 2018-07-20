package com.example.ysq.autoloadrecyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ysq.autoloadrecyclerview.recyclerview.loadmore.Page;

public class MainAdapter extends RecyclerView.Adapter {

    private RecyclerView mRecyclerView;

    private int mCount = 1;

    public void add() {
        mCount++;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        super.onAttachedToRecyclerView(recyclerView);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MainVH(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_main, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return mCount * Page.EVERY_PAGE_COUNT;
    }

    private static class MainVH extends RecyclerView.ViewHolder {

        public MainVH(@NonNull View itemView) {
            super(itemView);
        }
    }
}
