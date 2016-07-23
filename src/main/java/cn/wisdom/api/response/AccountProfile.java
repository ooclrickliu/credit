package cn.wisdom.api.response;

public class AccountProfile {
	
	// 7日待还
	private float pending7DAmount;

	// 30日待还
	private float pending30DAmount;

	// 全部待还
	private float pendingTotalAmount;
	
	// 总额度
	private float totalCreditLine;
	
	// 可用额度
	private float availableCreditLine;
	
	// 日利率
	private float ratePerDay;

	public float getPending7DAmount() {
		return pending7DAmount;
	}

	public void setPending7DAmount(float pending7dAmount) {
		pending7DAmount = pending7dAmount;
	}

	public float getPending30DAmount() {
		return pending30DAmount;
	}

	public void setPending30DAmount(float pending30dAmount) {
		pending30DAmount = pending30dAmount;
	}

	public float getPendingTotalAmount() {
		return pendingTotalAmount;
	}

	public void setPendingTotalAmount(float pendingTotalAmount) {
		this.pendingTotalAmount = pendingTotalAmount;
	}

	public float getTotalCreditLine() {
		return totalCreditLine;
	}

	public void setTotalCreditLine(float totalCreditLine) {
		this.totalCreditLine = totalCreditLine;
	}

	public float getAvailableCreditLine() {
		return availableCreditLine;
	}

	public void setAvailableCreditLine(float availableCreditLine) {
		this.availableCreditLine = availableCreditLine;
	}

	public float getRatePerDay() {
		return ratePerDay;
	}

	public void setRatePerDay(float ratePerDay) {
		this.ratePerDay = ratePerDay;
	}
}
