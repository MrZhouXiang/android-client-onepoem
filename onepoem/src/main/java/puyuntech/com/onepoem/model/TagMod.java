package puyuntech.com.onepoem.model;

import java.io.Serializable;
import java.sql.Date;

/**
 * 朝代
 *
 * @author Administrator
 */
public class TagMod implements Serializable {
    private String id; // 主键
    private String name; // 名字

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TagMod() {
    }

    public TagMod(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
