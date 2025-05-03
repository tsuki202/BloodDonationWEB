package controller.web;

import entity.UserData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminWebController {
    private final UserService userService;

    @GetMapping("/dashboard")
    public String dashboard(@RequestParam Integer userId, Model model) {
        model.addAttribute("userId", userId);
        return "admin/dashboard";
    }

    @GetMapping("/users")
    public String listUsers(@RequestParam Integer userId, @RequestParam String role, Model model) {
        List<UserData> users = userService.getUsersByRole(role);

        model.addAttribute("userId", userId);
        model.addAttribute("role", role);
        model.addAttribute("users", users);
        return "admin/users";
    }

    @PostMapping("/delete-user")
    public String deleteUser(
            @RequestParam Integer adminId,
            @RequestParam String username,
            @RequestParam String role,
            Model model) {

        boolean success = userService.deleteUserByUsernameAndRole(username, role);

        if (success) {
            return "redirect:/admin/users?userId=" + adminId + "&role=" + role + "&message=Користувача успішно видалено";
        } else {
            return "redirect:/admin/users?userId=" + adminId + "&role=" + role + "&error=Помилка при видаленні користувача";
        }
    }
}