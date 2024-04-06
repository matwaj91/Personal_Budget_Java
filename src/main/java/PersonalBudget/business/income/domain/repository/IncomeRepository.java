package PersonalBudget.business.income.domain.repository;

import PersonalBudget.business.income.domain.model.IncomeEntity;
import PersonalBudget.common.util.CategorySumDTO;
import PersonalBudget.common.util.ParticularActivityDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface IncomeRepository extends JpaRepository<IncomeEntity, Long> {

    @Query(value = "select new PersonalBudget.common.util.CategorySumDTO(e.name, SUM(f.amount)) from IncomeCategoryEntity e, IncomeEntity f " +
                   "where f.userId = :userId and e.id = f.incomeCategoryId and f.incomeDate between :dateFrom and :dateTo group by e.name")
    List<CategorySumDTO> findAllIncomesCategoriesSums(@Param("userId") Long userId, @Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo);


    @Query(value = "select new PersonalBudget.common.util.ParticularActivityDTO(f.amount, f.incomeDate, e.name) from IncomeEntity f " +
                   "inner join IncomeCategoryEntity e on f.incomeCategoryId  = e.id where f.userId = :userId and f.incomeDate between :dateFrom and :dateTo")
    List<ParticularActivityDTO> findAllUserParticularIncomesEachCategory(@Param("userId") Long userId, @Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo);
}
