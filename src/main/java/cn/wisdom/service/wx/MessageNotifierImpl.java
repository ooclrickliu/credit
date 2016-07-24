package cn.wisdom.service.wx;

import java.text.MessageFormat;
import java.util.List;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.WxMpCustomMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.wisdom.dao.vo.AppProperty;
import cn.wisdom.dao.vo.CreditApply;
import cn.wisdom.dao.vo.User;
import cn.wisdom.service.UserService;

@Service
public class MessageNotifierImpl implements MessageNotifier {

	@Autowired
	private UserService userService;
	
	@Autowired
	private WXService wxService;

	@Autowired
	private AppProperty appProperty;

	@Override
	public void notifyBossNewApply(CreditApply creditApply)
			throws WxErrorException {

		String content = MessageFormat.format("Boss, 有新借款申请(金额：{0})，请尽快审批!",
				creditApply.getAmount());

		List<String> bossOpenidList = appProperty.getBossOpenidList();
		for (String bossOpenid : bossOpenidList) {
			WxMpCustomMessage message = WxMpCustomMessage.TEXT()
					.content(content).toUser(bossOpenid).build();
			wxService.getWxMpService().customMessageSend(message);
		}
	}

	@Override
	public void notifyUserApplyApproved(CreditApply creditApply) throws WxErrorException {
		String content = MessageFormat.format("您好, 您的借款申请(金额：{0})已审批通过!",
				creditApply.getAmount());

		User user = userService.getUserById(creditApply.getUserId());
		
		WxMpCustomMessage message = WxMpCustomMessage.TEXT()
				.content(content).toUser(user.getOpenid()).build();
		wxService.getWxMpService().customMessageSend(message);

	}

}
