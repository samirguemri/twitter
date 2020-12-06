package edu.samir.twitter.service;


import edu.samir.twitter.model.Gender;
import edu.samir.twitter.model.User;
import edu.samir.twitter.service.user.IUserService;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;

public class ServiceApp {

	private IUserService userService;

	public ServiceApp() { }

	@Autowired
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public static void main(String[] args ) {

		ServiceApp serviceApp = new ServiceApp();
		serviceApp.setUserService(new AnnotationConfigApplicationContext(RuntimeScopeServiceConfiguration.class).getBean(IUserService.class));
		serviceApp.userService.addUser(new User("Samir", "Guemri", Gender.MALE, "10/10/1990"));
		
		System.out.println(serviceApp.userService.getAllUsers());
    }
}
