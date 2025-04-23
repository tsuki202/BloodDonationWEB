package view;

import entity.Donor;
import entity.Recipient;
import entity.UserData;
import lombok.RequiredArgsConstructor;
import service.DonorService;
import service.RecipientService;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class RecipientView {
    private final RecipientService recipientService;
    private final DonorService donorService;
    private final Scanner scanner = new Scanner(System.in);

    public void showMenu(Recipient recipient, UserData userData) {
        while (true) {
            System.out.println("\n🏥 Меню реципієнта:");
            System.out.println("1 - Переглянути профіль");
            System.out.println("2 - Оновити особисту інформацію");
            System.out.println("3 - Знайти потенційних донорів");
            System.out.println("4 - Вийти в головне меню");
            System.out.print("Ваш вибір: ");

            String input = scanner.nextLine();
            switch (input) {
                case "1" -> displayProfile(recipient);
                case "2" -> updateRecipientInfo(recipient);
                case "3" -> findPotentialDonors(recipient);
                case "4" -> { return; }
                default -> System.out.println("❌ Невірний вибір. Спробуйте ще раз.");
            }
        }
    }

    private void displayProfile(Recipient recipient) {
        System.out.println("\n📋 Ваш профіль:");
        System.out.println("🔹 Ім'я: " + recipient.getName());
        System.out.println("🔹 Прізвище: " + recipient.getSurname());
        System.out.println("🔹 Рік народження: " + recipient.getYear());
        System.out.println("🔹 Потрібна група крові: " + recipient.getNeededBloodType());
        System.out.println("🔹 Дата запиту: " + recipient.getRequestDate());
        System.out.println("🔹 Дійсний до: " + recipient.getValidUntil());
        System.out.println("🔹 Медичний стан: " + recipient.getMedicalCondition());
    }

    private void updateRecipientInfo(Recipient recipient) {
        System.out.println("\n✏️ Оновлення профілю (порожні поля залишаться без змін):");

        System.out.print("Ім'я [" + recipient.getName() + "]: ");
        String name = scanner.nextLine().trim();

        System.out.print("Прізвище [" + recipient.getSurname() + "]: ");
        String surname = scanner.nextLine().trim();

        System.out.print("Рік народження [" + recipient.getYear() + "]: ");
        String yearStr = scanner.nextLine().trim();

        System.out.print("Потрібна група крові [" + recipient.getNeededBloodType() + "]: ");
        String bloodType = scanner.nextLine().trim();

        System.out.print("Медичний стан [" + recipient.getMedicalCondition() + "]: ");
        String medicalCondition = scanner.nextLine().trim();

        System.out.print("Дійсний до (YYYY-MM-DD) [" + recipient.getValidUntil() + "]: ");
        String validUntilStr = scanner.nextLine().trim();

        // Створюємо новий об'єкт з оновленими даними
        Recipient updatedRecipient = new Recipient();
        updatedRecipient.setId(recipient.getId());
        updatedRecipient.setName(name.isEmpty() ? recipient.getName() : name);
        updatedRecipient.setSurname(surname.isEmpty() ? recipient.getSurname() : surname);
        updatedRecipient.setYear(yearStr.isEmpty() ? recipient.getYear() : Integer.parseInt(yearStr));
        updatedRecipient.setNeededBloodType(bloodType.isEmpty() ? recipient.getNeededBloodType() : bloodType);
        updatedRecipient.setMedicalCondition(medicalCondition.isEmpty() ? recipient.getMedicalCondition() : medicalCondition);
        updatedRecipient.setRequestDate(recipient.getRequestDate());
        updatedRecipient.setValidUntil(validUntilStr.isEmpty() ? recipient.getValidUntil() : Date.valueOf(validUntilStr));

        boolean success = recipientService.updateRecipientInfo(updatedRecipient);
        if (success) {
            System.out.println("✅ Профіль успішно оновлено.");
        } else {
            System.out.println("❌ Помилка при оновленні профілю.");
        }
    }

    private void findPotentialDonors(Recipient recipient) {
        System.out.println("\n🔍 Пошук потенційних донорів для групи крові: " + recipient.getNeededBloodType());

        List<Donor> compatibleDonors = donorService.getDonorsByBloodType(recipient.getNeededBloodType());

        if (compatibleDonors.isEmpty()) {
            System.out.println("❌ На жаль, донорів з потрібною групою крові наразі немає в системі.");
        } else {
            System.out.println("✅ Знайдено потенційних донорів: " + compatibleDonors.size());
            for (Donor donor : compatibleDonors) {
                System.out.println("🔹 " + donor.getName() + " " + donor.getSurname() +
                        " (Група крові: " + donor.getBloodType() + ")");
            }
            System.out.println("\nЗверніться до адміністратора для зв'язку з донорами.");
        }
    }
}