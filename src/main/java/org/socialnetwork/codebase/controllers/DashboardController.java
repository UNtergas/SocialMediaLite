package org.socialnetwork.codebase.controllers;

import org.socialnetwork.codebase.models.User;
import org.socialnetwork.codebase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
@SessionAttributes("currentUser")
public class DashboardController {
    private final UserService userService;

    @Autowired
    public DashboardController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String showDashboard(@SessionAttribute(value = "currentUser", required = false) User currentUser, Model model) {
        if (currentUser == null) {
            return "redirect:/auth/login";
        }
        List<User> users = this.userService.getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("currentUser", currentUser);
        return "dashboard";
    }
}
