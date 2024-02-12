package PersonalBudget.business.expense.domain.repository;

import PersonalBudget.business.expense.domain.model.ExpenseCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategoryEntity, Long> {

    @Query(value = "select e.name from ExpenseCategoryEntity e where e.userId = :userId")
    List<String> findAllExpenseCategoryNames(@Param("userId") Long userId);

    @Query(value = "select e.id from ExpenseCategoryEntity e where e.userId = :userId AND e.name = :category")
    Optional<ExpenseCategoryEntity> findExpenseCategoryIdByUserIdAndCategoryName(@Param("userId") Long userId, @Param("category") String category);
}
