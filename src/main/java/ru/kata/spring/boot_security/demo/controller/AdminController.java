package ru.kata.spring.boot_security.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleRepositoryServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserRepositoryServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;
    private RoleRepositoryServiceImpl roleRepository;
    private UserRepositoryServiceImpl userRepository;

    public AdminController(UserService userService, RoleRepositoryServiceImpl roleRepository, UserRepositoryServiceImpl userRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @GetMapping()
    public String showAllUsers(Model model, Principal principal) {
        model.addAttribute("users", userService.showAllUsers());
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleRepository.findAll());

        String username = principal.getName();
        User user = userRepository.findByUsername(username);
        model.addAttribute("currentUser", user);

        return "list1";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") User user,
                          @RequestParam(value = "roles", defaultValue = "1") List<Long> rolesId) {
        userService.createUser(user, rolesId);
        return "redirect:/admin";
        }

    @GetMapping("/update")
    public String updateForm(@RequestParam(value = "id") long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("allRoles", roleRepository.findAll());
        return "list1";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("user") User user,
                         @RequestParam(value = "roles", defaultValue = "1") List<Long> rolesId) {
        userService.updateUser(user.getId(), user, rolesId);
        return "redirect:/admin";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(value = "id") long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "list1";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute("user") User user,
                         @RequestParam(value = "roles" , defaultValue = "1") List<Long> rolesId) {
        userService.deleteUser(user.getId());
        return "redirect:/admin";
    }
}

