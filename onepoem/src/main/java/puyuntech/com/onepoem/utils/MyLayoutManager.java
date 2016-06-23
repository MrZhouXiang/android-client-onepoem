package puyuntech.com.onepoem.utils;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @作者 Administrator
 * @创建时间 2016-06-23 下午 16:39
 * @描述 MyLayoutManager
 * @修改时间 2016-06-23 下午 16:39
 * @修改描述
 * @修改者 Administrator
 **/
public class MyLayoutManager extends LinearLayoutManager {

    public MyLayoutManager(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }


    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {

        super.onMeasure(recycler, state, widthSpec, heightSpec);
    }
}
