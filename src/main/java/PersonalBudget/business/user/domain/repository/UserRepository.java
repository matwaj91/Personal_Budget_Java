package PersonalBudget.business.user.domain.repository;

import PersonalBudget.business.user.domain.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByEmail(String email);
    Optional<UserEntity> findUserByEmail(String email);
}
