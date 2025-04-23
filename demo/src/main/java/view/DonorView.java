package view;

import entity.Donation;
import entity.Donor;
import entity.UserData;
import lombok.RequiredArgsConstructor;
import service.DonorService;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class DonorView {
    private final DonorService donorService;
    private final Scanner scanner = new Scanner(System.in);

    public void showMenu(Donor donor, UserData userData) {
        while (true) {
            System.out.println("\n🧑‍⚕️ Меню донора:");
            System.out.println("1 - Переглянути профіль");
            System.out.println("2 - Оновити особисту інформацію");
            System.out.println("3 - Переглянути історію донорства");
            System.out.println("4 - Додати нову донацію");
            System.out.println("5 - Вийти в головне меню");
            System.out.print("Ваш вибір: ");

            String input = scanner.nextLine();
            switch (input) {
                case "1" -> displayProfile(donor);
                case "2" -> updateDonorInfo(donor);
                case "3" -> displayDonationHistory(donor.getId());
                case "4" -> addNewDonation(donor.getId());
                case "5" -> { return; }
                default -> System.out.println("❌ Невірний вибір. Спробуйте ще раз.");
            }
        }
    }

    private void displayProfile(Donor donor) {
        System.out.println("\n📋 Ваш профіль:");
        System.out.println("🔹 Ім'я: " + donor.getName());
        System.out.println("🔹 Прізвище: " + donor.getSurname());
        System.out.println("🔹 Рік народження: " + donor.getYear());
        System.out.println("🔹 Група крові: " + donor.getBloodType());
        System.out.println("🔹 Вага (кг): " + donor.getWeight());
        System.out.println("🔹 Зріст (см): " + donor.getHeight());
    }

    private void updateDonorInfo(Donor donor) {
        System.out.println("\n✏️ Оновлення профілю (порожні поля залишаться без змін):");

        System.out.print("Ім'я [" + donor.getName() + "]: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) name = null;

        System.out.print("Прізвище [" + donor.getSurname() + "]: ");
        String surname = scanner.nextLine().trim();
        if (surname.isEmpty()) surname = null;

        System.out.print("Рік народження [" + donor.getYear() + "]: ");
        String yearStr = scanner.nextLine().trim();
        Integer year = yearStr.isEmpty() ? null : Integer.parseInt(yearStr);

        System.out.print("Група крові [" + donor.getBloodType() + "]: ");
        String bloodType = scanner.nextLine().trim();
        if (bloodType.isEmpty()) bloodType = null;

        System.out.print("Вага (кг) [" + donor.getWeight() + "]: ");
        String weightStr = scanner.nextLine().trim();
        Integer weight = weightStr.isEmpty() ? null : Integer.parseInt(weightStr);

        System.out.print("Зріст (см) [" + donor.getHeight() + "]: ");
        String heightStr = scanner.nextLine().trim();
        Integer height = heightStr.isEmpty() ? null : Integer.parseInt(heightStr);

        boolean success = donorService.updateDonorInfo(donor.getId(), name, surname, year, bloodType, weight, height);
        if (success) {
            System.out.println("✅ Профіль успішно оновлено.");
        } else {
            System.out.println("❌ Помилка при оновленні профілю.");
        }
    }

    private void displayDonationHistory(Integer donorId) {
        List<Donation> donations = donorService.getDonationHistory(donorId);
        System.out.println("\n📜 Історія донацій:");

        if (donations.isEmpty()) {
            System.out.println("Ви ще не здавали кров. Заплануйте свою першу донацію!");
        } else {
            for (Donation donation : donations) {
                System.out.println("🔹 Дата: " + donation.getDonationDate() +
                        ", Кількість: " + donation.getAmount() + " мл, Місце: " + donation.getLocation());
            }
        }
    }

    private void addNewDonation(Integer donorId) {
        System.out.println("\n➕ Додавання нової донації:");

        System.out.print("Дата (YYYY-MM-DD) [сьогодні]: ");
        String dateStr = scanner.nextLine().trim();
        Date donationDate = dateStr.isEmpty()
                ? Date.valueOf(LocalDate.now())
                : Date.valueOf(dateStr);

        System.out.print("Кількість мл [450]: ");
        String amountStr = scanner.nextLine().trim();
        Integer amount = amountStr.isEmpty() ? 450 : Integer.parseInt(amountStr);

        System.out.print("Місце донації: ");
        String location = scanner.nextLine().trim();

        boolean success = donorService.createDonationRecord(donorId, donationDate, amount, location);
        if (success) {
            System.out.println("✅ Донацію успішно додано до системи.");
        } else {
            System.out.println("❌ Помилка при додаванні донації.");
        }
    }
}