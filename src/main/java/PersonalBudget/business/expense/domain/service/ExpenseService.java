package PersonalBudget.business.expense.domain.service;

import PersonalBudget.business.expense.domain.mapper.ExpenseMapper;
import PersonalBudget.business.expense.domain.model.DefaultPaymentMethod;
import PersonalBudget.business.expense.domain.model.ExpenseCategoryEntity;
import PersonalBudget.business.expense.domain.model.ExpenseDefaultCategory;
import PersonalBudget.business.expense.domain.model.ExpenseEntity;
import PersonalBudget.business.expense.domain.model.PaymentMethodEntity;
import PersonalBudget.business.expense.domain.repository.ExpenseCategoryRepository;
import PersonalBudget.business.expense.domain.repository.ExpenseRepository;
import PersonalBudget.business.expense.domain.repository.PaymentMethodRepository;
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
    private final PaymentMethodRepository paymentMethodRepository;
    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;
    private final UserFacade userFacade;

    public List<String> getExpenseCategoriesAssignedToUser() {
        Long loggedInUserId = userFacade.fetchLoggedInUserId();
        return expenseCategoryRepository.findAllExpenseCategoryName(loggedInUserId);
    }

    public List<String> getPaymentMethodsAssignedToUser() {
        Long loggedInUserId = userFacade.fetchLoggedInUserId();
        return paymentMethodRepository.findAllPaymentMethodName(loggedInUserId);
    }

    public void addExpense(ExpenseDTO expenseDTO) {
        Long userId = userFacade.fetchLoggedInUserId();
        Long userExpenseCategory = expenseCategoryRepository.findExpenseCategoryIdByUserIdAndCategoryName(userId, expenseDTO.category()).orElseThrow(() ->
                new ExpenseCategoryIdNotFoundException("Expense Category id not found"));
        Long userPaymentMethod = paymentMethodRepository.findPaymentMethodIdByUserIdAndMethodName(userId, expenseDTO.paymentMethod()).orElseThrow(() ->
                new PaymentMethodIdNotFoundException("Payment method id not found"));
        ExpenseEntity expenseEntity = expenseMapper.mapExpenseDTOToExpenseEntity(expenseDTO, userId, userExpenseCategory, userPaymentMethod);
        expenseRepository.save(expenseEntity);
    }

    public List<ExpenseCategoryEntity> buildDefaultExpenseCategories(Long userId) {
        return Stream.of(ExpenseDefaultCategory.values()).map(defaultCategory -> ExpenseCategoryEntity.builder()
                .userId(userId)
                .name(defaultCategory.toString().toLowerCase())
                .build())
                .toList();
    }

    public List<PaymentMethodEntity> buildDefaultPaymentMethods(Long userId) {
        return Stream.of(DefaultPaymentMethod.values()).map(defaultPaymentMethod -> PaymentMethodEntity.builder()
                .userId(userId)
                .name(defaultPaymentMethod.toString().toLowerCase())
                .build())
                .toList();
    }

    public void addDefaultExpenseCategoriesToUserAccount(Long userId) {
        List<ExpenseCategoryEntity> defaultExpenseCategories = buildDefaultExpenseCategories(userId);
        expenseCategoryRepository.saveAll(defaultExpenseCategories);
    }

    public void addDefaultPaymentMethodsToUserAccount(Long userId) {
        List<PaymentMethodEntity> defaultPaymentMethods = buildDefaultPaymentMethods(userId);
        paymentMethodRepository.saveAll(defaultPaymentMethods);
    }
}
