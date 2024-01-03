package PersonalBudget.business.income.domain.repository;

import PersonalBudget.business.income.domain.model.IncomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeRepository extends JpaRepository<IncomeEntity, Long> {

}
