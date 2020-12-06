package edu.samir.twitter.service.user;

import java.util.List;

import edu.samir.twitter.model.User;

public interface IUserService {
	void addUser(User user);
	User getUserById(Long userId);
	User getUserByName(String firstName, String lastName);
	List<User> getAllUsers();
	void updateUser(User user);
	void deleteUserById(Long userId);
}
