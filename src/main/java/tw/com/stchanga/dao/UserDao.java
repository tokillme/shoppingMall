package tw.com.stchanga.dao;

import tw.com.stchanga.dto.UserRegisterRequest;
import tw.com.stchanga.model.User;

public interface UserDao {
	
	User getUserById(Integer userId);
	
	User getUserByEmail(String email);
	
	Integer createUser(UserRegisterRequest userRegisterRequest);
}
