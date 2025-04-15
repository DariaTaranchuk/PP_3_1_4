package ru.kata.spring.boot_security.demo.service;



import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    List<User> showAllUsers();

    User getUser(Long id);

    void createUser(User user, List<Long> rolesId);

    void updateUser(Long id, User updateUser, List<Long> rolesId);

    void deleteUser(Long id);

    User findByUsername(String email);
}
