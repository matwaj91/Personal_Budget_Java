package PersonalBudget.business.income.domain.service;

import PersonalBudget.business.income.domain.mapper.IncomeCategoryMapper;
import PersonalBudget.business.income.domain.mapper.IncomeMapper;
import PersonalBudget.business.income.domain.model.IncomeCategoryEntity;
import PersonalBudget.business.income.domain.model.IncomeDefaultCategory;
import PersonalBudget.business.income.domain.model.IncomeEntity;
import PersonalBudget.business.income.domain.repository.IncomeCategoryRepository;
import PersonalBudget.business.income.domain.repository.IncomeRepository;
import PersonalBudget.business.income.dto.IncomeCategoryDTO;
import PersonalBudget.business.income.dto.IncomeDTO;
import PersonalBudget.business.income.dto.IncomeNewCategoryDTO;
import PersonalBudget.business.user.domain.UserFacade;
import PersonalBudget.common.util.ParticularActivityDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;


@RequiredArgsConstructor
@Service
public class IncomeService {

    private final IncomeCategoryRepository incomeCategoryRepository;
    private final IncomeRepository incomeRepository;
    private final IncomeMapper incomeMapper;
    private final IncomeCategoryMapper incomeCategoryMapper;
    private final UserFacade userFacade;

    public List<IncomeCategoryDTO> getUserIncomeCategories() {
        Long loggedInUserId = userFacade.fetchLoggedInUserId();
        return incomeCategoryRepository.findAllIncomesCategoriesByUserId(loggedInUserId);
    }

    public void addIncome(IncomeDTO incomeDTO) {
        Long userId = userFacade.fetchLoggedInUserId();
        IncomeEntity incomeEntity = incomeMapper.mapIncomeDTOToIncomeEntity(incomeDTO, userId);
        incomeRepository.save(incomeEntity);
    }

    public List<IncomeCategoryEntity> buildDefaultCategories(Long userId) {
        return Stream.of(IncomeDefaultCategory.values()).map(defaultCategory ->
                    IncomeCategoryEntity.builder()
                        .userId(userId)
                        .name(defaultCategory.toString().toLowerCase())
                        .build()
                )
                .toList();
    }

    public void addDefaultIncomeCategoriesToUserAccount(Long userId) {
        List<IncomeCategoryEntity> defaultCategories = buildDefaultCategories(userId);
        incomeCategoryRepository.saveAll(defaultCategories);
    }

    public List<ParticularActivityDTO> getUserParticularsIncomeCategory(LocalDate dateFrom, LocalDate dateTo) {
        Long loggedInUserId = userFacade.fetchLoggedInUserId();
        return incomeRepository.findAllUserParticularIncomesEachCategory(loggedInUserId, dateFrom, dateTo);
    }

    public void addIncomeCategory(IncomeNewCategoryDTO incomeNewCategoryDTO) {
        Long userId = userFacade.fetchLoggedInUserId();
        IncomeCategoryEntity incomeCategoryEntity = incomeCategoryMapper.mapIncomeCategoryDTOToIncomeCategoryEntity(incomeNewCategoryDTO, userId);
        incomeCategoryRepository.save(incomeCategoryEntity);
    }

    public void deleteIncomeCategory(IncomeCategoryDTO incomeCategoryDTO) {
        Long userId = userFacade.fetchLoggedInUserId();
        Long id = incomeCategoryDTO.id();
        incomeCategoryRepository.deleteParticularIncomeCategory(userId, id);
    }

    boolean checkIfIncomeCategoriesStored(IncomeCategoryDTO incomeCategoryDTO) {
        Long id = incomeCategoryDTO.id();
        return incomeRepository.findRelatedIncomeByCategoryId(id);
    }

    boolean checkIfCategoryNameAlreadyExists(IncomeNewCategoryDTO incomeNewCategoryDTO) {
        List<IncomeCategoryDTO> incomeCategories = getUserIncomeCategories();
        return incomeCategories.stream()
                .anyMatch(category -> (incomeNewCategoryDTO.name()).equalsIgnoreCase(category.incomeCategory()));
    }

    public void deleteUserIncomes(Long userId) {
        incomeRepository.deleteAllIncomesByUserId(userId);
    }
}
