package genspark.employeemanagement.EmpoyeeManagement.services;

import genspark.employeemanagement.EmpoyeeManagement.models.User;
import genspark.employeemanagement.EmpoyeeManagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    UserRepository userRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Optional<User> findByUsername(String username) {
        return this.userRepo.findByUsername(username);
    }

    @Override
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return this.userRepo.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return this.userRepo.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return this.userRepo.findById(id);
    }

    @Override
    public String deleteUser(Long id) {
        return "Deleted User";
    }


}
