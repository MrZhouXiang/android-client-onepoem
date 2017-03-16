package puyuntech.com.onepoem.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nicodelee.utils.JsonUtils;
import com.nicodelee.utils.ListUtils;
import com.nicodelee.utils.StringUtils;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 诗词对象
 *
 * @author Administrator
 */
@JsonIgnoreProperties(ignoreUnknown = true)//可忽略多余字段
public class DiyPoemMod implements Serializable {
    private String id; // 主键
    private String user_id; // 外键 诗人id
    private String title; // 标题
    private String content; // 内容
    private String url; // 图片地址
    private String is_publish; //是否公开
    private Date creat_time;// 发布时间
    private String tag; //标签
    private String pen_name; //作者笔名
    private int type; //类型0:普通诗词，只有一张图片	1:图文混编诗词，content存储的是json
    private int status; //状态

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Date getCreat_time() {
        return creat_time;
    }

    public void setCreat_time(Date creat_time) {
        this.creat_time = creat_time;
    }

    public String getIs_publish() {
        return is_publish;
    }

    public void setIs_publish(String is_publish) {
        this.is_publish = is_publish;
    }

    public String getPen_name() {
        return pen_name;
    }

    public void setPen_name(String pen_name) {
        this.pen_name = pen_name;
    }

    public List<EditMod> getContentList() {
        List<EditMod> list = new ArrayList<>();
        if (!StringUtils.isEmpty(content)) {
            EditMod[] modtemp = JsonUtils.readValue(content, EditMod[].class);
            list.addAll(Arrays.asList(modtemp));
        }
        return list;
    }

    /**
     * 根据类型获取类容列表
     *
     * @param type
     * @return
     */
    public List<EditMod> getContentListByType(int type) {
        List<EditMod> list = getContentList();
        List<EditMod> reList = new ArrayList<>();
        for (int i = 0; i < ListUtils.getSize(list); i++) {
            if (list.get(i).getItemType() == type && !StringUtils.isEmpty(list.get(i).getContent())) {
                reList.add(list.get(i));
            }
        }
        return reList;
    }

}
