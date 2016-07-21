package cn.wisdom.service.wx.message;

import java.util.List;
import java.util.Map;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutNewsMessage;
import me.chanjar.weixin.mp.bean.outxmlbuilder.NewsBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.wisdom.common.log.Logger;
import cn.wisdom.common.log.LoggerFactory;
import cn.wisdom.common.utils.CollectionUtils;
import cn.wisdom.common.utils.DateTimeUtils;
import cn.wisdom.common.utils.StringUtils;
import cn.wisdom.service.UserService;
import cn.wisdom.service.exception.ServiceException;
import cn.wisdom.service.wx.WXService;

@Component
public class WxMpEventHandler implements WxMpMessageHandler {

	@Autowired
	private WXService wxService;
	
	@Autowired
	private UserService userService;
	
//	private static final String EVENT_KEY_SCAN = "qrscene_";
	
	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
			Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager) throws WxErrorException {

		logger.info("====== Fired event ======");
		logger.info(wxMessage.toString());
		logger.info("=========================");
		
		WxMpXmlOutMessage response = null;
		
		// 关注
		if (StringUtils.equalsIgnoreCase(wxMessage.getEvent(),
				WxConsts.EVT_SUBSCRIBE)) {
			response = handleSubscribe(wxMessage);
		}
		// 取消关注
		else if (StringUtils.equalsIgnoreCase(wxMessage.getEvent(),
				WxConsts.EVT_UNSUBSCRIBE)) {
			// do nothing
		}
		// 点击菜单
		else if (StringUtils.equalsIgnoreCase(wxMessage.getEvent(),
				WxConsts.EVT_CLICK)) {
			response = handleMenuClick(wxMessage);
		}

		return response;
	}

	private WxMpXmlOutMessage handleMenuClick(WxMpXmlMessage wxMessage) {
		String menuKey = wxMessage.getEventKey();
		
		WxMpXmlOutMessage response = null;
		
		//开奖公告
		if (StringUtils.equalsIgnoreCase(menuKey, "draw_notice")) {
//			response = getDrawNotice(wxMessage);
		}
		//我的彩票
		else if (StringUtils.equalsIgnoreCase(menuKey, "my_lottery")) {
//			response = getMyLottery(wxMessage);
		}
		
		return response;
	}

	private WxMpXmlOutMessage handleSubscribe(WxMpXmlMessage wxMessage) {
		// 保存用户
//		try {
//			userService.createUser(wxMessage.getFromUserName(), RoleType.CUSTOMER);
//		} catch (ServiceException e) {
//			
//		}
		
		return null;

	}

}
