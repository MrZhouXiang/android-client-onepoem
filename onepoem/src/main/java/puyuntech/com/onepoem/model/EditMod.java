package puyuntech.com.onepoem.model;


import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * 诗词对象
 *
 * @author Administrator
 */
public class EditMod extends MultiItemEntity implements Serializable {
    public static final int TEXT = 1;//文本编辑
    public static final int IMG = 2;//图片展示
    public static final int HEADER = 3;//头部
    public static EditMod MOD_HEADER = new EditMod("", EditMod.HEADER);
    public static EditMod MOD_TEXT = new EditMod("", EditMod.TEXT);

    private String content;

    public EditMod(String s, int header) {
        setContent(s);
        setItemType(header);
    }

    public EditMod() {

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
