package com.jjxt.ssm.utils;

import java.util.List;

public class Series<T>{
    public String name;

    public String type;
    public List<T> data;// 这里要用int 不能用String 不然前台显示不正常（特别是在做数学运算的时候）
    public String barWidth;
    public String smooth;
//    public itemStyle itemStyle;
    public String symbol;
    public Series(String name, String type, List<T> data,String barWidth,String smooth) {
        super();
        this.name = name;
        this.type = type;
        this.data = data;
        this.barWidth = barWidth;
        this.smooth = smooth;
//        this.itemStyle=new itemStyle();
    }
    public Series(String name, String type, List<T> data,String barWidth,String smooth,String symbol) {
    	super();
    	this.name = name;
    	this.type = type;
    	this.data = data;
    	this.barWidth = barWidth;
    	this.smooth = smooth;
    	this.symbol = symbol;
//    	this.itemStyle=new itemStyle();
    }
}

class itemStyle {
	public normal normal;
	public itemStyle() {
		this.normal=new normal();
	}
}
class normal{
	public label label;
	public normal() {
		this.label=new label();
	}
}
class label{
	public String show;
	
	public label(){
		this.show="false";
	}
}