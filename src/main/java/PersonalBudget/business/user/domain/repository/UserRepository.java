package PersonalBudget.business.user.domain.repository;

import PersonalBudget.business.user.domain.model.UserAccountEntity;
import PersonalBudget.business.user.dto.UserProfileDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserAccountEntity, Long> {

    boolean existsByEmail(String email);

    Optional<UserAccountEntity> findUserByEmail(String email);

    @Query(value = "select e.id from UserAccountEntity e where e.email = :email")
    Optional<Long> findIdByEmail (@Param("email") String email);

    @Query("select new PersonalBudget.business.user.dto.UserProfileDTO(f.name, f.email, f.password) from UserAccountEntity f where f.id = :userId")
    UserProfileDTO findUserProfileDetailsByUserId(@Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query(value = "update user_account set name = :name where id = :userId", nativeQuery = true)
    void setUserName(@Param("userId") Long userId, @Param("name") String name);

    @Modifying
    @Transactional
    @Query(value = "update user_account set password = :password where id = :userId", nativeQuery = true)
    void setUserPassword(@Param("userId") Long userId, @Param("password") String password);

    @Modifying
    @Transactional
    @Query(value = "update user_account set name = :name, password = :password where id = :userId", nativeQuery = true)
    void setUserNameAndPassword(@Param("userId") Long userId, @Param("name") String name, @Param("password") String password);
}
