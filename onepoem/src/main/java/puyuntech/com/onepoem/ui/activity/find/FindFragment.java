package puyuntech.com.onepoem.ui.activity.find;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.nicodelee.utils.ListUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import puyuntech.com.onepoem.R;
import puyuntech.com.onepoem.app.ActivityBuilder.Impl.FragmentDirector;
import puyuntech.com.onepoem.app.AppDataUtils;

@ContentView(R.layout.fragment_find)
public class FindFragment extends FragmentDirector implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    @ViewInject(R.id.container)
    private ViewPager mViewPager;

    @ViewInject(R.id.tab_layout)
    private TabLayout tab_layout;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    List<String> titleList;

    public static FindFragment instantiation(int position) {
        FindFragment fragment = new FindFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (this.getView() != null)
            this.getView().setVisibility(menuVisible ? View.VISIBLE : View.GONE);
    }

    @Event({R.id.fab})
    private void clickEvent(View view) {
        switch (view.getId()) {
            case R.id.fab:
//                showShortToast("发布");
                skipIntent(PublishDiyPoemActivity.class, false);
                break;
            default:
                break;
        }
    }

    @Override
    public Object getValue(Enum type) {
        return null;
    }

    @Override
    public void updateUI(Object params, Enum type) {

    }

    @Override
    public void initData() {
        titleList = new ArrayList<>();
        for (int i = 0; i < ListUtils.getSize(AppDataUtils.dynasty); i++) {
            titleList.add(AppDataUtils.dynasty.get(i).getName());
        }
    }

    @Override
    public void initView() {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tab_layout.setupWithViewPager(mViewPager);
    }

    @Override
    public void setViewClickListener() {

    }

    @Override
    public void getDataLoc() {

    }

    @Override
    public void showTitle() {

    }

    @Override
    public void showView() {

    }

    @Override
    public void getDataNet() {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMoreRequested() {

    }

    @Override
    public void onNetChange() {

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return FindListFragment.instantiation(position);
        }

        @Override
        public int getCount() {
            return ListUtils.getSize(titleList);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }
    }
}
