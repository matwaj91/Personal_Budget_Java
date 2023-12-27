package PersonalBudget.business.income.domain.repository;

import PersonalBudget.business.income.domain.model.IncomeCategoryEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeCategoryRepository extends JpaRepository<IncomeCategoryEntity, Long> {

    @Modifying
    @Transactional
    @Query(value = "insert into incomes_category_assigned_to_users (user_id, name) " +
            "values (:userId, 'Salary'), " +
            "(:userId, 'Interest'), " +
            "(:userId, 'Allegro'), " +
            "(:userId, 'Another')",
            nativeQuery = true)
    void addDefaultCategories(@Param("userId") Long userId);

    @Query(value = "select name from incomes_category_assigned_to_users " +
            "where user_id = :userId", nativeQuery = true)
    List<String> getAllIncomeCategories(@Param("userId") Long userId);

    @Query(value = "select id from incomes_category_assigned_to_users " +
            "where user_id = :userId AND name = :category", nativeQuery = true)
    Long getIdOfParticularCategory(@Param("userId") Long userId, @Param("category") String category);
}

