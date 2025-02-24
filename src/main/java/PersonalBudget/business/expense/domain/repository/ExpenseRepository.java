package PersonalBudget.business.expense.domain.repository;

import PersonalBudget.business.expense.domain.model.ExpenseEntity;
import PersonalBudget.common.util.ParticularActivityDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {

    @Query(value = "select new PersonalBudget.common.util.ParticularActivityDTO(f.amount, f.expenseDate, e.name) from ExpenseEntity f " +
                    "inner join ExpenseCategoryEntity e on f.expenseCategoryId  = e.id where f.userId = :userId and f.expenseDate between :dateFrom and :dateTo")
    List<ParticularActivityDTO> findAllUserParticularExpensesEachCategory(@Param("userId") Long userId, @Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo);

    @Query(value = "SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM expense e WHERE e.expense_category_id = :id", nativeQuery = true)
    boolean findRelatedExpenseByCategoryId(@Param("id") Long id);

    @Query(value = "SELECT CASE WHEN COUNT(h) > 0 THEN true ELSE false END FROM expense h WHERE h.payment_method_id = :id", nativeQuery = true)
    boolean findRelatedExpenseByPaymentMethodId(@Param("id") Long id);

    @Query(value = "SELECT SUM(amount) AS sum FROM expense e WHERE e.expense_category_id = :selectedExpenseCategory and e.expense_date between :dateFrom and :dateTo", nativeQuery = true)
    BigDecimal findCurrentMonthExpenseSum(Long selectedExpenseCategory, LocalDate dateFrom, LocalDate dateTo);

    @Modifying
    @Transactional
    @Query(value = "delete from expense f where f.user_id = :userId", nativeQuery = true)
    void deleteAllExpensesByUserId(@Param("userId") Long userId);

}
