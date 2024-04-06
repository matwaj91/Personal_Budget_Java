package PersonalBudget.business.expense.domain.repository;

import PersonalBudget.business.expense.domain.model.ExpenseCategoryEntity;
import PersonalBudget.business.expense.dto.ExpenseCategoryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategoryEntity, Long> {

    @Query(value = "select new PersonalBudget.business.expense.dto.ExpenseCategoryDTO(e.id, e.name) from ExpenseCategoryEntity e where e.userId = :userId")
    List<ExpenseCategoryDTO> findAllExpensesCategoriesByUserId(@Param("userId") Long userId);
}
