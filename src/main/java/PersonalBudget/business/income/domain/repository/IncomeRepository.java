package PersonalBudget.business.income.domain.repository;

import PersonalBudget.business.income.domain.model.IncomeEntity;
import PersonalBudget.common.util.ParticularActivityDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface IncomeRepository extends JpaRepository<IncomeEntity, Long> {

    @Query(value = "select new PersonalBudget.common.util.ParticularActivityDTO(f.amount, f.incomeDate, e.name) from IncomeEntity f " +
                   "inner join IncomeCategoryEntity e on f.incomeCategoryId  = e.id where f.userId = :userId and f.incomeDate between :dateFrom and :dateTo")
    List<ParticularActivityDTO> findAllUserParticularIncomesEachCategory(@Param("userId") Long userId, @Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo);

    @Query(value = "SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM income e WHERE e.income_category_id = :id", nativeQuery = true)
    boolean findRelatedIncomeByCategoryId(@Param("id") Long id);
}
