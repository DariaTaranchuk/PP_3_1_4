package ru.kata.spring.boot_security.demo.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleRepositoryServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserRepositoryServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

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
    public String create(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult, Model model, @RequestParam("roles") List<Long> rolesId) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("allRoles", roleRepository.findAll());
            return "list1";
        }
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
    public String update(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult, @RequestParam("roles") List<Long> rolesId) {
        if(bindingResult.hasErrors()) {
            return "list1";
        }
        userService.updateUser(user.getId(), user, rolesId);
        return "redirect:/admin";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(value = "id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}

