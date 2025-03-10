package PersonalBudget.business.expense.domain.repository;

import PersonalBudget.business.expense.domain.model.ExpenseCategoryEntity;
import PersonalBudget.business.expense.dto.ExpenseCategoryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategoryEntity, Long> {

    @Query(value = "select new PersonalBudget.business.expense.dto.ExpenseCategoryDTO(e.id, e.name, e.limitAmount) from ExpenseCategoryEntity e where e.userId = :userId")
    List<ExpenseCategoryDTO> findAllExpensesCategoriesByUserId(@Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query(value = "delete from expense_category f where f.user_id = :userId and f.id = :id", nativeQuery = true)
    void deleteParticularExpenseCategory(@Param("userId") Long userId, @Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "update expense_category set limit_amount = :limit_Amount where id = :id", nativeQuery = true)
    void setExpenseCategoryLimit(@Param("id") Long id, @Param("limit_Amount") BigDecimal limitAmount);

    @Modifying
    @Transactional
    @Query(value = "delete from expense_category f where f.user_id = :userId", nativeQuery = true)
    void deleteExpenseCategoriesByUserId(@Param("userId") Long userId);
}
