package repository;

import entity.Donor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DonorRepository extends JpaRepository<Donor, Integer> {
    List<Donor> findByBloodType(String bloodType);
}
