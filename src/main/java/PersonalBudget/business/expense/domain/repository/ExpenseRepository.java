package PersonalBudget.business.expense.domain.repository;

import PersonalBudget.business.expense.domain.model.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {
}
