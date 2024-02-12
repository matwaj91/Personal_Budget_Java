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
import PersonalBudget.business.expense.domain.service.exception.ExpenseCategoryIdNotFoundException;
import PersonalBudget.business.expense.domain.service.exception.PaymentMethodIdNotFoundException;
import PersonalBudget.business.expense.dto.ExpenseDTO;
import PersonalBudget.business.user.domain.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public List<String> getUserExpenseCategories() {
        Long loggedInUserId = userFacade.fetchLoggedInUserId();
        return expenseCategoryRepository.findAllExpenseCategoryNames(loggedInUserId);
    }

    public List<String> getUserPaymentMethods() {
        Long loggedInUserId = userFacade.fetchLoggedInUserId();
        return paymentMethodRepository.findAllPaymentMethodName(loggedInUserId);
    }

    public void addExpense(ExpenseDTO expenseDTO) {
        Long userId = userFacade.fetchLoggedInUserId();
        ExpenseCategoryEntity userExpenseCategory = expenseCategoryRepository.findExpenseCategoryIdByUserIdAndCategoryName(userId, expenseDTO.category()).orElseThrow(() ->
                new ExpenseCategoryIdNotFoundException("Expense Category id not found"));
        Long userPaymentMethod = paymentMethodRepository.findPaymentMethodIdByUserIdAndMethodName(userId, expenseDTO.paymentMethod()).orElseThrow(() ->
                new PaymentMethodIdNotFoundException("Payment method id not found"));
        ExpenseEntity expenseEntity = expenseMapper.mapExpenseDTOToExpenseEntity(expenseDTO, userId, userExpenseCategory, userPaymentMethod);
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
}
