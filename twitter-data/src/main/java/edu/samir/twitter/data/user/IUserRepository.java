package edu.samir.twitter.data.user;

import java.util.List;

import edu.samir.twitter.model.User;

public interface IUserRepository {
	
	public void insertUser(User user);
	public User selectUserById(Long userId);
	public User selectUserByName(String firstName, String lastName);
	public List<User> selectAllUsers();
	public void updateUser(User user);
	public void removeUserById(Long userId);
	
}
