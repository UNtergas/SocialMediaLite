package org.socialnetwork.codebase.controllers;

import org.socialnetwork.codebase.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
@SessionAttributes("currentUser")
public class DashboardController {
    @GetMapping("")
    public String showDashboard(@SessionAttribute(value = "currentUser", required = false) User currentUser, Model model) {
        if (currentUser == null) {
            return "redirect:/auth/login";
        }
        model.addAttribute("currentUser", currentUser);
        return "dashboard";
    }
}
