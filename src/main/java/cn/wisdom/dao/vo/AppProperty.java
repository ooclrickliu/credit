/**
 * AppProperty.java
 * 
 * Copyright@2016 OVT Inc. All rights reserved. 
 * 
 * 2016-2-17
 */
package cn.wisdom.dao.vo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
	@Value("${cookie.access_token.age}")
	public int cookieAccessTokenAge;

	@Value("${credit.rate.day}")
	public float creditRatePerDay;
	
	@Value("${credit.line.default}")
	public float defaultCreditLine;
	
	@Value("${credit.line.min}")
	public float minCreditLine;
	
	@Value("${credit.line.max}")
	public float maxCreditLine;
}
