package cn.wisdom.service.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import cn.wisdom.service.CreditService;

public class CreditTask {

	@Autowired
	private CreditService creditService;
	
	@Scheduled(cron = "0 5 0 * * ?")
	public void scanOverdueApply()
	{
//		List<CreditApply> overdueApplies = creditService.getOverdueApplyList();
		
//		for (CreditApply creditApply : overdueApplies) {
//			creditApply.setApplyState(ApplyState.Overdue);
//			
//			//给用户和老板发通知消息
//		}
		
		creditService.updateOverdueApplyState();
	}

}
