package PersonalBudget.business.income.domain.service;

import PersonalBudget.business.income.domain.mapper.IncomeMapper;
import PersonalBudget.business.income.domain.model.IncomeCategoryEntity;
import PersonalBudget.business.income.domain.model.IncomeDefaultCategory;
import PersonalBudget.business.income.domain.model.IncomeEntity;
import PersonalBudget.business.income.domain.repository.IncomeCategoryRepository;
import PersonalBudget.business.income.domain.repository.IncomeRepository;
import PersonalBudget.business.income.dto.IncomeCategoryDTO;
import PersonalBudget.business.income.dto.IncomeDTO;
import PersonalBudget.business.income.dto.IncomeParticularDTO;
import PersonalBudget.business.user.domain.UserFacade;
import PersonalBudget.common.util.CategorySumDTO;
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
    private final UserFacade userFacade;

    public List<IncomeCategoryDTO> getUserIncomeCategories() {
        Long loggedInUserId = userFacade.fetchLoggedInUserId();
        return incomeCategoryRepository.findAllIncomeCategoryByUserId(loggedInUserId);
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

    public List<CategorySumDTO> getUserIncomeCategoriesSums(LocalDate dateFrom, LocalDate dateTo) {
        Long loggedInUserId = userFacade.fetchLoggedInUserId();
        return incomeRepository.findAllIncomeCategoriesSum(loggedInUserId, dateFrom, dateTo);
    }

    public List<IncomeParticularDTO> getUserParticularsIncomeCategory(LocalDate dateFrom, LocalDate dateTo) {
        Long loggedInUserId = userFacade.fetchLoggedInUserId();
        return incomeRepository.findAllParticularIncomesEachCategory(loggedInUserId, dateFrom, dateTo);
    }

}
