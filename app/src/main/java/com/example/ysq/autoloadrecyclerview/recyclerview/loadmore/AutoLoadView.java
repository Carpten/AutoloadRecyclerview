package com.example.ysq.autoloadrecyclerview.recyclerview.loadmore;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.example.ysq.autoloadrecyclerview.MeasureUtils;
import com.example.ysq.autoloadrecyclerview.R;

import java.util.List;


/**
 * @author: yangshuiqiang
 * Time:2017/11/26 20:46
 */

public class AutoLoadView extends FrameLayout implements View.OnClickListener {

    private TextView mTextView;
    private int mStatus;
    private int mIndex = 1;

    private OnLoadListener onLoadListener;

    private boolean hideAfterOver = false;

    private boolean isHidding = false;

    /**
     * @param hideAfterOver 加载完成是否隐藏autoloadview
     */
    public AutoLoadView(@NonNull Context context, boolean hideAfterOver) {
        this(context, null);
        this.hideAfterOver = hideAfterOver;
    }

    public AutoLoadView(@NonNull Context context) {
        this(context, null);
    }

    public AutoLoadView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoLoadView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, MeasureUtils.dp2px(getContext(), 40)));
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_loadview, this, false);
        mTextView = (TextView) inflate.findViewById(R.id.tv_status);
        FrameLayout.LayoutParams layoutParams =
                new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        addView(inflate, layoutParams);
        setOnClickListener(this);
        setStatus(Status.STATUS_NORMAL);
    }

    public void setStatus(int status) {
        if (status < 0 || status > 3)
            throw new RuntimeException("setStatus for error code");
        this.mStatus = status;
        updateUI();
    }

    public void setOnLoadListener(OnLoadListener onLoadListener) {
        this.onLoadListener = onLoadListener;
    }

    public int getIndex() {
        return mIndex;
    }

    public void addIndex() {
        mIndex++;
    }

    public void setRefreshIndex() {
        mIndex = 2;
        setStatus(Status.STATUS_NORMAL);
    }

    public void shouldOver(List<?> objects) {
        if (objects == null || objects.size() < Page.EVERY_PAGE_COUNT) {
            setStatus(Status.STATUS_OVER);
        }
    }

    public void load() {
        if (onLoadListener != null) {
            onLoadListener.onLoad();
        }
    }

    public void requestLoad() {
        if (mStatus == Status.STATUS_NORMAL) {
            setStatus(Status.STATUS_LOAD);
            load();
        }
    }

    public AutoLoadAdapter.OnAutoLoadListener getOnAutoLoadListener() {
        return new AutoLoadAdapter.OnAutoLoadListener() {
            @Override
            public void autoLoad() {
                requestLoad();
            }
        };
    }


    @Override
    public void onClick(View view) {
        if (mStatus == Status.STATUS_ERROR) {
            setStatus(Status.STATUS_LOAD);
            load();
        }
    }


    private void updateUI() {
        mTextView.setText(getResources().getStringArray(R.array.loadview_status)[mStatus]);
        if (mStatus == Status.STATUS_OVER && hideAfterOver && !isHidding) {
            setHeight(0);
            isHidding = true;
        } else if (mStatus != Status.STATUS_OVER && hideAfterOver && isHidding) {
            setHeight(40);
            isHidding = false;
        }
    }

    private void setHeight(int dpValue) {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = MeasureUtils.dp2px(getContext(), dpValue);
        setLayoutParams(layoutParams);
    }

    public void loadFail(){
        setStatus(Status.STATUS_ERROR);
    }


    public void startLoad(){
        setStatus(Status.STATUS_LOAD);
    }

    public void overLoad(){
        if (mStatus == Status.STATUS_LOAD) {
            addIndex();
            setStatus(Status.STATUS_NORMAL);
        }
    }

}
