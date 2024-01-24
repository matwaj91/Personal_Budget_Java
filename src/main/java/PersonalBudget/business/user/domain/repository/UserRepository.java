package PersonalBudget.business.user.domain.repository;

import PersonalBudget.business.user.domain.model.UserAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserAccountEntity, Long> {

    boolean existsByEmail(String email);
    Optional<UserAccountEntity> findUserByEmail(String email);

    @Query(value = "select e.id from UserAccountEntity e where e.email = :email")
    Optional<Long> findIdByEmail (@Param("email") String email);
}
