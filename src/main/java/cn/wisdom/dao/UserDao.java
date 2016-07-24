package cn.wisdom.dao;

import java.util.List;

import cn.wisdom.dao.constant.UserState;
import cn.wisdom.dao.vo.User;

public interface UserDao {

	void save(User user);

	void updateUserWxInfo(User user);

	User getUserByOpenid(String openId);

	User getUserById(long userId);

	void updateUserStuffInfo1(User user);

	void updateUserStuffInfo2(User user);

	void updateUserStuffInfo3(User user);

	void updateUserStuffInfo4(User user);

	List<User> getUserList(UserState userState);

}
