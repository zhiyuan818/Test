package com.jjxt.ssm.utils;

import java.util.List;

public class PageResult<T> {

	private Integer total;

	private List<T> rows;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Object getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public PageResult(Integer total, List<T> rows) {
		super();
		this.total = total;
		this.rows = rows;
	}

}
