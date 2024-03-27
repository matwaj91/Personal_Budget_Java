package PersonalBudget.business.expense.domain.repository;

import PersonalBudget.business.expense.domain.model.ExpenseEntity;
import PersonalBudget.common.util.CategorySumDTO;
import PersonalBudget.common.util.ParticularActivityDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {

    @Query(value = "select new PersonalBudget.common.util.CategorySumDTO(e.name, SUM(f.amount)) from ExpenseCategoryEntity e, ExpenseEntity f " +
                    "where f.userId = :userId and e.id = f.expenseCategoryId and f.date between :dateFrom and :dateTo group by e.name")
    List<CategorySumDTO> findAllExpenseCategoriesSum(@Param("userId") Long userId, @Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo);

    @Query(value = "select new PersonalBudget.common.util.ParticularActivityDTO(f.amount, f.date, e.name) from ExpenseEntity f " +
                    "inner join ExpenseCategoryEntity e on f.expenseCategoryId  = e.id where f.userId = :userId and f.date between :dateFrom and :dateTo")
    List<ParticularActivityDTO> findAllParticularExpensesEachCategory(@Param("userId") Long userId, @Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo);
}
