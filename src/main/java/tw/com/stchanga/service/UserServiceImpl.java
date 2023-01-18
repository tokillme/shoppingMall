package tw.com.stchanga.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

import tw.com.stchanga.dao.UserDao;
import tw.com.stchanga.dto.UserLoginRequest;
import tw.com.stchanga.dto.UserRegisterRequest;
import tw.com.stchanga.model.User;

@Component
public class UserServiceImpl implements UserService{
	
	
	//org.slf4j
	private final static Logger log=LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public User getUserById(Integer userId) {
		return userDao.getUserById(userId);
	}
	
	@Override
	public Integer register(UserRegisterRequest userRegisterRequest) {
		
		//check register email
		User user=userDao.getUserByEmail(userRegisterRequest.getEmail());
		
		if (user != null) {
			log.warn("該 email {} 已經被註冊", userRegisterRequest.getEmail());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		
		//use MD5 Generate password hash value
		String hashedPassword=DigestUtils.md5DigestAsHex(userRegisterRequest.getPassword().getBytes());
		userRegisterRequest.setPassword(hashedPassword);
		
		return userDao.createUser(userRegisterRequest);
	}

	@Override
	public User login(UserLoginRequest userLoginRequest) {
		//check user exist
		User user=userDao.getUserByEmail(userLoginRequest.getEmail());
		
		if(user==null) {
			log.warn("該 email {} 尚未註冊",userLoginRequest.getEmail());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		
		//use MD5 Generate password hash value
		String hashedPassword=DigestUtils.md5DigestAsHex(userLoginRequest.getPassword().getBytes());
		
		if(user.getPassword().equals(hashedPassword)) {
			return user;
		}else {
			log.warn("email {} 的密碼不正確",userLoginRequest.getEmail());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		
		
		
	}

	
	
	
}
