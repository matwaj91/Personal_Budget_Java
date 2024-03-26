package PersonalBudget.business.income.domain.repository;

import PersonalBudget.business.income.domain.model.IncomeEntity;
import PersonalBudget.business.income.dto.IncomeCategorySumDTO;
import PersonalBudget.business.income.dto.IncomeParticularDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface IncomeRepository extends JpaRepository<IncomeEntity, Long> {

    @Query(value = "select new PersonalBudget.business.income.dto.IncomeCategorySumDTO(e.name, SUM(f.amount)) from IncomeCategoryEntity e, IncomeEntity f " +
                   "where f.userId = :userId and e.id = f.incomeCategoryId and f.incomeDate between :dateFrom and :dateTo group by e.name")
    List<IncomeCategorySumDTO> findAllIncomeCategoriesSum(@Param("userId") Long userId, @Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo);


    @Query(value = "select new PersonalBudget.business.income.dto.IncomeParticularDTO(f.amount, f.incomeDate, e.name, f.incomeComment) from IncomeEntity f " +
                   "inner join IncomeCategoryEntity e on f.incomeCategoryId  = e.id where f.userId = :userId and f.incomeDate between :dateFrom and :dateTo")
    List<IncomeParticularDTO> findAllParticularIncomesEachCategory(@Param("userId") Long userId, @Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo);
}
