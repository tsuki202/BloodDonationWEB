package controller.web;

import entity.Donation;
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

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainWebController {

    private final UserService userService;
    private final DonorService donorService;
    private final RecipientService recipientService;

    // 🌐 Головна сторінка
    @GetMapping("/")
    public String home() {
        return "index";
    }

    // 🔐 Вхід
    @GetMapping("/login")
    public String loginPage() {
        return "Login";
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
                        return "/login";
                }
            } else {
                model.addAttribute("error", "Невірний пароль");
                return "/login";
            }
        } else {
            model.addAttribute("error", "Користувач не знайдений");
            return "/login";
        }
    }
    @GetMapping("/profile")
    public String donorRegistrationPage() {
        return "profile"; // бо це файл profile.html
    }

    @GetMapping("/add-donation")
    public String donationSchedulePage() {
        return "add-donation"; // бо це файл add-donationD.html
    }

    @GetMapping("/donation-history")
    public String bloodInventoryPage() {
        return "donation-history"; // бо це файл donation-history.html
    }

    // 📝 Реєстрація
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password, @RequestParam String role, Model model) {
        boolean success = userService.registerUser(username, password, role);

        if (success) {
            model.addAttribute("message", "Реєстрація успішна. Будь ласка, увійдіть у систему.");
            return "/login";
        } else {
            model.addAttribute("error", "Помилка при реєстрації. Можливо, логін вже існує.");
            return "/register";
        }
    }
//
//    // 📋 Панель адміністратора
//    @GetMapping("/admin/dashboard")
//    public String adminDashboard(@RequestParam Integer userId, Model model) {
//        model.addAttribute("userId", userId);
//        return "admin/dashboard";
//    }

    // 👤 Список користувачів для адміністратора
//    @GetMapping("/admin/users")
//    public String adminUsers(@RequestParam Integer userId, @RequestParam String role, Model model) {
//        List<UserData> users = userService.getUsersByRole(role);
//        model.addAttribute("userId", userId);
//        model.addAttribute("role", role);
//        model.addAttribute("users", users);
//        return "admin/users";
//    }

//    @PostMapping("/admin/delete-user")
//    public String deleteUser(@RequestParam Integer adminId, @RequestParam String username, @RequestParam String role) {
//        boolean success = userService.deleteUserByUsernameAndRole(username, role);
//
//        if (success) {
//            return "redirect:/admin/users?userId=" + adminId + "&role=" + role + "&message=Користувача успішно видалено";
//        } else {
//            return "redirect:/admin/users?userId=" + adminId + "&role=" + role + "&error=Помилка при видаленні користувача";
//        }
//    }
//    // 👤 Профіль донора
//    @GetMapping("/donor/dashboard")
//    public String donorDashboard(@RequestParam Integer userId, Model model) {
//        Optional<Donor> donorOptional = donorService.getDonorById(userId);
//        return donorOptional.map(donor -> {
//            model.addAttribute("donor", donor);
//            return "donor/dashboard";
//        }).orElse("redirect:/auth/login?error=Профіль не знайдено");
//    }
//
//    @GetMapping("/donor/profile")
//    public String donorProfile(@RequestParam Integer userId, Model model) {
//        Optional<Donor> donorOptional = donorService.getDonorById(userId);
//        return donorOptional.map(donor -> {
//            model.addAttribute("donor", donor);
//            return "donor/profile";
//        }).orElse("redirect:/auth/login?error=Профіль не знайдено");
//    }

//    @PostMapping("/donor/update-profile")
//    public String updateProfile(@RequestParam Integer userId, @RequestParam String name, @RequestParam String surname,
//                                @RequestParam Integer year, @RequestParam String bloodType, @RequestParam Integer weight,
//                                @RequestParam Integer height) {
//        boolean success = donorService.updateDonorInfo(userId, name, surname, year, bloodType, weight, height);
//        if (success) {
//            return "redirect:/donor/profile?userId=" + userId + "&message=Профіль успішно оновлено";
//        } else {
//            return "redirect:/donor/profile?userId=" + userId + "&error=Помилка при оновленні профілю";
//        }
//    }

//    // 🩸 Історія донацій
//    @GetMapping("/donor/donations")
//    public String donationHistory(@RequestParam Integer userId, Model model) {
//        Optional<Donor> donorOptional = donorService.getDonorById(userId);
//        if (donorOptional.isPresent()) {
//            List<Donation> donations = donorService.getDonationHistory(userId);
//            model.addAttribute("donor", donorOptional.get());
//            model.addAttribute("donations", donations);
//            return "donor/donations";
//        } else {
//            return "redirect:/auth/login?error=Профіль не знайдено";
//        }
//    }

//    @GetMapping("/donor/add-donation")
//    public String showAddDonationForm(@RequestParam Integer userId, Model model) {
//        Optional<Donor> donorOptional = donorService.getDonorById(userId);
//        if (donorOptional.isPresent()) {
//            model.addAttribute("donor", donorOptional.get());
//            model.addAttribute("today", Date.valueOf(LocalDate.now()));
//            return "donor/add-donation";
//        } else {
//            return "redirect:/auth/login?error=Профіль не знайдено";
//        }
//    }

//    @PostMapping("/donor/add-donation")
//    public String addDonation(@RequestParam Integer userId, @RequestParam Date donationDate,
//                              @RequestParam Integer amount, @RequestParam String location) {
//        boolean success = donorService.createDonationRecord(userId, donationDate, amount, location);
//        if (success) {
//            return "redirect:/donor/donations?userId=" + userId + "&message=Донацію успішно додано";
//        } else {
//            return "redirect:/donor/add-donation?userId=" + userId + "&error=Помилка при додаванні донації";
//        }
//    }
//
//    // 🎯 Панель реципієнта
//    @GetMapping("/recipient/dashboard")
//    public String recipientDashboard() {
//        return "recipient/dashboard";
//    }
}