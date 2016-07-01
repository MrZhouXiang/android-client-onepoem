package puyuntech.com.onepoem.app;


import java.util.ArrayList;

import puyuntech.com.onepoem.model.DynastyMod;
import puyuntech.com.onepoem.model.TagMod;

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
    public static ArrayList<TagMod> tags = new ArrayList<TagMod>() {{
        add(new TagMod("0", "随笔"));
        add(new TagMod("1", "小清新"));
        add(new TagMod("2", "么么哒"));
        add(new TagMod("3", "励志"));
        add(new TagMod("4", "理想"));
        add(new TagMod("5", "夜色"));
    }};

}