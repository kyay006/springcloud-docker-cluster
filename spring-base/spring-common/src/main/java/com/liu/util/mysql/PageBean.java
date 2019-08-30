package com.liu.util.mysql;

import io.swagger.annotations.ApiModelProperty;

/**
 * 分页Bean
 */
public class PageBean {

    @ApiModelProperty(value = "当前页码")
    private int pageNum = 1;// 当前页码
    @ApiModelProperty(value = "每页大小")
    private int pageSize = 10;// 每页记录数
    private Integer totalCount = 0;// 总记录数
    private Integer totalPage = 0;// 总页数
    
    private Object val;//封装的数据返回

    public PageBean(int pageSize, int pageNum) {
        this.pageSize = pageSize ;
        this.pageNum = pageNum ;
    }

    public Integer getOffset(){
		return (pageNum -1) * pageSize;
    }
    

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getTotalPage() {
        totalPage = totalCount / pageSize;
        if (totalCount % pageSize > 0) {
            totalPage++;
        }
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }
    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        if(pageNum > 10000)
        {
            this.pageNum = 10000;
            return;
        }
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        if(pageSize > 500)
        {
            this.pageSize = 500;
            return;
        }
        this.pageSize = pageSize;
    }
    public Object getVal() {
		return val;
	}

	public void setVal(Object val) {
		this.val = val;
	}

}