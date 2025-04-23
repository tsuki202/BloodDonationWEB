package controller.web;

import entity.Donor;
import entity.Recipient;
import entity.UserData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import service.DonorService;
import service.RecipientService;
import service.UserService;

import java.util.Optional;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthWebController {
    private final UserService userService;
    private final DonorService donorService;
    private final RecipientService recipientService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        Optional<UserData> userOptional = userService.getUserByUsername(username);

        if (userOptional.isPresent()) {
            UserData user = userOptional.get();
            if (user.getPassword().equals(password)) {
                // Successful login
                switch (user.getRole().toUpperCase()) {
                    case "ADMIN":
                        return "redirect:/admin/dashboard?userId=" + user.getId();
                    case "DONOR":
                        return "redirect:/donor/dashboard?userId=" + user.getId();
                    case "RECIPIENT":
                        return "redirect:/recipient/dashboard?userId=" + user.getId();
                    default:
                        model.addAttribute("error", "Невідома роль користувача");
                        return "auth/login";
                }
            } else {
                model.addAttribute("error", "Невірний пароль");
                return "auth/login";
            }
        } else {
            model.addAttribute("error", "Користувач не знайдений");
            return "auth/login";
        }
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String role,
            Model model) {

        boolean success = userService.registerUser(username, password, role);

        if (success) {
            model.addAttribute("message", "Реєстрація успішна. Будь ласка, увійдіть у систему.");
            return "auth/login";
        } else {
            model.addAttribute("error", "Помилка при реєстрації. Можливо, логін вже існує.");
            return "auth/register";
        }
    }
}