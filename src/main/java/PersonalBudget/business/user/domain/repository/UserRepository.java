package PersonalBudget.business.user.domain.repository;

import PersonalBudget.business.user.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserByEmail(String email);
    Optional<User> findUserByEmailAndPassword(String email, String password);
}
