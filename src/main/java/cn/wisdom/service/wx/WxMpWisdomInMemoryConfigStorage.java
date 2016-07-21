package cn.wisdom.service.wx;

import java.io.File;
import java.io.InputStream;

import me.chanjar.weixin.common.util.xml.XStreamInitializer;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("xml")
public class WxMpWisdomInMemoryConfigStorage extends WxMpInMemoryConfigStorage {

	protected volatile String uploadDir;

	public static WxMpWisdomInMemoryConfigStorage fromXml(InputStream is) {
		XStream xstream = XStreamInitializer.getInstance();
		xstream.processAnnotations(WxMpWisdomInMemoryConfigStorage.class);
		return (WxMpWisdomInMemoryConfigStorage) xstream.fromXML(is);
	}

	@Override
	public String toString() {
		return "SimpleWxConfigProvider [appId=" + appId + ", secret=" + secret
				+ ", accessToken=" + accessToken + ", expiresTime="
				+ expiresTime + ", token=" + token + ", aesKey=" + aesKey + "]";
	}
	
	@Override
	public File getTmpDirFile() {
		if (tmpDirFile == null) {
			tmpDirFile = new File(uploadDir);
		}
		
		return tmpDirFile;
	}
}
