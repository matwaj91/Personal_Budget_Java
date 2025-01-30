package PersonalBudget.business.expense.domain.service;

import PersonalBudget.business.expense.domain.mapper.ExpenseCategoryMapper;
import PersonalBudget.business.expense.domain.mapper.ExpenseMapper;
import PersonalBudget.business.expense.domain.model.*;
import PersonalBudget.business.expense.domain.repository.ExpenseCategoryRepository;
import PersonalBudget.business.expense.domain.repository.ExpensePaymentMethodRepository;
import PersonalBudget.business.expense.domain.repository.ExpenseRepository;
import PersonalBudget.business.expense.dto.ExpenseCategoryDTO;
import PersonalBudget.business.expense.dto.ExpenseDTO;
import PersonalBudget.business.expense.dto.ExpenseNewCategoryDTO;
import PersonalBudget.business.expense.dto.ExpensePaymentMethodDTO;
import PersonalBudget.business.user.domain.UserFacade;
import PersonalBudget.common.util.ParticularActivityDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class ExpenseService {

    private final ExpenseCategoryRepository expenseCategoryRepository;
    private final ExpensePaymentMethodRepository paymentMethodRepository;
    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;
    private final ExpenseCategoryMapper expenseCategoryMapper;
    private final UserFacade userFacade;

    public List<ExpenseCategoryDTO> getUserExpenseCategories() {
        Long loggedInUserId = userFacade.fetchLoggedInUserId();
        return expenseCategoryRepository.findAllExpensesCategoriesByUserId(loggedInUserId);
    }

    public List<ExpensePaymentMethodDTO> getUserPaymentMethods() {
        Long loggedInUserId = userFacade.fetchLoggedInUserId();
        return paymentMethodRepository.findAllPaymentMethodsNames(loggedInUserId);
    }

    public void addExpense(ExpenseDTO expenseDTO) {
        Long userId = userFacade.fetchLoggedInUserId();
        ExpenseEntity expenseEntity = expenseMapper.mapExpenseDTOToExpenseEntity(expenseDTO, userId);
        expenseRepository.save(expenseEntity);
    }

    public List<ExpenseCategoryEntity> buildDefaultExpenseCategories(Long userId) {
        return Stream.of(ExpenseDefaultCategory.values()).map(defaultCategory ->
                        ExpenseCategoryEntity.builder()
                                .userId(userId)
                                .name(defaultCategory.toString().toLowerCase())
                                .build()
                )
                .toList();
    }

    public List<ExpensePaymentMethodEntity> buildDefaultPaymentMethods(Long userId) {
        return Stream.of(ExpenseDefaultPaymentMethod.values()).map(defaultPaymentMethod ->
                        ExpensePaymentMethodEntity.builder()
                                .userId(userId)
                                .name(defaultPaymentMethod.toString().toLowerCase())
                                .build()
                )
                .toList();
    }

    public void addDefaultExpenseCategoriesToUserAccount(Long userId) {
        List<ExpenseCategoryEntity> defaultExpenseCategories = buildDefaultExpenseCategories(userId);
        expenseCategoryRepository.saveAll(defaultExpenseCategories);
    }

    public void addDefaultPaymentMethodsToUserAccount(Long userId) {
        List<ExpensePaymentMethodEntity> defaultPaymentMethods = buildDefaultPaymentMethods(userId);
        paymentMethodRepository.saveAll(defaultPaymentMethods);
    }

    public List<ParticularActivityDTO> getUserParticularsExpenseCategory(LocalDate dateFrom, LocalDate dateTo) {
        Long loggedInUserId = userFacade.fetchLoggedInUserId();
        return expenseRepository.findAllUserParticularExpensesEachCategory(loggedInUserId, dateFrom, dateTo);
    }

    boolean checkIfCategoryNameAlreadyExists(ExpenseNewCategoryDTO expenseNewCategoryDTO) {
        List<ExpenseCategoryDTO> expenseCategories = getUserExpenseCategories();
        return expenseCategories.stream()
                .anyMatch(category -> (expenseNewCategoryDTO.name()).equalsIgnoreCase(category.expenseCategory()));
    }

    public void addExpenseCategory(@Valid ExpenseNewCategoryDTO expenseNewCategoryDTO) {
        Long userId = userFacade.fetchLoggedInUserId();
        ExpenseCategoryEntity expenseCategoryEntity = expenseCategoryMapper.mapExpenseCategoryDTOToIncomeCategoryEntity(expenseNewCategoryDTO, userId);
        expenseCategoryRepository.save(expenseCategoryEntity);
    }

    public boolean checkIfExpenseCategoriesStored(@Valid ExpenseCategoryDTO expenseCategoryDTO) {
        Long id = expenseCategoryDTO.id();
        return expenseRepository.findRelatedExpenseByCategoryId(id);
    }

    public void deleteExpenseCategory(@Valid ExpenseCategoryDTO expenseCategoryDTO) {
        Long userId = userFacade.fetchLoggedInUserId();
        Long id = expenseCategoryDTO.id();
        expenseCategoryRepository.deleteParticularExpenseCategory(userId, id);
    }
}
