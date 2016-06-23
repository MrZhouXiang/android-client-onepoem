package puyuntech.com.onepoem.app.ActivityBuilder.Impl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.nicodelee.utils.ListUtils;
import com.nicodelee.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import puyuntech.com.onepoem.R;
import puyuntech.com.onepoem.app.ActivityBuilder.BuildHelper;
import puyuntech.com.onepoem.app.AppDataUtils;
import puyuntech.com.onepoem.app.BaseFragment;
import puyuntech.com.onepoem.presenter.IUpdateUIListener;
import puyuntech.com.onepoem.app.ActivityBuilder.InitBuilder;
import puyuntech.com.onepoem.http.httpContor.base.HttpAfterExpand;
import puyuntech.com.onepoem.presenter.BasePresenter;


/**
 * 作者 Administrator
 * 创建时间 2016/4/25 0025
 * 描述 抽象Fragment【导演者】类，所有Fragment需要继承他
 * 修改时间 2016/4/25 0025
 * 修改描述 实现InitBulder
 * 修改者 Administrator
 **/
public abstract class FragmentDirector extends BaseFragment implements BuildHelper, IUpdateUIListener {
    public static final int REFRESH_FLAG = 0;//刷新
    public static final int LOAD_MORE_FLAG = 1;//更多


    private HttpAfterExpand afterExpand;
    private int pageFlag;//分页标示 0：刷新 1：更多
    private List pageMods;//分页数据
    private InitBuilder initBuilder;//初始化建造者
    protected boolean isHasMore = true;
    protected int pageSize = AppDataUtils.pageSize;
    protected BasePresenter mPresenter;//主导器

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initBuilder = new InitBuilderImpl();
        initBuilder.initFragment(this);//初始化方式2:初始化一个Fragment
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //返回建设置事件
                homeBack();
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    /**
     * @param loadType     刷新or加载更多
     * @param getList      加载数据
     * @param adapter      需要刷新的adapter
     * @param mSwipeLayout loading界面
     */
    @Override
    public void refreshPage(int loadType, List getList, BaseQuickAdapter adapter, SwipeRefreshLayout mSwipeLayout,RecyclerView recyclerView) {
        if (loadType == REFRESH_FLAG) {
            isHasMore = true;
            if (ListUtils.isEmpty(getList)) {//无数据
                isHasMore = false;
                adapter.setNewData(new ArrayList());
                adapter.notifyDataChangedAfterLoadMore(false);
                setEmpty(adapter, recyclerView);
            } else if (ListUtils.getSize(getList) < pageSize) {//当前页有数据，没有更多
                isHasMore = false;
                adapter.setNewData(getList);
                adapter.openLoadMore(pageSize, true);
                adapter.notifyDataChangedAfterLoadMore(false);
            } else {//有更多数据
                adapter.setNewData(getList);
                adapter.openLoadMore(pageSize, true);
            }
        } else if (loadType == LOAD_MORE_FLAG) {
            if (ListUtils.isEmpty(getList)) {
//                showToast("全部加载完毕");
                isHasMore = false;
                adapter.notifyDataChangedAfterLoadMore(false);
            } else {
                //加载更多
                adapter.notifyDataChangedAfterLoadMore(getList, true);
                //全部加载完毕
                if (ListUtils.getSize(getList) < pageSize) {
//                    showToast("全部加载完毕");
                    isHasMore = false;
                    adapter.notifyDataChangedAfterLoadMore(false);
                }
            }

        }
        if (mSwipeLayout != null)
            mSwipeLayout.setRefreshing(false);
    }
    protected void setEmpty(BaseQuickAdapter adapter, RecyclerView recyclerView) {
        View emptyView = getActivity().getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) recyclerView.getParent(), false);
        adapter.setEmptyView(emptyView);
        recyclerView.setAdapter(adapter);
    }
    /**
     * 初始化头部
     *
     * @param title
     * @param showLeft 是否显示左边按钮
     */
    @Override
    public void initTitle(String title, boolean showLeft) {
        //标题展示
//        initTitle(title, R.mipmap.ic_return,null, -1);
    }
    /**
     * 自定义左边按钮
     *
     * @param title
     * @param leftId
     * @param rightStr
     * @param rightId
     */
    @Override
    public void initTitle(String title, int leftId, String rightStr, int rightId) {


    }

    @Override
    public void rightImgClick() {

    }

    @Override
    public void rightTextClick() {

    }


    @Override
    public void leftImgClick() {

    }

    @Override
    public void homeBack() {
    }
}
