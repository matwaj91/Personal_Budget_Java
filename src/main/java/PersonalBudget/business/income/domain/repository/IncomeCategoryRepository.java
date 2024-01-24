package PersonalBudget.business.income.domain.repository;

import PersonalBudget.business.income.domain.model.IncomeCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IncomeCategoryRepository extends JpaRepository<IncomeCategoryEntity, Long> {


    @Query(value = "select e.name from IncomeCategoryEntity e where e.userId = :userId")
    List<String> findAllIncomeCategoryName(@Param("userId") Long userId);

    @Query(value = "select e.id from IncomeCategoryEntity e where e.userId = :userId AND e.name = :category")
    Optional<Long> findCategoryIdByUserIdAndCategoryName(@Param("userId") Long userId, @Param("category") String category);
}


