package cn.wisdom.service.wx.message;

import java.util.Map;

import org.springframework.stereotype.Component;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import cn.wisdom.common.log.Logger;
import cn.wisdom.common.log.LoggerFactory;

@Component
public class WxMpLogHandler implements WxMpMessageHandler {

	private Logger logger = LoggerFactory.getLogger(WxMpLogHandler.class.getName());
	
	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
			Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager) throws WxErrorException {
		logger.info("From: " + wxMessage.getFromUserName() + " \n Message: " + wxMessage.getContent());
		
		return WxMpEventHandler.success;
	}

}
