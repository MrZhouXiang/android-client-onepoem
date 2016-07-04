package puyuntech.com.onepoem.ui.activity.find;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.nicodelee.utils.ListUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.List;

import puyuntech.com.onepoem.R;
import puyuntech.com.onepoem.app.ActivityBuilder.Impl.FragmentDirector;
import puyuntech.com.onepoem.app.AppDataUtils;
import puyuntech.com.onepoem.app.BaseAct;
import puyuntech.com.onepoem.model.DiyPoemMod;
import puyuntech.com.onepoem.presenter.find.PublishDiyPoemPresenter;
import puyuntech.com.onepoem.ui.adapter.DiyPoemFragmentAdapter;

@ContentView(R.layout.fragment_find_list)
public class FindListFragment extends FragmentDirector implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    public static FindListFragment instantiation(int position) {
        FindListFragment fragment = new FindListFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }


    @ViewInject(R.id.swipe_container)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @ViewInject(R.id.content_rv)
    RecyclerView mRecyclerView;

    private DiyPoemFragmentAdapter mQuickAdapter;
    private int delayMillis = 1000;
    private int mCurrentCounter = 0;
    private int position = 0;


    @Override
    public Object getValue(Enum type) {
        PublishDiyPoemPresenter.ValueGetType type1 = (PublishDiyPoemPresenter.ValueGetType) type;
        switch (type1) {
            case CURRENT_PAGE_SIZE:
                //获取的值
                return ListUtils.getSize(mQuickAdapter.getData());
            case GET_LAST:
                //获取的值
                return ListUtils.isEmpty(mQuickAdapter.getData()) ? null : mQuickAdapter.getData().get(ListUtils.getSize(mQuickAdapter.getData()) - 1);
            case GET_TAG:
                //获取朝代ID
                return AppDataUtils.tags.get(position).getName();
            default:
                break;
        }
        return null;
    }

    @Override
    public void updateUI(final Object params, Enum type) {
        PublishDiyPoemPresenter.UpdateUIType type1 = (PublishDiyPoemPresenter.UpdateUIType) type;
        switch (type1) {
            case LOAD_MORE:
                //加载更多
                mRecyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        List l = (List) params;
                        refreshPage(LOAD_MORE_FLAG, l, mQuickAdapter, mSwipeRefreshLayout, mRecyclerView);
                    }
                });
                break;
            case REFRESH:
                //刷新
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        if (((BaseAct) getActivity()).loadingDialog != null) {
                            ((BaseAct) getActivity()).loadingDialog.closeDialog();
                        }
                        List l = (List) params;
                        refreshPage(REFRESH_FLAG, l, mQuickAdapter, mSwipeRefreshLayout, mRecyclerView);
                    }
                });
                break;
            default:
                break;
        }
    }

    @Override
    public void initData() {
        mPresenter = new PublishDiyPoemPresenter(this);
        initAdapter();
    }

    @Override
    public void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mQuickAdapter);
    }

    @Override
    public void setViewClickListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mQuickAdapter.setOnLoadMoreListener(this);
        mQuickAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                DiyPoemMod mod = (DiyPoemMod) mQuickAdapter.getData().get(position);
                HashMap<String, Object> map = new HashMap();
                map.put("model", mod);
                skipIntent(DiyPoemDetailActivity.class, map, false);

            }
        });
    }

    @Override
    public void getDataLoc() {
        position = getArguments().getInt("position");
    }

    @Override
    public void showTitle() {
//        initTitle("刷新和分页", true);

    }

    @Override
    public void showView() {

    }

    @Override
    public void getDataNet() {
//        ((BaseAct) getActivity()).loadingDialog.showDialog();
        ((PublishDiyPoemPresenter) mPresenter).refresh();

    }


    @Override
    public void onNetChange() {

    }

    @Override
    public void onRefresh() {
        //刷新
        ((PublishDiyPoemPresenter) mPresenter).refresh();

    }

    @Override
    public void onLoadMoreRequested() {
        //加载更多
        ((PublishDiyPoemPresenter) mPresenter).loadMore();
    }


    private void initAdapter() {
        mQuickAdapter = new DiyPoemFragmentAdapter(getActivity());
        mQuickAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mCurrentCounter = mQuickAdapter.getItemCount();
        mQuickAdapter.openLoadMore(pageSize, true);//or call mQuickAdapter.setPageSize(PAGE_SIZE);  mQuickAdapter.openLoadMore(true);
//        addHeadView();
//        setEmpty(mQuickAdapter, mRecyclerView);
    }


}
