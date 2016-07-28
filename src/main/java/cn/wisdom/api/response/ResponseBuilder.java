package cn.wisdom.api.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.wisdom.common.utils.CollectionUtils;
import cn.wisdom.dao.vo.CreditApply;
import cn.wisdom.dao.vo.User;

public class ResponseBuilder {

	public static List<CreditApplyResponse> buildCreditApplyResposne(
			List<CreditApply> applyList, List<User> userList) {
		List<CreditApplyResponse> responses = new ArrayList<CreditApplyResponse>();
		
		if (CollectionUtils.isNotEmpty(applyList)) {
			
			Map<Long, User> userMap = toUserMap(userList);
			
			CreditApplyResponse response;
			for (CreditApply creditApply : applyList) {
				response = new CreditApplyResponse();
				response.setCreditApply(creditApply);
				response.setUser(userMap.get(creditApply.getUserId()));
				
				responses.add(response);
			}
		}
		
		return responses;
	}

	private static Map<Long, User> toUserMap(List<User> userList) {
		Map<Long, User> userMap = new HashMap<Long, User>();
		for (User user : userList) {
			userMap.put(user.getId(), user);
		}
		return userMap;
	}

}
