package service;

import entity.Donation;
import entity.Donor;
import entity.DonorQuestionnaire;
import lombok.RequiredArgsConstructor;
import repository.DonationRepository;
import repository.DonorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DonorService {
    private final DonorRepository donorRepository;
    private final DonationRepository donationRepository;

    public Optional<Donor> getDonorById(Integer id) {
        return donorRepository.findById(id);
    }

    @Transactional
    public boolean updateDonorInfo(Integer id, String name, String surname, Integer year,
                                   String bloodType, Integer weight, Integer height) {
        return donorRepository.findById(id).map(donor -> {
            if (name != null) donor.setName(name);
            if (surname != null) donor.setSurname(surname);
            if (year != null) donor.setYear(year);
            if (bloodType != null) donor.setBloodType(bloodType);
            if (weight != null) donor.setWeight(weight);
            if (height != null) donor.setHeight(height);
            donorRepository.save(donor);
            return true;
        }).orElse(false);
    }

    public List<Donation> getDonationHistory(Integer donorId) {
        return donationRepository.findByDonorIdOrderByDonationDateDesc(donorId);
    }

    @Transactional
    public boolean createDonationRecord(Integer donorId, Date donationDate, Integer amount, String location) {
        Donation donation = new Donation();
        donation.setDonorId(donorId);
        donation.setDonationDate(donationDate);
        donation.setAmount(amount);
        donation.setLocation(location);
        donationRepository.save(donation);
        return true;
    }

    public List<Donor> getDonorsByBloodType(String bloodType) {
        return donorRepository.findByBloodType(bloodType);
    }
}