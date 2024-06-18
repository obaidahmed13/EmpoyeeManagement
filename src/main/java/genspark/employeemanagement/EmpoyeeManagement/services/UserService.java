package genspark.employeemanagement.EmpoyeeManagement.services;


import genspark.employeemanagement.EmpoyeeManagement.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);
    User createUser(User user);
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    String deleteUser(Long id);
}
