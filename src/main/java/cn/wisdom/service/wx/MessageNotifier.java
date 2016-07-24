package cn.wisdom.service.wx;

import me.chanjar.weixin.common.exception.WxErrorException;
import cn.wisdom.dao.vo.CreditApply;

public interface MessageNotifier {

	void notifyBossNewApply(CreditApply creditApply) throws WxErrorException;

	void notifyUserApplyApproved(CreditApply apply) throws WxErrorException;
}
