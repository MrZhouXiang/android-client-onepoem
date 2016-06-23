package puyuntech.com.onepoem.app;


import java.util.ArrayList;

import puyuntech.com.onepoem.model.DynastyMod;

/**
 * 作者：zx
 * 时间：2016-01-21 下午 16:50
 * 描述：
 */
public class AppDataUtils {
    //文件保存路径
    public static boolean isDeBug = true;
    public final static int pageSize = 10;
    public static ArrayList<DynastyMod> dynasty = new ArrayList<DynastyMod>() {{
        add(new DynastyMod("0", "全部"));
        add(new DynastyMod("1", "唐"));
        add(new DynastyMod("2", "宋"));
        add(new DynastyMod("3", "元"));
        add(new DynastyMod("4", "明"));
        add(new DynastyMod("5", "清"));
    }};


}