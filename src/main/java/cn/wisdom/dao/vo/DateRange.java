package cn.wisdom.dao.vo;

import java.util.Date;

import cn.wisdom.common.utils.DateTimeUtils;

public enum DateRange {

	Day5(5), Day30(30), All(99999);
	
	private int value;
	
	private DateRange(int value)
	{
		this.value = value;
	}
	
	public Date exactTime()
	{
		return DateTimeUtils.addDays(DateTimeUtils.getCurrentTimestamp(), this.value);
	}
	
	public static void main(String[] args) {
		System.out.println(Day5.exactTime());
		System.out.println(Day30.exactTime());
		System.out.println(All.exactTime());
		
		System.out.println(DateRange.valueOf("Day5"));
		System.out.println(DateRange.valueOf("Day30"));
		System.out.println(DateRange.valueOf("All"));
	}
}
