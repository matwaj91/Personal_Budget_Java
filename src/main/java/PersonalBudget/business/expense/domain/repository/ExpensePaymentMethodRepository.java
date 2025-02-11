package PersonalBudget.business.expense.domain.repository;

import PersonalBudget.business.expense.domain.model.ExpensePaymentMethodEntity;
import PersonalBudget.business.expense.dto.ExpensePaymentMethodDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ExpensePaymentMethodRepository extends JpaRepository<ExpensePaymentMethodEntity, Long> {

    @Query(value = "select new PersonalBudget.business.expense.dto.ExpensePaymentMethodDTO(e.id, e.name) from ExpensePaymentMethodEntity e where e.userId = :userId")
    List<ExpensePaymentMethodDTO> findAllPaymentMethodsNames(@Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query(value = "delete from payment_method f where f.user_id = :userId and f.id = :id", nativeQuery = true)
    void deleteParticularExpensePaymentMethod(@Param("userId") Long userId, @Param("id") Long id);
}
