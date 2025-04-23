package com.example.demo;

import controller.AuthController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConsoleApplication {

    private static AuthController authController;

    @Autowired
    public ConsoleApplication(AuthController authController) {
        ConsoleApplication.authController = authController;
    }

    // Для виклику з Main без DI
    public ConsoleApplication() {}

    public void run() {
        if (authController != null) {
            authController.start();
        } else {
            System.out.println("❌ Помилка: контролер авторизації не був ініціалізований");
        }
    }
}