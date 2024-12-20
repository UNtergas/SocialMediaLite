package org.socialnetwork.codebase.controllers;

import org.socialnetwork.codebase.DTO.RelationDTO;
import org.socialnetwork.codebase.DTO.UserDTO;
import org.socialnetwork.codebase.models.RelationType;
import org.socialnetwork.codebase.models.User;
import org.socialnetwork.codebase.service.RelationService;
import org.socialnetwork.codebase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
@SessionAttributes("currentUser")
public class DashboardController {
    private final UserService userService;
    private final RelationService relationService;

    @Autowired
    public DashboardController(UserService userService, RelationService relationService) {
        this.userService = userService;
        this.relationService = relationService;
    }

    @GetMapping("")
    public String showDashboard(@SessionAttribute(value = "currentUser", required = false) User currentUser, Model model) {
        if (currentUser == null) {
            return "redirect:/auth/login";
        }
        List<UserDTO> userDTOs = userService.getAllUsers()
                .stream()
                .map(user -> new UserDTO(user.getUserID(), user.getUsername(), user.getDescription()))
                .toList();

        List<RelationDTO> relationDTOs = relationService.getAllRelations()
                .stream()
                .map(relation -> new RelationDTO(
                        relation.getUserInit().getUsername(),
                        relation.getUserRecv().getUsername(),
                        relation.getRelationType().toString()
                ))
                .toList();

        List<UserDTO> usersNotConnectedToCurrentUserDTOs =
                userService.getUsersNotConnectedToUser(currentUser.getUsername())
                    .stream()
                    .map(user -> new UserDTO(user.getUserID(), user.getUsername(), user.getDescription()))
                    .toList();

        List<UserDTO> userWithRelationToSpecificUserDTOs =
                userService.getUserWithRelationToSpecificUser(currentUser.getUsername())
                        .stream()
                        .map(user -> new UserDTO(user.getUserID(), user.getUsername(), user.getDescription()))
                        .toList();
        List<UserDTO> userWithNoRelations = userService.getUsersWithNoRelations()
                .stream()
                .map(user -> new UserDTO(user.getUserID(), user.getUsername(), user.getDescription()))
                .toList();
        List<UserDTO> userWithMultipleRelationTypes = userService.getUserWithMultipleRelationTypes()
                .stream()
                .map(user -> new UserDTO(user.getUserID(), user.getUsername(), user.getDescription()))
                .toList();

        model.addAttribute("users", userDTOs);
        model.addAttribute("relations", relationDTOs);
        model.addAttribute("usersNotConnectedToCurrentUser", usersNotConnectedToCurrentUserDTOs);
        model.addAttribute("relationTypes", RelationType.values());
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("usersWithRelationToSpecificUser", userWithRelationToSpecificUserDTOs);
        model.addAttribute("usersWithNoRelations", userWithNoRelations);
        model.addAttribute("usersWithMultipleRelation", userWithMultipleRelationTypes);
        return "dashboard";
    }

    @PostMapping("/connect")
    public String connectUsers(
            @SessionAttribute(value = "currentUser", required = false) User currentUser,
            @RequestParam("username") String targetUsername,
            @RequestParam("relationType") RelationType relationType,
            Model model
    ) {
        if (currentUser == null) {
            return "redirect:/auth/login"; // Redirect if the user is not logged in
        }

        // Call the relation service to create the relation
        relationService.createRelationByUsernames(
                relationType,
                currentUser.getUsername(),
                targetUsername
        );

        // Redirect back to the dashboard to reflect changes
        return "redirect:/";
    }

    @PostMapping("/description")
    public String findUserByDescription(
            @SessionAttribute(value = "currentUser", required = false) User currentUser,
            @RequestParam("keyword") String keyword,
            RedirectAttributes redirectAttributes
    ){
        if (currentUser == null) {
            return "redirect:/auth/login"; // Redirect if the user is not logged in
        }

        List<UserDTO> userDTOs = userService.getUserByDescription(keyword)
                .stream()
                .map(user -> new UserDTO(user.getUserID(), user.getUsername(), user.getDescription()))
                .toList();

        redirectAttributes.addFlashAttribute("userFromReq", userDTOs);
        return "redirect:/?view=description";
    }
    @PostMapping("/count")
    public String findUserWithMoreThanOrEqualsToNRelations(
            @SessionAttribute(value = "currentUser", required = false) User currentUser,
            @RequestParam("relationCount") int count,
            RedirectAttributes redirectAttributes
    ){
        if (currentUser == null) {
            return "redirect:/auth/login"; // Redirect if the user is not logged in
        }

        List<UserDTO> userDTOs = userService.getUsersWithMoreThanOrEqualsToNRelations(count)
                .stream()
                .map(user -> new UserDTO(user.getUserID(), user.getUsername(), user.getDescription()))
                .toList();

        redirectAttributes.addFlashAttribute("userFromReq", userDTOs);
        return "redirect:/?view=count";
    }
}
