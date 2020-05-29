package com.cmcc.algo.common;

import java.io.Serializable;

public class PageInfo implements Serializable {

    private static final long serialVersionUID = -3168245848375551671L;

    /** 总页数 */
    private long pages;
    /** 总记录数 */
    private long total;
    /** 查询数据的当前页 */
    protected int pageNum;
    /** 查询时每页的数据条数 */
    protected int step;


    public PageInfo(long total) {
        this.total = total;
    }

    public PageInfo(long total, int pageNum, int step) {
        this.total = total;
        this.pageNum = pageNum;
        this.step = step;
        this.pages = (total - 1) / step + 1;
    }

    public static PageInfo page(long total) {
        return new PageInfo(total);
    }

    public static PageInfo page(long total, int pageNum, int step) {
        return new PageInfo(total, pageNum, step);
    }

    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }
}
