package com.trivium.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.trivium.demo.entity.TriviumUser1;
import com.trivium.demo.repo.TriviumUser1UserRepo;

@Service
public class TriviumUsersDetailService implements UserDetailsService {

	@Autowired
	TriviumUser1UserRepo triviumUser1Repo;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		TriviumUser1 triviumUser1 = triviumUser1Repo.findByUserName(username).get();
		System.out.println("Guru "+triviumUser1.getUserName()+" "+triviumUser1.getPassword()+" "+triviumUser1.getRoles());
		return User.builder().username(triviumUser1.getUserName()).password(triviumUser1.getPassword())
				.roles(triviumUser1.getRoles().split(",")).build();

	}

}
