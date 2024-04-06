package PersonalBudget.business.expense.domain.service;

import PersonalBudget.business.expense.domain.mapper.ExpenseMapper;
import PersonalBudget.business.expense.domain.model.ExpenseCategoryEntity;
import PersonalBudget.business.expense.domain.model.ExpenseDefaultCategory;
import PersonalBudget.business.expense.domain.model.ExpenseDefaultPaymentMethod;
import PersonalBudget.business.expense.domain.model.ExpenseEntity;
import PersonalBudget.business.expense.domain.model.ExpensePaymentMethodEntity;
import PersonalBudget.business.expense.domain.repository.ExpenseCategoryRepository;
import PersonalBudget.business.expense.domain.repository.ExpensePaymentMethodRepository;
import PersonalBudget.business.expense.domain.repository.ExpenseRepository;
import PersonalBudget.business.expense.dto.ExpenseCategoryDTO;
import PersonalBudget.business.expense.dto.ExpenseDTO;
import PersonalBudget.business.expense.dto.ExpensePaymentMethodDTO;
import PersonalBudget.business.user.domain.UserFacade;
import PersonalBudget.common.util.CategorySumDTO;
import PersonalBudget.common.util.ParticularActivityDTO;
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

    public List<CategorySumDTO> getUserExpenseCategoriesSums(LocalDate dateFrom, LocalDate dateTo) {
        Long loggedInUserId = userFacade.fetchLoggedInUserId();
        return expenseRepository.findAllExpensesCategoriesSums(loggedInUserId, dateFrom, dateTo);
    }

    public List<ParticularActivityDTO> getUserParticularsExpenseCategory(LocalDate dateFrom, LocalDate dateTo) {
        Long loggedInUserId = userFacade.fetchLoggedInUserId();
        return expenseRepository.findAllUserParticularExpensesEachCategory(loggedInUserId, dateFrom, dateTo);
    }
}
