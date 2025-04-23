package controller;

import entity.Donor;
import entity.Recipient;
import entity.UserData;
import lombok.RequiredArgsConstructor;
import service.DonorService;
import service.RecipientService;
import service.UserService;
import view.AdminView;
import view.DonorView;
import view.RecipientView;
import org.springframework.stereotype.Controller;

import java.util.Optional;
import java.util.Scanner;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final DonorService donorService;
    private final RecipientService recipientService;
    private final AdminView adminView;
    private final DonorView donorView;
    private final RecipientView recipientView;

    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("\n\uD83D\uDCCC Оберіть дію:");
            System.out.println("1️⃣ - Вхід");
            System.out.println("2️⃣ - Реєстрація");
            System.out.println("3️⃣ - Вихід");
            System.out.print("🔹 Ваш вибір: ");

            String input = scanner.nextLine();
            if (!input.matches("\\d+")) {
                System.out.println("❌ Невірний ввід. Введіть число.");
                continue;
            }

            int choice = Integer.parseInt(input);

            switch (choice) {
                case 1 -> loginUser();
                case 2 -> registerUser();
                case 3 -> exitProgram();
                default -> System.out.println("❌ Невірний вибір! Спробуйте ще раз.");
            }
        }
    }

    private void loginUser() {
        System.out.print("\n\uD83D\uDC64 Введіть логін: ");
        String username = scanner.nextLine().trim();
        System.out.print("\uD83D\uDD11 Введіть пароль: ");
        String password = scanner.nextLine();

        Optional<UserData> userOptional = userService.getUserByUsername(username);

        if (userOptional.isPresent()) {
            UserData user = userOptional.get();
            if (user.getPassword().equals(password)) {
                System.out.println("✅ Вхід успішний!");

                switch (user.getRole().toUpperCase()) {
                    case "ADMIN" -> adminView.showMenu(user.getId(), user.getUsername());
                    case "DONOR" -> {
                        Optional<Donor> donorOptional = donorService.getDonorById(user.getId());
                        if (donorOptional.isPresent()) {
                            donorView.showMenu(donorOptional.get(), user);
                        } else {
                            System.out.println("❌ Помилка завантаження профілю донора.");
                        }
                    }
                    case "RECIPIENT" -> {
                        Optional<Recipient> recipientOptional = recipientService.getRecipientById(user.getId());
                        if (recipientOptional.isPresent()) {
                            recipientView.showMenu(recipientOptional.get(), user);
                        } else {
                            System.out.println("❌ Помилка завантаження профілю реципієнта.");
                        }
                    }
                    default -> System.out.println("\uD83D\uDC64 Ви увійшли як " + user.getRole() + ". Немає додаткового меню.");
                }
            } else {
                System.out.println("❌ Невірний пароль.");
            }
        } else {
            System.out.println("❌ Користувач не знайдений.");
        }
    }

    private void registerUser() {
        System.out.print("\n\uD83C\uDD95 Введіть логін: ");
        String username = scanner.nextLine().trim();
        System.out.print("\uD83D\uDD10 Введіть пароль: ");
        String password = scanner.nextLine();

        String role = null;
        while (role == null) {
            System.out.println("\uD83C\uDFBE Оберіть роль:");
            System.out.println("1 - Адмін");
            System.out.println("2 - Донор");
            System.out.println("3 - Реципієнт");
            System.out.print("Ваш вибір: ");

            String input = scanner.nextLine();
            switch (input) {
                case "1" -> role = "ADMIN";
                case "2" -> role = "DONOR";
                case "3" -> role = "RECIPIENT";
                default -> System.out.println("❌ Невірний вибір. Спробуйте ще раз.");
            }
        }

        boolean success = userService.registerUser(username, password, role);
        if (success) {
            System.out.println("✅ Реєстрація успішна.");
        } else {
            System.out.println("❌ Помилка при реєстрації. Можливо, логін вже існує.");
        }
    }

    private void exitProgram() {
        System.out.println("\uD83D\uDC4B До побачення!");
        System.exit(0);
    }
}