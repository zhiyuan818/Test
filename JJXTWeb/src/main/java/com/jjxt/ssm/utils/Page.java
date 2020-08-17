package com.jjxt.ssm.utils;

public class Page {
	// 总页数
	private int totalPageCount;
	// 页面大小，即每页显示记录
	private int pageSize;
	// 记录总数
	private int totalCount;
	// 当前页码
	private int currPageNo;

	public int getTotalPageCount() {
		return totalPageCount;
	}

	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		if (pageSize > 0)
			this.pageSize = pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getCurrPageNo() {
		return currPageNo;
	}

	public void setCurrPageNo(int currPageNo) {
		if (currPageNo > 0)
			this.currPageNo = currPageNo;
	}

	public Page(int pageSize, int totalCount, Integer currPageNo) {
		this.pageSize = pageSize;
		this.totalCount = totalCount;
		// 计算总页数
		this.totalPageCount = this.totalCount % pageSize == 0 ? (this.totalCount / pageSize)
				: this.totalCount / pageSize + 1;
		if (null == currPageNo) {
			this.currPageNo = 1;
		} else {
			this.currPageNo = currPageNo;
		}
	}
}
