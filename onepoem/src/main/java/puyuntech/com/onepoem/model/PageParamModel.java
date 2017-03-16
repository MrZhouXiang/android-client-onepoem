package puyuntech.com.onepoem.model;

import java.io.Serializable;

/**
 * Created by wnb17 on 2017/3/15.
 */
public class PageParamModel implements Serializable {
    private int pageNum = 1;
    private int pageSize =10;
    private String orderBy ="";

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}
