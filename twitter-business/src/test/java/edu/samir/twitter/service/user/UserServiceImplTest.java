package edu.samir.twitter.service.user;

import edu.samir.twitter.data.user.IUserRepository;
import edu.samir.twitter.model.Gender;
import edu.samir.twitter.model.User;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

public class UserServiceImplTest {

    @Mock
    private SessionFactory sessionFactory;
    @Mock
    private IUserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    private final User samir = new User("Samir", "Guemri", Gender.MALE, "10/10/1990");

    @Before
    public void init() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void sessionFactory() {
        assertNotNull(sessionFactory);
    }

    @Test
    public void userRepository() {
        assertNotNull(userRepository);
    }

    @Test
    public void userService() {
        assertNotNull(userService);
    }

    @Test
    public void addUser() {
        // userService.addUser(samir);
        // ArgumentCaptor<User> argument = ArgumentCaptor.forClass( User.class );
        // verify(userRepository).insertUser(argument.capture());
        // assertEquals(samir, argument.getValue());

        samir.setUserId(0L);
        userService.addUser(samir);
        when(userRepository.selectUserById(0L)).thenReturn(samir);
        assertEquals(samir,userService.getUserById(samir.getUserId()));
    }

    @Test
    public void getUserById() {
        samir.setUserId(0L);
        userService.addUser(samir);
        when(userRepository.selectUserById(0L)).thenReturn(samir);
        assertEquals(samir,userService.getUserById(samir.getUserId()));
    }

    @Test
    public void getUserByName() {
        userService.addUser(samir);
        when(userRepository.selectUserByName("Samir", "Guemri")).thenReturn(samir);
        assertEquals(samir,userService.getUserByName("Samir", "Guemri"));
    }

    @Test
    public void getAllUsers() {
        userService.addUser(samir);
        when(userRepository.selectAllUsers()).thenReturn(new ArrayList<>(Arrays.asList(new User[]{samir})));
        assertEquals(1,userService.getAllUsers().size());
    }

    @Test
    public void updateUser() {
        samir.setUserId(0L);
        userService.addUser(samir);
        when(userRepository.selectUserById(0L)).thenReturn(samir);
        assertEquals(samir,userService.getUserById(samir.getUserId()));

        samir.setFirstName("Zeyn");
        userService.updateUser(samir);
        when(userRepository.selectUserById(0L)).thenReturn(samir);
        assertEquals("Zeyn",userService.getUserById(samir.getUserId()).getFirstName());
    }

    @Test
    public void deleteUserById() {
        userService.addUser(samir);
        when(userRepository.selectAllUsers()).thenReturn(new ArrayList<>(Arrays.asList(new User[]{samir})));
        assertEquals(1, userService.getAllUsers().size());
        userService.deleteUserById(samir.getUserId());
        when(userRepository.selectAllUsers()).thenReturn(new ArrayList<>());
        assertEquals(0, userService.getAllUsers().size());
    }
}