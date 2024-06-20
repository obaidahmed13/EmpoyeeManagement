package genspark.employeemanagement.EmpoyeeManagement.services;

import genspark.employeemanagement.EmpoyeeManagement.models.User;
import genspark.employeemanagement.EmpoyeeManagement.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImp userServ;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        user1 = new User();
        user1.setId(1L);
        user1.setUsername("user1");
        user1.setPassword("password1");
        user1.setRole("NORMAL");

        user2 = new User();
        user2.setId(2L);
        user2.setUsername("user2");
        user2.setPassword("password2");
        user2.setRole("ADMIN");
    }

    @Test
    void createUser() {
        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user1);

        User userToCreate = new User();
        userToCreate.setUsername("user1");
        userToCreate.setPassword("password1");
        userToCreate.setRole("ROLE_USER");

        User createdUser = userServ.createUser(userToCreate);

        assertNotNull(createdUser);
        assertEquals("user1", createdUser.getUsername());
        assertEquals("password1", createdUser.getPassword());
        assertEquals("NORMAL", createdUser.getRole());
    }

    @Test
    void getAllUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<User> users = userServ.getAllUsers();

        assertEquals(2, users.size());
        assertEquals("user1", users.get(0).getUsername());
        assertEquals("user2", users.get(1).getUsername());
    }

    @Test
    void getUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));

        Optional<User> foundUser = userServ.getUserById(1L);

        assertTrue(foundUser.isPresent());
        assertEquals(1L, foundUser.get().getId());
        assertEquals("user1", foundUser.get().getUsername());
        assertEquals("password1", foundUser.get().getPassword());
    }
}