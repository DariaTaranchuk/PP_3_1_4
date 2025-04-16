package ru.kata.spring.boot_security.demo.service;

import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public List<User> showAllUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }

    @Transactional
    @Override
    public User getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        user.getRoles().size();
        return user;
    }

    @Transactional
    @Override
    public void createUser(User user, List<Long> rolesId) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = roleRepository.findByIdIn(rolesId);
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void updateUser(Long id, User updateUser, List<Long> rolesId) {
        User userToBeUpdated = getUser(id);
        userToBeUpdated.setName(updateUser.getName());
        userToBeUpdated.setSurname(updateUser.getSurname());
        userToBeUpdated.setAge(updateUser.getAge());
        userToBeUpdated.setEmail(updateUser.getEmail());
        if (updateUser.getPassword() != null && !updateUser.getPassword().isEmpty()) {
            userToBeUpdated.setPassword(passwordEncoder.encode(updateUser.getPassword()));
        }
        Set<Role> roles = roleRepository.findByIdIn(rolesId);
        userToBeUpdated.setRoles(roles);
        userRepository.save(userToBeUpdated);
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


    public User findByUsername(String email) {
        return userRepository.findByUsername(email);
    }
}
