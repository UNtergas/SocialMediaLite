package org.socialnetwork.codebase.controllers;

import org.socialnetwork.codebase.models.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
@SessionAttributes("currentUser")
public class DashboardController {
    @GetMapping("")
    public String showDashboard(@SessionAttribute(value = "currentUser", required = false) Person currentPerson, Model model) {
        if (currentPerson == null) {
            return "redirect:/auth/login";
        }
        model.addAttribute("currentUser", currentPerson);
        return "dashboard";
    }
}
