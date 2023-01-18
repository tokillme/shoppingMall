package tw.com.stchanga.service;

import tw.com.stchanga.dto.UserLoginRequest;
import tw.com.stchanga.dto.UserRegisterRequest;
import tw.com.stchanga.model.User;

public interface UserService {
	
	User getUserById(Integer userId);
	
	Integer register(UserRegisterRequest userRegisterRequest);
	
	User login(UserLoginRequest userLoginRequest);
}
