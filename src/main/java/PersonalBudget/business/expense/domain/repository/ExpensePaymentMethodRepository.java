package PersonalBudget.business.expense.domain.repository;

import PersonalBudget.business.expense.domain.model.ExpensePaymentMethodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExpensePaymentMethodRepository extends JpaRepository<ExpensePaymentMethodEntity, Long> {

    @Query(value = "select e.name from ExpensePaymentMethodEntity e where e.userId = :userId")
    List<String> findAllPaymentMethodName(@Param("userId") Long userId);

    @Query(value = "select e.id from ExpensePaymentMethodEntity e where e.userId = :userId AND e.name = :method")
    Optional<Long> findPaymentMethodIdByUserIdAndMethodName(Long userId, String method);
}