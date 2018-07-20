package com.example.ysq.autoloadrecyclerview;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.ysq.autoloadrecyclerview.recyclerview.ComplexAdapterBuilder;
import com.example.ysq.autoloadrecyclerview.recyclerview.loadmore.AutoLoadView;
import com.example.ysq.autoloadrecyclerview.recyclerview.loadmore.OnLoadListener;

public class MainActivity extends AppCompatActivity implements OnLoadListener {

    private MainAdapter mMainAdapter;
    private ComplexAdapterBuilder.ComplexAdapter mComplexAdapter;
    private AutoLoadView mAutoLoadView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAutoLoadView = new AutoLoadView(this, true);
        mAutoLoadView.setOnLoadListener(this);
        mMainAdapter = new MainAdapter();
        mComplexAdapter  = new ComplexAdapterBuilder(mMainAdapter).addFooter(mAutoLoadView).build();
        mComplexAdapter.setOnAutoLoadListener(mAutoLoadView.getOnAutoLoadListener());

        RecyclerView recyclerView = findViewById(R.id.recyleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(mComplexAdapter);


    }

    @Override
    public void onLoad() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mMainAdapter.add();
                mComplexAdapter.notifyDataSetChanged();
                mAutoLoadView.overLoad();
            }
        }, 2000);
    }
}
