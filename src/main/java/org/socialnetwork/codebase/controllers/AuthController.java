package org.socialnetwork.codebase.controllers;

import jakarta.servlet.http.HttpSession;
import org.socialnetwork.codebase.exceptions.UsernameAlreadyTakenException;
import org.socialnetwork.codebase.models.Person;
import org.socialnetwork.codebase.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/auth")
@SessionAttributes("currentUser")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/signup")
    public String signup(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
        try {
            Person registeredPerson = this.authService.registerPerson(username, password);
            session.setAttribute("currentUser", registeredPerson);
            return "redirect:/";
        } catch (UsernameAlreadyTakenException usernameAlreadyTakenException) {
            model.addAttribute("error", usernameAlreadyTakenException.getMessage());
            return "login";
        }
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        Person authenticatedPerson = this.authService.authenticatePerson(username, password);
        if (authenticatedPerson != null) {
            model.addAttribute("currentUser", authenticatedPerson);
            return "redirect:/";
        }

        model.addAttribute("error", "Invalid username or password");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(SessionStatus status) {
        status.setComplete();
        return "redirect:/auth/login";
    }
}
