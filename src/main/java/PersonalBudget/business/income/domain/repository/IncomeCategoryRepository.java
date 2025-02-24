package PersonalBudget.business.income.domain.repository;

import PersonalBudget.business.income.domain.model.IncomeCategoryEntity;
import PersonalBudget.business.income.dto.IncomeCategoryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IncomeCategoryRepository extends JpaRepository<IncomeCategoryEntity, Long> {

    @Query(value = "select new PersonalBudget.business.income.dto.IncomeCategoryDTO(e.id, e.name) from IncomeCategoryEntity e where e.userId = :userId")
    List<IncomeCategoryDTO> findAllIncomesCategoriesByUserId(@Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query(value = "delete from income_category f where f.user_id = :userId and f.id = :id", nativeQuery = true)
    void deleteParticularIncomeCategory(@Param("userId") Long userId, @Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "delete from income_category f where f.user_id = :userId", nativeQuery = true)
    void deleteIncomeCategoriesById(Long userId);
}


