package controller.web;

import entity.Donation;
import entity.Donor;
import entity.UserData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import service.DonorService;
import service.UserService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/donor")
@RequiredArgsConstructor
public class DonorWebController {
    private final DonorService donorService;
    private final UserService userService;

    @GetMapping("/dashboard")
    public String dashboard(@RequestParam Integer userId, Model model) {
        Optional<Donor> donorOptional = donorService.getDonorById(userId);

        if (donorOptional.isPresent()) {
            model.addAttribute("donor", donorOptional.get());
            return "donor/dashboard";
        } else {
            return "redirect:/login?error=Профіль не знайдено";
        }
    }

    @GetMapping("/profile")
    public String profile(@RequestParam Integer userId, Model model) {
        Optional<Donor> donorOptional = donorService.getDonorById(userId);

        if (donorOptional.isPresent()) {
            model.addAttribute("donor", donorOptional.get());
            return "donor/profile";
        } else {
            return "redirect:/login?error=Профіль не знайдено";
        }
    }

    @PostMapping("/update-profile")
    public String updateProfile(
            @RequestParam Integer userId,
            @RequestParam String name,
            @RequestParam String surname,
            @RequestParam Integer year,
            @RequestParam String bloodType,
            @RequestParam Integer weight,
            @RequestParam Integer height,
            Model model) {

        boolean success = donorService.updateDonorInfo(userId, name, surname, year, bloodType, weight, height);

        if (success) {
            return "redirect:/donor/profile?userId=" + userId + "&message=Профіль успішно оновлено";
        } else {
            return "redirect:/donor/profile?userId=" + userId + "&error=Помилка при оновленні профілю";
        }
    }

    @GetMapping("/donations")
    public String donationHistory(@RequestParam Integer userId, Model model) {
        Optional<Donor> donorOptional = donorService.getDonorById(userId);

        if (donorOptional.isPresent()) {
            List<Donation> donations = donorService.getDonationHistory(userId);
            model.addAttribute("donor", donorOptional.get());
            model.addAttribute("donations", donations);
            return "donor/donations";
        } else {
            return "redirect:/login?error=Профіль не знайдено";
        }
    }

//    @GetMapping("/add-donation")
//    public String showAddDonationForm(@RequestParam Integer userId, Model model) {
//        Optional<Donor> donorOptional = donorService.getDonorById(userId);
//
//        if (donorOptional.isPresent()) {
//            model.addAttribute("donor", donorOptional.get());
//            model.addAttribute("today", Date.valueOf(LocalDate.now()));
//            return "donor/add-donation";
//        } else {
//            return "redirect:/auth/login?error=Профіль не знайдено";
//        }
//    }

    @PostMapping("/add-donation")
    public String addDonation(
            @RequestParam Integer userId,
            @RequestParam Date donationDate,
            @RequestParam Integer amount,
            @RequestParam String location,
            Model model) {

        boolean success = donorService.createDonationRecord(userId, donationDate, amount, location);

        if (success) {
            return "redirect:/donor/donations?userId=" + userId + "&message=Донацію успішно додано";
        } else {
            return "redirect:/donor/add-donation?userId=" + userId + "&error=Помилка при додаванні донації";
        }
    }
}