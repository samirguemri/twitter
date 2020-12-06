package edu.samir.twitter.data;

import edu.samir.twitter.data.user.IUserRepository;
import edu.samir.twitter.model.Gender;
import edu.samir.twitter.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DataApp {

    private IUserRepository userRepository;

    @Autowired
    public void setUserRepository(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static void main( String[] args )
    {
        DataApp dataApp = new DataApp();
        dataApp.setUserRepository(new AnnotationConfigApplicationContext(RuntimeScopePersistenceConfiguration.class).getBean(IUserRepository.class));
        dataApp.userRepository.insertUser(new User("Samir", "Guemri", Gender.MALE, "10/10/1990"));
        System.out.println(dataApp.userRepository.selectAllUsers().get(0));
    }
}
