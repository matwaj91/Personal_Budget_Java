package PersonalBudget.business.income.domain.repository;

import PersonalBudget.business.income.domain.model.IncomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeRepository extends JpaRepository<IncomeEntity, Long> {

}
