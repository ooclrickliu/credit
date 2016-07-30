package cn.wisdom.service.wx;

import me.chanjar.weixin.common.exception.WxErrorException;
import cn.wisdom.dao.vo.CreditApply;
import cn.wisdom.dao.vo.CreditPayRecord;

public interface MessageNotifier {

	void notifyBossNewApply(CreditApply creditApply) throws WxErrorException;

	void notifyUserApplyApproved(CreditApply apply) throws WxErrorException;

	void notifyUserReturnSuccess(CreditApply apply, CreditPayRecord payRecord) throws WxErrorException;

	void notifyUserReturnFailed(CreditApply apply, CreditPayRecord payRecord) throws WxErrorException;
}
