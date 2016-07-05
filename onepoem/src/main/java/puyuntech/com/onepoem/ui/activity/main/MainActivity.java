package puyuntech.com.onepoem.ui.activity.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import puyuntech.com.onepoem.R;
import puyuntech.com.onepoem.app.ActivityBuilder.Impl.ActivityDirector;
import puyuntech.com.onepoem.ui.activity.find.FindFragment;
import puyuntech.com.onepoem.ui.activity.poem.PoemFragment;

@ContentView(R.layout.activity_main)
public class MainActivity  extends ActivityDirector {

    @ViewInject(R.id.tab_menu)
    RadioGroup myTabRg;
    @ViewInject(R.id.main_content)
    private FrameLayout mContainer;
    @ViewInject(R.id.tab_rl)
    RelativeLayout tab_rl;

    public void hideBottom() {
        tab_rl.setVisibility(View.GONE);
    }

    public void showBottom() {
        tab_rl.setVisibility(View.VISIBLE);
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

    }

    @Override
    public void initView() {
        Fragment fragment = (Fragment) mFragmentPagerAdapter
                .instantiateItem(mContainer, R.id.rbHome);
        mFragmentPagerAdapter.setPrimaryItem(mContainer, 0, fragment);
        mFragmentPagerAdapter.finishUpdate(mContainer);

        myTabRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                Fragment fragment = (Fragment) mFragmentPagerAdapter
                        .instantiateItem(mContainer, checkedId);
                mFragmentPagerAdapter.setPrimaryItem(mContainer, 0, fragment);
                mFragmentPagerAdapter.finishUpdate(mContainer);
            }
        });
    }
    private FragmentPagerAdapter mFragmentPagerAdapter = new FragmentPagerAdapter(
            getSupportFragmentManager()) {

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case R.id.rbHome:
                    return PoemFragment.instantiation(0);
                case R.id.rbFind:
                    return FindFragment.instantiation(1);
                case R.id.rbMe:
                    return PoemFragment.instantiation(2);
                default:
                    return PoemFragment.instantiation(0);
            }

        }

        @Override
        public int getCount() {
            return 3;
        }

    };
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
}
