package cn.wisdom.dao;

import java.util.List;

import cn.wisdom.dao.constant.UserState;
import cn.wisdom.dao.mapper.DaoRowMapper;
import cn.wisdom.dao.vo.User;

public class UserDaoImpl implements UserDao {

	private DaoHelper daoHelper;

	private static final String SQL_SAVE_USER = "insert into user(openid, role, credit_line, user_state, update_time) values (?, ?, ?, ?, current_timestamp)";

	private static final String SQL_UPDATE_USER_WX_INFO = "update user set nick_name = ?, headImgUrl = ?, update_time = current_timestamp where openid = ?";

	private static final String SQL_UPDATE_USER_STUFF_INFO1 = "update user set real_name = ?, id_face_img_url = ?, id_back_img_url = ?, person_id_img_url = ?, province = ?, city = ?, country = ?, marital_status = ?, degree = ?, update_time = current_timestamp where openid = ?";

	private static final String SQL_UPDATE_USER_STUFF_INFO2 = "update user set relative_name1 = ?, relative_relation1 = ?, relative_phone1 = ?, relative_name2 = ?, relative_relation2 = ?, relative_phone2 = ?, update_time = current_timestamp where openid = ?";

	private static final String SQL_UPDATE_USER_STUFF_INFO3 = "update user set phone = ?, phonePassword = ?, accountNo = ?, update_time = current_timestamp where openid = ?";

	private static final String SQL_UPDATE_USER_STUFF_INFO4 = "update user set wx_pay_img_url = ?, update_time = current_timestamp where openid = ?";

	private static final String SQL_GET_USER_PREFIX = "select * from user ";

	private static final String SQL_GET_USER_BY_OPENDID = SQL_GET_USER_PREFIX
			+ "where openid = ?";

	private static final String SQL_GET_USER_BY_STATE = SQL_GET_USER_PREFIX
			+ "where user_state = ?";

	private static final DaoRowMapper<User> userMapper = new DaoRowMapper<User>(
			User.class);

	@Override
	public void save(User user) {

		String errMsg = "Failed to save new user, openid: " + user.getOpenid();
		daoHelper.save(SQL_SAVE_USER, errMsg, false, user.getOpenid(), user
				.getRole().toString(), user.getCreditLine(), user
				.getUserState().toString());
	}

	@Override
	public void updateUserWxInfo(User user) {

		String errMsg = "Failed to update user wx info, openid: "
				+ user.getOpenid();
		daoHelper.update(SQL_UPDATE_USER_WX_INFO, errMsg, user.getNickName(),
				user.getHeadImgUrl(), user.getOpenid());
	}

	@Override
	public User getUserByOpenid(String openId) {

		String errMsg = "Failed to get user by openid: " + openId;
		User user = daoHelper.queryForObject(SQL_GET_USER_BY_OPENDID,
				userMapper, errMsg, openId);

		return user;
	}

	@Override
	public void updateUserStuffInfo1(User user) {

		String errMsg = "Failed to update user stuff info step1, openid: "
				+ user.getOpenid();
		daoHelper.update(SQL_UPDATE_USER_STUFF_INFO1, errMsg, user
				.getRealName(), user.getIdFaceImgUrl(), user.getIdBackImgUrl(),
				user.getPersonIdImgUrl(), user.getProvince(), user.getCity(),
				user.getDistinct(), user.getMaritalStatus().toString(), user
						.getDegree().toString(), user.getOpenid());
	}

	@Override
	public void updateUserStuffInfo2(User user) {

		String errMsg = "Failed to update user stuff info step1, openid: "
				+ user.getOpenid();
		daoHelper.update(SQL_UPDATE_USER_STUFF_INFO2, errMsg, user
				.getRealName(), user.getIdFaceImgUrl(), user.getIdBackImgUrl(),
				user.getPersonIdImgUrl(), user.getProvince(), user.getCity(),
				user.getDistinct(), user.getMaritalStatus().toString(), user
						.getDegree().toString(), user.getOpenid());
	}

	@Override
	public void updateUserStuffInfo3(User user) {

		String errMsg = "Failed to update user stuff info step1, openid: "
				+ user.getOpenid();
		daoHelper.update(SQL_UPDATE_USER_STUFF_INFO3, errMsg, user
				.getRealName(), user.getIdFaceImgUrl(), user.getIdBackImgUrl(),
				user.getPersonIdImgUrl(), user.getProvince(), user.getCity(),
				user.getDistinct(), user.getMaritalStatus().toString(), user
						.getDegree().toString(), user.getOpenid());
	}

	@Override
	public void updateUserStuffInfo4(User user) {

		String errMsg = "Failed to update user stuff info step1, openid: "
				+ user.getOpenid();
		daoHelper.update(SQL_UPDATE_USER_STUFF_INFO4, errMsg, user
				.getRealName(), user.getIdFaceImgUrl(), user.getIdBackImgUrl(),
				user.getPersonIdImgUrl(), user.getProvince(), user.getCity(),
				user.getDistinct(), user.getMaritalStatus().toString(), user
						.getDegree().toString(), user.getOpenid());
	}

	@Override
	public List<User> getUserList(UserState userState) {

		String errMsg = "Failed to query user of state: " + userState;
		return daoHelper.queryForList(SQL_GET_USER_BY_STATE, userMapper,
				errMsg, userState.toString());
	}

}