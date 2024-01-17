package PersonalBudget.business.income.domain.service;

import PersonalBudget.business.income.domain.mapper.IncomeMapper;
import PersonalBudget.business.income.domain.model.DefaultCategory;
import PersonalBudget.business.income.domain.model.IncomeCategoryEntity;
import PersonalBudget.business.income.domain.model.IncomeEntity;
import PersonalBudget.business.income.domain.repository.IncomeCategoryRepository;
import PersonalBudget.business.income.domain.repository.IncomeRepository;
import PersonalBudget.business.income.domain.service.exception.CategoryIdNotFoundException;
import PersonalBudget.business.income.dto.IncomeDTO;
import PersonalBudget.business.user.domain.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@RequiredArgsConstructor
@Service
public class IncomeService {

    private final IncomeCategoryRepository incomeCategoryRepository;
    private final IncomeRepository incomeRepository;
    private final IncomeMapper incomeMapper;
    private final UserFacade userFacade;

    public List<String> getCategoriesAssignedToUser() {
        Long loggedInUserId = userFacade.fetchLoggedInUserId();
        return incomeCategoryRepository.findAllIncomeCategoryName(loggedInUserId);
    }

    public void addIncome(IncomeDTO incomeDTO) {
        Long userId = userFacade.fetchLoggedInUserId();
        Long userIncomeCategory = incomeCategoryRepository.findCategoryIdByUserIdAndCategoryName(userId, incomeDTO.category()).orElseThrow(() ->
                new CategoryIdNotFoundException("Category id not found"));
        IncomeEntity incomeEntity = incomeMapper.mapIncomeDTOToIncomeEntity(incomeDTO, userId, userIncomeCategory);
        incomeRepository.save(incomeEntity);
    }

    public List<IncomeCategoryEntity> buildDefaultCategories(Long userId) {
        return Arrays.stream(DefaultCategory.values()).map(defaultCategory -> IncomeCategoryEntity.builder()
                .userId(userId)
                .name(defaultCategory.toString().toLowerCase())
                .build())
                .toList();

        /*return DefaultCategory.stream().map(defaultCategory -> IncomeCategoryEntity.builder()
                .userId(userId)
                .name(defaultCategory.toString().toLowerCase())
                .build())
                .toList();
         */
    }

    public void addDefaultCategoriesToUserAccount(Long userId) {
        List<IncomeCategoryEntity> defaultCategories = buildDefaultCategories(userId);
        incomeCategoryRepository.saveAll(defaultCategories);
    }
}
