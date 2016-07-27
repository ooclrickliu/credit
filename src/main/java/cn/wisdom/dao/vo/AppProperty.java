/**
 * AppProperty.java
 * 
 * Copyright@2016 OVT Inc. All rights reserved. 
 * 
 * 2016-2-17
 */
package cn.wisdom.dao.vo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import cn.wisdom.common.utils.CollectionUtils;
import cn.wisdom.common.utils.StringUtils;

/**
 * AppProperty
 * 
 * @Author leo.liu
 * @Version 1.0
 * @See
 * @Since [OVT Cloud Platform]/[API] 1.0
 */
@Component
public class AppProperty
{
	@Value("${cookie.access_token.hour.age}")
	public int cookieAccessTokenHourAge;

	@Value("${credit.rate.day}")
	public float creditRatePerDay;
	
	@Value("${credit.commission.rate}")
	public float creditCommissionRate;
	
	@Value("${credit.line.default}")
	public float defaultCreditLine;
	
	@Value("${credit.line.min}")
	public float minCreditLine;
	
	@Value("${credit.line.max}")
	public float maxCreditLine;
	
	@Value("${user.openid.boss}")
	public String bossOpenids;
	
	private List<String> bossOpenidList;

	public List<String> getBossOpenidList() {
		if (CollectionUtils.isEmpty(bossOpenidList)) {
			bossOpenidList = new ArrayList<String>();
			
			if (StringUtils.isNotBlank(bossOpenids)) {
				String[] bossOpenidArr = bossOpenids.split(",|;");
				for (String bossOpenid : bossOpenidArr) {
					bossOpenidList.add(bossOpenid);
				}
			}
		}
		return bossOpenidList;
	}
}
