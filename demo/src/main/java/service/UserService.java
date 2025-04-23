package service;

import entity.UserData;
import lombok.RequiredArgsConstructor;
import entity.Donor;
import entity.Recipient;
import entity.UserData;
import repository.DonorRepository;
import repository.RecipientRepository;
import repository.UserDataRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDataRepository userRepository;
    private final DonorRepository donorRepository;
    private final RecipientRepository recipientRepository;

    public Optional<UserData> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<UserData> getUsersByRole(String role) {
        return userRepository.findByRole(role);
    }

    @Transactional
    public boolean registerUser(String username, String password, String role) {
        // Перевіряємо, чи існує вже користувач з таким ім'ям
        if (userRepository.existsByUsername(username)) {
            return false;
        }

        // Створюємо користувача
        UserData userData = new UserData();
        userData.setUsername(username);
        userData.setPassword(password);
        userData.setRole(role);
        userData = userRepository.save(userData);

        // Створюємо відповідний запис в залежності від ролі
        switch (role.toUpperCase()) {
            case "DONOR":
                Donor donor = new Donor();
                donor.setId(userData.getId());
                donor.setName("Новий");
                donor.setSurname("Донор");
                donor.setYear(2000);
                donor.setBloodType("Невідомо");
                donor.setWeight(70);
                donor.setHeight(170);
                donor.setUserData(userData);
                donorRepository.save(donor);
                break;
            case "RECIPIENT":
                Recipient recipient = new Recipient();
                recipient.setId(userData.getId());
                recipient.setName("Новий");
                recipient.setSurname("Реципієнт");
                recipient.setYear(2000);
                recipient.setNeededBloodType("Невідомо");
                recipient.setRequestDate(Date.valueOf(LocalDate.now()));
                recipient.setValidUntil(Date.valueOf(LocalDate.now().plusDays(30)));
                recipient.setMedicalCondition("Не вказано");
                recipient.setUserData(userData);
                recipientRepository.save(recipient);
                break;
        }

        return true;
    }

    @Transactional
    public boolean deleteUserByUsernameAndRole(String username, String role) {
        if (!userRepository.existsByUsername(username)) {
            return false;
        }
        userRepository.deleteByUsernameAndRole(username, role);
        return true;
    }
}