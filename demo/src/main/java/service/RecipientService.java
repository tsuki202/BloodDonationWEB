package service;

import entity.Recipient;
import lombok.RequiredArgsConstructor;
import repository.RecipientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipientService {
    private final RecipientRepository recipientRepository;

    public Optional<Recipient> getRecipientById(Integer id) {
        return recipientRepository.findById(id);
    }

    @Transactional
    public boolean updateRecipientInfo(Recipient updatedRecipient) {
        return recipientRepository.findById(updatedRecipient.getId()).map(recipient -> {
            recipient.setName(updatedRecipient.getName());
            recipient.setSurname(updatedRecipient.getSurname());
            recipient.setYear(updatedRecipient.getYear());
            recipient.setNeededBloodType(updatedRecipient.getNeededBloodType());
            recipient.setMedicalCondition(updatedRecipient.getMedicalCondition());
            recipient.setValidUntil(updatedRecipient.getValidUntil());
            recipientRepository.save(recipient);
            return true;
        }).orElse(false);
    }
}