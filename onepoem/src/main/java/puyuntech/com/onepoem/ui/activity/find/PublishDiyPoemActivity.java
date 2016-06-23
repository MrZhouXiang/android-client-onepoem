package puyuntech.com.onepoem.ui.activity.find;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import puyuntech.com.onepoem.R;
import puyuntech.com.onepoem.app.ActivityBuilder.Impl.ActivityDirector;
import puyuntech.com.onepoem.model.EditMod;
import puyuntech.com.onepoem.ui.adapter.EditAdapter;

/**
 * 诗词发布
 */
@ContentView(R.layout.activity_publish_diy_poem)
public class PublishDiyPoemActivity extends ActivityDirector {

    @ViewInject(R.id.content_rv)
    RecyclerView content_rv;
    private EditAdapter mQuickAdapter;
    List<EditMod> list;


    @Event({R.id.img_add_iv})
    private void clickEvent(View view) {
        switch (view.getId()) {
            case R.id.img_add_iv:
                addOnePic();
                break;
            default:
                break;
        }
    }

    /**
     * 添加一张图片
     */
    private void addOnePic() {
        List<EditMod> list_one = new ArrayList<>();
        EditMod mod3 = new EditMod();
        mod3.setContent("http://110.173.23.108:8080/res/assets/img/model/91a724d6-5d87-4af7-b12a-c3d64fd6b91e.jpg");
        mod3.setItemType(EditMod.IMG);
        list_one.add(mod3);
        EditMod mod = new EditMod();
        mod.setContent("");
        mod.setItemType(EditMod.TEXT);
        list_one.add(mod);
        list.addAll(list_one);
        mQuickAdapter.addData(list_one);
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
        list = new ArrayList();
        EditMod mod = new EditMod();
        mod.setContent("");
        mod.setItemType(EditMod.HEADER);
        list.add(mod);
        EditMod mod2 = new EditMod();
        mod2.setContent("");
        mod2.setItemType(EditMod.TEXT);
        list.add(mod2);
        EditMod mod3 = new EditMod();
        mod3.setContent("http://110.173.23.108:8080/res/assets/img/model/91a724d6-5d87-4af7-b12a-c3d64fd6b91e.jpg");
        mod3.setItemType(EditMod.IMG);
        list.add(mod3);
        list.add(mod2);
        list.add(mod2);
        list.add(mod2);
        list.add(mod2);
        initAdapter();

    }

    @Override
    public void initView() {
        content_rv.setLayoutManager(new LinearLayoutManager(this));
        content_rv.setAdapter(mQuickAdapter);
        content_rv.setHasFixedSize(true);

    }

    @Override
    public void setViewClickListener() {
        mQuickAdapter.setOnRecyclerViewItemChildClickListener(new BaseQuickAdapter.OnRecyclerViewItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                EditMod mod = (EditMod) mQuickAdapter.getItem(position);
                switch (view.getId()) {
                    case R.id.del_iv:
                        list.remove(position);//删除数据
                        mQuickAdapter.remove(position);//删除UI
                        mQuickAdapter.notifyDataSetChanged();//跟新所有UI
                        break;
                }
            }
        });
    }


    @Override
    public void getDataLoc() {

    }

    @Override
    public void showTitle() {
        initTitle("发布诗文", R.mipmap.ic_return, "发布", -1);

    }

    @Override
    public void rightTextClick() {
        showShortToast("发布");
    }


    @Override
    public void showView() {
    }

    @Override
    public void getDataNet() {

    }

    private void initAdapter() {
        mQuickAdapter = new EditAdapter(this, list);
    }


}
