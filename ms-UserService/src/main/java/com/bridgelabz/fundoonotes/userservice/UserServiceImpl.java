package com.bridgelabz.fundoonotes.userservice;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.userdao.UserDetailsRepository;
import com.bridgelabz.fundoonotes.usermodels.User;
import com.bridgelabz.fundoonotes.userutility.EmailUtil;
import com.bridgelabz.fundoonotes.userutility.GenerateTokenImlp;

@Service
public class UserServiceImpl implements UserServiceInf {
	@Autowired
	private PasswordEncoder getPasswordEncoder;

	@Autowired
	private UserDetailsRepository userDetailsRepository;

	@Autowired
	private GenerateTokenImlp tokenGenerator;

	@Autowired
	private EmailUtil emailUtil;

	private static Logger log = org.slf4j.LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public boolean register(User user, HttpServletRequest request) {
		user.setPassword(getPasswordEncoder.encode(user.getPassword()));
		User registeredUser = userDetailsRepository.save(user);
		if (registeredUser != null) {
			String token = tokenGenerator.generateToken(String.valueOf(registeredUser.getId()));
			StringBuffer requestURL = request.getRequestURL();
			String activationURL = requestURL.substring(0, requestURL.lastIndexOf("/"));
			activationURL = activationURL + "/activationstatus/" + token;
			emailUtil.sendEmail("", "please click the link below to verify your Email", activationURL);
			return true;
		}
		return false;
	}

	public String userLogin(User user, HttpServletRequest request, HttpServletResponse response) {
		Optional<User> possiblyUser = userDetailsRepository.findByEmailId(user.getEmailId());
		if (possiblyUser.isPresent()) {
			User currentuser = possiblyUser.get();
			if (getPasswordEncoder.matches(user.getPassword(), currentuser.getPassword())
					&& currentuser.isActivate_Status()) {
				String token = tokenGenerator.generateToken(String.valueOf(currentuser.getId()));
				log.info(token);
				return token;
			}
		}
		return null;
	}



	public User userActivation(String token, HttpServletRequest request) {
		int id = tokenGenerator.verifyToken(token);
		Optional<User> possiblyUser = userDetailsRepository.findById(id);
		return possiblyUser
				.map(currentUser -> userDetailsRepository.save(currentUser.setActivate_Status(true)))
				.orElseGet(() -> null);
	}

	public User updateUser(String token, User user, HttpServletRequest request)
	{
		int id = tokenGenerator.verifyToken(token);
		Optional<User> possiblyUser = userDetailsRepository.findById(id);
		return possiblyUser
				.map(currentUser ->
				{
					currentUser.setName(user.getName())
					.setEmailId(user.getEmailId())
					.setMobileNumber(user.getMobileNumber())
					.setPassword(getPasswordEncoder.encode(user.getPassword()));
					return userDetailsRepository.save(currentUser);	
				}).orElseGet(()-> null);

	}

	public boolean deleteUser(String token, HttpServletRequest request) {
		int id = tokenGenerator.verifyToken(token);
		Optional<User> possiblyUser = userDetailsRepository.findById(id);
		return possiblyUser.map(currentUser -> {
			userDetailsRepository.delete(currentUser);
			return true;
		}).orElseGet(() -> false);
	}


	public boolean forgotPassword(String emailId, HttpServletRequest request) {
		Optional<User> user = userDetailsRepository.findByEmailId(emailId);
		if (user != null) {
			String token = tokenGenerator.generateToken(String.valueOf(user.get()));
			StringBuffer requestUrl = request.getRequestURL();
			String forgotPasswordLink = requestUrl.substring(0, requestUrl.lastIndexOf("/"));
			forgotPasswordLink = forgotPasswordLink + "/resetpassword/" + token;
			emailUtil.sendEmail("", "please click the link below to Reset your password"
					+ "", forgotPasswordLink);

		}
		return false;

	}

	public User resetPassword(User user, String token, HttpServletRequest request) {
		int id = tokenGenerator.verifyToken(token);
		Optional<User> possiblyUser = userDetailsRepository.findById(id);
		if (possiblyUser.isPresent()) {
			User reSetUser=possiblyUser.get();
			reSetUser.setPassword(getPasswordEncoder.encode(user.getPassword()));
			userDetailsRepository.save(reSetUser);
			return reSetUser;
		}
		return null;
	}








}




