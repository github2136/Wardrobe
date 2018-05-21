package com.github2136.wardrobe.base;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.github2136.base.BaseLoadMoreRecyclerAdapter;
import com.github2136.base.BaseRecyclerAdapter;
import com.github2136.wardrobe.R;
import com.github2136.wardrobe.base.mvp.BaseMVPPresenter;

import java.util.List;

/**
 * Created by yubin on 2017/7/18.
 */

public abstract class BaseListActivity<T, P extends BaseMVPPresenter> extends BaseActivity<P> {
    protected TextView tvTotalSum;
    protected RecyclerView rvContent;
    protected SwipeRefreshLayout srContent;
    protected int mPageNumber = 0;//页码
    protected boolean mHasItemClick = true;
    protected BaseLoadMoreRecyclerAdapter<T> mAdapter;

    @Override
    protected void initData(Bundle savedInstanceState) {
        srContent = (SwipeRefreshLayout) findViewById(R.id.sr_content);
        rvContent = (RecyclerView) findViewById(R.id.rv_content);
        tvTotalSum = (TextView) findViewById(R.id.tv_total);
        mAdapter = getAdapter();
        mAdapter.setOnLoadMoreListener(mOnLoadMoreListener);
        mAdapter.setEnableLoadMore(true);
        mAdapter.setFailedRefresh(true);
        srContent.setOnRefreshListener(mOnRefreshListener);
        getFirstPage();
        initListData(savedInstanceState);
        if (mHasItemClick) {
            mAdapter.setOnItemClickListener(mOnItemClickListener);
            mAdapter.setOnItemLongClickListener(mOnItemLongClickListener);
        }
    }

    /**
     * 获取第一页数据
     */
    protected void getFirstPage() {
        mPageNumber = 0;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                srContent.setRefreshing(true);
                mAdapter.setIsRefresh(true);
                getListData();

            }
        });
    }

    private BaseLoadMoreRecyclerAdapter.OnLoadMoreListener mOnLoadMoreListener = new BaseLoadMoreRecyclerAdapter.OnLoadMoreListener() {
        @Override
        public void onLoadMore() {
            srContent.setEnabled(false);
            getListData();
        }

        @Override
        public void onRefresh() {
            getFirstPage();
        }
    };
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mPageNumber = 0;
            mAdapter.setIsRefresh(true);
            getListData();
        }
    };

    private BaseRecyclerAdapter.OnItemClickListener mOnItemClickListener = new BaseRecyclerAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseRecyclerAdapter baseRecyclerAdapter, int i) {
            if (mAdapter.getHeadView() != null) {
                i--;
            }
            itemClick(mAdapter.getItem(i), i);
        }
    };
    private BaseRecyclerAdapter.OnItemLongClickListener mOnItemLongClickListener = new BaseRecyclerAdapter.OnItemLongClickListener() {
        @Override
        public void onItemClick(BaseRecyclerAdapter baseRecyclerAdapter, int i) {
            if (mAdapter.getHeadView() != null) {
                i--;
            }
            itemLongClick(mAdapter.getItem(i), i);
        }
    };

    protected void getDataSuccessful(List<T> list, int... total) {
        srContent.setRefreshing(false);
        srContent.setEnabled(true);
        mAdapter.setIsRefresh(false);
        if (mPageNumber == 0) {
            mAdapter.setFailed(false);
            rvContent.setAdapter(mAdapter);
            mAdapter.setData(null);
        }
        mAdapter.loadMoreSucceed(list);
        mPageNumber++;
    }

    protected void getDataFailure() {
        srContent.setRefreshing(false);
        srContent.setEnabled(true);
        mAdapter.setIsRefresh(false);
        if (mPageNumber == 0) {
            mAdapter.setData(null);
            mAdapter.setFailed(true);
            rvContent.setAdapter(mAdapter);
        } else {
            mAdapter.loadMoreFailed();
        }
    }

    protected abstract void initListData(Bundle savedInstanceState);

    protected abstract BaseLoadMoreRecyclerAdapter<T> getAdapter();

    protected abstract void getListData();

    protected void itemClick(T t, int position) { }

    protected void itemLongClick(T t, int position) { }
}