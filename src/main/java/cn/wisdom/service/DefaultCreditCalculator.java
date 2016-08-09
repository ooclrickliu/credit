package cn.wisdom.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.wisdom.common.utils.DateTimeUtils;
import cn.wisdom.common.utils.NumberUtils;
import cn.wisdom.dao.vo.AppProperty;
import cn.wisdom.dao.vo.CreditApply;
import cn.wisdom.dao.vo.User;

@Service
public class DefaultCreditCalculator implements CreditCalculator {

	/**
	 * Key: userLevel, Value: base creditLine
	 */
	private Map<Integer, CreditLineLevel> userLevelCreditLineMap = new HashMap<Integer, CreditLineLevel>();

	@PostConstruct
	public void init() {
		userLevelCreditLineMap.put(0, new CreditLineLevel(0, 0));
		userLevelCreditLineMap.put(1, new CreditLineLevel(1000, 2000));
		userLevelCreditLineMap.put(2, new CreditLineLevel(2000, 3000));
		userLevelCreditLineMap.put(3, new CreditLineLevel(3000, 4000));
		userLevelCreditLineMap.put(4, new CreditLineLevel(4000, 4500));
		userLevelCreditLineMap.put(5, new CreditLineLevel(4500, 5000));
	}

	class CreditLineLevel {
		private int min;
		private int max;

		public CreditLineLevel(int min, int max) {
			this.min = min;
			this.max = max;
		}

		public int getMin() {
			return min;
		}

		public void setMin(int min) {
			this.min = min;
		}

		public int getMax() {
			return max;
		}

		public void setMax(int max) {
			this.max = max;
		}

	}

	@Autowired
	private AppProperty appProperty;

	@Override
	public void calculateFee(CreditApply creditApply) {

		// 1. interest
		Date today = new Date();
		Date dueDate = DateTimeUtils.addMonths(today, creditApply.getMonth());
		int days = DateTimeUtils.dateDiff(today, dueDate);

		float interest = NumberUtils.formatFloat(creditApply.getAmount() * days
				* appProperty.creditRatePerDay);
		creditApply.setInterest(interest);

		// 2. commission
		float commission = NumberUtils.formatFloat(creditApply.getAmount()
				* appProperty.creditCommissionRate);
		creditApply.setCommission(commission);
	}

	@Override
	public float calculateInterest(float amount, Timestamp effectiveTime,
			float creditRatePerDay) {

		int days = DateTimeUtils.dateDiff(effectiveTime,
				DateTimeUtils.getCurrentTimestamp());

		return NumberUtils.formatFloat(amount * creditRatePerDay * days);
	}

	@Override
	public float calculateUserCreditLine(User user) {

		CreditLineLevel creditLineLevel = userLevelCreditLineMap.get(user
				.getLevel());

		Random random = new Random();

		int creditLine = random.nextInt(creditLineLevel.getMax()
				- creditLineLevel.getMin())
				+ creditLineLevel.getMin();
		return creditLine;
	}

}
