package PersonalBudget.business.income.domain.repository;

import PersonalBudget.business.income.domain.model.IncomeCategoryEntity;
import PersonalBudget.business.income.dto.IncomeCategoryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IncomeCategoryRepository extends JpaRepository<IncomeCategoryEntity, Long> {

    @Query(value = "select new PersonalBudget.business.income.dto.IncomeCategoryDTO(e.id, e.name) from IncomeCategoryEntity e where e.userId = :userId")
    List<IncomeCategoryDTO> findAllIncomeCategoryByUserId(@Param("userId") Long userId);
}


