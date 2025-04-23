package repository;

import entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserDataRepository extends JpaRepository<UserData, Integer> {
    Optional<UserData> findByUsername(String username);
    List<UserData> findByRole(String role);
    boolean existsByUsername(String username);
    void deleteByUsernameAndRole(String username, String role);
}
