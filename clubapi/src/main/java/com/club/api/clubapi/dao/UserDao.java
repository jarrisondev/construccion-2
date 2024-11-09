
package com.club.api.clubapi.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.club.api.clubapi.dao.repositories.UserRepository;
import com.club.api.clubapi.dto.UserDto;
import com.club.api.clubapi.model.Role;
import com.club.api.clubapi.model.User;

@Service
public class UserDao {

	@Autowired
	public UserRepository userRepository;

	public UserDto findByUserName(UserDto userDto) throws Exception {
		User user = userRepository.findByUserName(userDto.getUsername());
		if (user == null) {
			return null;
		}
		return Helper.parse(user);
	}

	public List<UserDto> findByRole(Role role) throws Exception {
		List<UserDto> usersDto = new ArrayList<>();
		List<User> users = userRepository.findByRole(role);
		for (User user : users) {
			UserDto userDto = Helper.parse(user);
			usersDto.add(userDto);
		}
		return usersDto;

	}

	public boolean existsByUserName(UserDto userDto) throws Exception {
		return userRepository.existsByUserName(userDto.getUsername());
	}

	public void createUser(UserDto userDto) throws Exception {
		User user = Helper.parse(userDto);
		userRepository.save(user);
	}

	public void updateUser(UserDto userDto) throws Exception {
		User user = userRepository.findByUserName(userDto.getUsername());
		if (user == null) {
			throw new Exception("Usuario no encontrado.");
		}
		user.setRole(userDto.getRole());
		userRepository.save(user);
	}
}