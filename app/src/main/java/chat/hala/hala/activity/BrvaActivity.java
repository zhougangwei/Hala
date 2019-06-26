/*
package com.hala.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import chat.hala.hala.R;
import HomeAdapter;

import java.util.ArrayList;
import java.util.List;

public class BrvaActivity extends AppCompatActivity {

    List<String> allDatas = new ArrayList<String>();
    List<String> modeDatas = new ArrayList<String>();
    private HomeAdapter homeAdapter;
    private int mCurrentCounter;
    private boolean isErr;
    private double TOTAL_COUNTER = 40;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private boolean isLoadMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brva);
        recyclerView = findViewById(R.id.rv);
        refreshLayout = findViewById(R.id.swipe_refresh);
        addDatas();
        for (int i = 21; i < 40; i++) {
            modeDatas.add("----" + i + "" + i + "" + "-----");
        }
        homeAdapter = new HomeAdapter(R.layout.simplestring, allDatas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(homeAdapter);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isLoadMore = false;
                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        addDatas();
                        homeAdapter.notifyDataSetChanged();
                        refreshLayout.setRefreshing(false);
                    }
                }, 200);
            }
        });







 homeAdapter.setUpFetchEnable(true);
        homeAdapter.setStartUpFetchPosition(2);
        homeAdapter.setUpFetchListener(new BaseQuickAdapter.UpFetchListener() {
            @Override
            public void onUpFetch() {
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startUpFetch();
                        homeAdapter.notifyDataSetChanged();
                    }
                }, 300);

            }
        });

        homeAdapter.setUpFetchListener(new BaseQuickAdapter.UpFetchListener() {
            @Override
            public void onUpFetch() {
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startUpFetch();
                    }
                },300);

            }
        });


        homeAdapter.setPreLoadNumber(5);

        homeAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mCurrentCounter >= TOTAL_COUNTER) {
                            //数据全部加载完毕
                            homeAdapter.loadMoreEnd();
                        } else {
                            if (!isErr) {
                                //成功获取更多数据
                                homeAdapter.addData(modeDatas);
                                mCurrentCounter = homeAdapter.getData().size();
                                homeAdapter.loadMoreComplete();
                            } else {
                                //获取更多数据失败
                                isErr = true;
                                homeAdapter.loadMoreFail();

                            }
                        }
                    }
                }, 300);
            }
        });

    }

    private void addDatas() {
        allDatas.clear();
        for (int i = 0; i < 20; i++) {
            allDatas.add("----" + i + "" + i + "" + "-----");
        }
    }

    private void startUpFetch() {
        homeAdapter.setUpFetching(true);
        addDatas();
    }
}
*/
