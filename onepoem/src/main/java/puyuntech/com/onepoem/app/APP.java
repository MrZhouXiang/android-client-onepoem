package puyuntech.com.onepoem.app;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import puyuntech.com.onepoem.utils.NetWorkUtils;

import org.xutils.x;

import puyuntech.com.onepoem.utils.DBUtilsX;

/**
 * @作者 周翔
 * @创建时间 2016/2/5 0005
 * @描述
 **/
public class APP extends Application {
    private static APP app;
    public static int mNetWorkState;

    /**
     * 全局application对象
     *
     * @return
     */
    public static synchronized APP getInstance() {
        return app;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        initData();
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public void initData() {
        //xUtils初始化
        x.Ext.init(this);
        //初始化数据库管理对象
        DBUtilsX.init();
        //初始化网路监听
        mNetWorkState = NetWorkUtils.getNetworkState(this);

    }

}
