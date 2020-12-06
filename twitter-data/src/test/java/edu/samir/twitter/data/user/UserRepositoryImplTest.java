package edu.samir.twitter.data.user;

import edu.samir.twitter.data.RuntimeScopePersistenceConfiguration;
import edu.samir.twitter.data.TestScopePersistenceConfiguration;
import edu.samir.twitter.model.Gender;
import edu.samir.twitter.model.User;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import static org.junit.Assert.*;

public class UserRepositoryImplTest {

    private SessionFactory sessionFactory;
    private IUserRepository userRepository;
    private PlatformTransactionManager transactionManager;

    private final User samir = new User("Samir", "Guemri", Gender.MALE, "10/10/1990");

    @Before
    public void init() {
        AnnotationConfigApplicationContext config = new AnnotationConfigApplicationContext(TestScopePersistenceConfiguration.class);
        sessionFactory = config.getBean(SessionFactory.class);
        userRepository = config.getBean(IUserRepository.class);
        transactionManager = config.getBean(PlatformTransactionManager.class); 
    }

    @Test
    public void sessionFactory() {
        assertNotNull(sessionFactory);
    }

    @Test
    public void insertUser() {
        TransactionStatus transaction = transactionManager.getTransaction(new DefaultTransactionDefinition());

        assertEquals(0, sessionFactory.getCurrentSession().createQuery("FROM User", User.class).getResultList().size());
        userRepository.insertUser(samir);
        assertEquals(1, sessionFactory.getCurrentSession().createQuery("FROM User", User.class).getResultList().size());
        assertEquals(samir, sessionFactory.getCurrentSession().get(User.class,1L));

        transactionManager.commit(transaction);
    }

    @Test
    public void selectUserById() {
        userRepository.insertUser(samir);
        User user = userRepository.selectUserById(samir.getUserId());
        assertEquals(samir,user);
    }

    @Test
    public void selectUserByName() {
        userRepository.insertUser(samir);
        User user = userRepository.selectUserByName(samir.getFirstName(), samir.getLastName());
        assertEquals(samir,user);
    }

    @Test
    public void selectAllUsers() {
        assertEquals(0, userRepository.selectAllUsers().size());
        userRepository.insertUser(samir);
        assertEquals(1, userRepository.selectAllUsers().size());
    }

    @Test
    public void updateUser() {
        userRepository.insertUser(samir);
        samir.setFirstName("Zeyn");
        userRepository.updateUser(samir);
        assertEquals("Zeyn", userRepository.selectUserById(samir.getUserId()).getFirstName());
    }

    @Test
    public void removeUserById() {
        userRepository.insertUser(samir);
        assertEquals(1, userRepository.selectAllUsers().size());
        userRepository.removeUserById(samir.getUserId());
        assertEquals(0, userRepository.selectAllUsers().size());
    }
}