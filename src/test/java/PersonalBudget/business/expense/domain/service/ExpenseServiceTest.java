package PersonalBudget.business.expense.domain.service;

import PersonalBudget.business.expense.domain.mapper.ExpenseCategoryMapper;
import PersonalBudget.business.expense.domain.mapper.ExpenseMapper;
import PersonalBudget.business.expense.domain.model.*;
import PersonalBudget.business.expense.domain.repository.ExpenseCategoryRepository;
import PersonalBudget.business.expense.domain.repository.ExpensePaymentMethodRepository;
import PersonalBudget.business.expense.domain.repository.ExpenseRepository;
import PersonalBudget.business.expense.dto.*;
import PersonalBudget.business.user.domain.UserFacade;
import PersonalBudget.business.user.domain.repository.UserRepository;
import PersonalBudget.business.user.domain.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static PersonalBudget.common.util.DateUtils.getFirstDayCurrentMonth;
import static PersonalBudget.common.util.DateUtils.getLastDayCurrentMonth;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class ExpenseServiceTest {


    private final Long USER_ID = 1L;

    @Mock
    private ExpenseCategoryRepository expenseCategoryRepository;

    @Mock
    private ExpensePaymentMethodRepository paymentMethodRepository;

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private ExpenseMapper expenseMapper;

    @Mock
    private ExpenseCategoryMapper expenseCategoryMapper;

    @Mock
    private UserFacade userFacade;

    @Mock
    UserService userService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ExpenseService expenseService;

    @Test
    void getUserExpenseCategoriesTest() {
        Long loggedInUserId = getCurrentLoggedInUserId();
        expenseService.getUserExpenseCategories();

        verify(expenseCategoryRepository, times(1)).findAllExpensesCategoriesByUserId(loggedInUserId);
    }

    @Test
    void getCurrentMonthExpenseSumTest() {
        Long selectedExpenseCategory = 2L;
        LocalDate dateFrom = getFirstDayCurrentMonth();
        LocalDate dateTo = getLastDayCurrentMonth();

        expenseService.getCurrentMonthExpenseSum(selectedExpenseCategory);

        verify(expenseRepository, times(1)).findCurrentMonthExpenseSum(selectedExpenseCategory, dateFrom, dateTo);
    }

    @Test
    void getUserPaymentMethodsTest() {
        Long loggedInUserId = getCurrentLoggedInUserId();

        expenseService.getUserPaymentMethods();

        verify(paymentMethodRepository, times(1)).findAllPaymentMethodsNames(loggedInUserId);
    }

    @Test
    void addExpenseTest() {
        ExpenseDTO expenseDTO = mock(ExpenseDTO.class);
        Long loggedInUserId = getCurrentLoggedInUserId();
        ExpenseEntity expenseEntity = expenseMapper.mapExpenseDTOToExpenseEntity(expenseDTO, loggedInUserId);

        expenseService.addExpense(expenseDTO);

        verify(expenseRepository, times(1)).save(expenseEntity);
    }

    @Test
    void buildDefaultExpenseCategoriesTest() {
        List<ExpenseCategoryEntity> result = expenseService.buildDefaultExpenseCategories(USER_ID);

        assertNotNull(result);
        assertEquals(ExpenseDefaultCategory.values().length, result.size());
    }

    @Test
    void buildDefaultPaymentMethodsTest() {
        List<ExpensePaymentMethodEntity> result = expenseService.buildDefaultPaymentMethods(USER_ID);

        assertNotNull(result);
        assertEquals(ExpenseDefaultPaymentMethod.values().length, result.size());
    }

    @Test
    void addDefaultExpenseCategoriesToUserAccountTest() {
        List<ExpenseCategoryEntity> defaultExpenseCategories = expenseService.buildDefaultExpenseCategories(USER_ID);

        expenseService.addDefaultExpenseCategoriesToUserAccount(USER_ID);

        expenseCategoryRepository.saveAll(defaultExpenseCategories);
    }

    @Test
    void addDefaultPaymentMethodsToUserAccountTest() {
        List<ExpensePaymentMethodEntity> defaultPaymentMethods = expenseService.buildDefaultPaymentMethods(USER_ID);

        expenseService.addDefaultPaymentMethodsToUserAccount(USER_ID);

        paymentMethodRepository.saveAll(defaultPaymentMethods);
    }

    @Test
    void getUserParticularsExpenseCategoryTest() {
        Long loggedInUserId = getCurrentLoggedInUserId();
        LocalDate dateFrom = getFirstDayCurrentMonth();
        LocalDate dateTo = getLastDayCurrentMonth();

        expenseService.getUserParticularsExpenseCategory(dateFrom, dateTo);

        verify(expenseRepository, times(1)).findAllUserParticularExpensesEachCategory(loggedInUserId, dateFrom, dateTo);
    }

    @Test
    void checkIfCategoryNameAlreadyExistsTest() {
        ExpenseNewCategoryDTO expenseNewCategoryDTO = mock(ExpenseNewCategoryDTO.class);

        Boolean result = expenseService.checkIfCategoryNameAlreadyExists(expenseNewCategoryDTO);

        assertNotNull(result);
    }

    @Test
    void addExpenseCategoryTest() {
        ExpenseNewCategoryDTO expenseNewCategoryDTO = new ExpenseNewCategoryDTO("testCategory");
        Long loggedInUserId = getCurrentLoggedInUserId();

        expenseService.addExpenseCategory(expenseNewCategoryDTO);
        ExpenseCategoryEntity expenseCategoryEntity = expenseCategoryMapper.mapExpenseCategoryDTOToExpenseCategoryEntity(expenseNewCategoryDTO, loggedInUserId);

        verify(expenseCategoryRepository, times(1)).save(expenseCategoryEntity);
    }

    @Test
    void checkIfExpenseCategoriesStoredTest() {
        ExpenseCategoryDTO expenseCategoryDTO = mock(ExpenseCategoryDTO.class);
        when(expenseService.checkIfExpenseCategoriesStored(expenseCategoryDTO)).thenReturn(true);
    }

    @Test
    void deleteExpenseCategoryTest() {
        ExpenseCategoryDTO expenseCategoryDTO = mock(ExpenseCategoryDTO.class);
        Long loggedInUserId = getCurrentLoggedInUserId();
        Long id = expenseCategoryDTO.id();

        expenseService.deleteExpenseCategory(expenseCategoryDTO);

        verify(expenseCategoryRepository, times(1)).deleteParticularExpenseCategory(loggedInUserId, id);
    }

    @Test
    void setSpendingLimitTest() {
        ExpenseCategoryDTO expenseCategoryDTO = mock(ExpenseCategoryDTO.class);
        Long id = expenseCategoryDTO.id();
        BigDecimal amountLimit = expenseCategoryDTO.limitAmount();

        expenseService.setSpendingLimit(expenseCategoryDTO);

        verify(expenseCategoryRepository, times(1)).setExpenseCategoryLimit(id, amountLimit);
    }

    @Test
    void checkIfPaymentMethodNameAlreadyExistsTest() {
        ExpenseNewPaymentMethodDTO expenseNewPaymentMethodDTO = mock(ExpenseNewPaymentMethodDTO.class);

        Boolean result = expenseService.checkIfPaymentMethodNameAlreadyExists(expenseNewPaymentMethodDTO);

        assertNotNull(result);
    }

    @Test
    void addExpensePaymentMethodTest() {
        ExpenseNewPaymentMethodDTO expenseNewPaymentMethodDTO = new ExpenseNewPaymentMethodDTO("testPaymentMethod");
        Long loggedInUserId = getCurrentLoggedInUserId();

        expenseService.addExpensePaymentMethod(expenseNewPaymentMethodDTO);
        ExpensePaymentMethodEntity expensePaymentMethodEntity = expenseCategoryMapper.mapExpensePaymentMethodDTOToExpensePaymentMethodEntity(expenseNewPaymentMethodDTO, loggedInUserId);

        verify(paymentMethodRepository, times(1)).save(expensePaymentMethodEntity);
    }

    @Test
    void deleteExpensePaymentMethodTest() {
        ExpensePaymentMethodDTO expensePaymentMethodDTO = mock(ExpensePaymentMethodDTO.class);
        Long loggedInUserId = getCurrentLoggedInUserId();
        Long id = expensePaymentMethodDTO.id();

        expenseService.deleteExpensePaymentMethod(expensePaymentMethodDTO);

        verify(paymentMethodRepository, times(1)).deleteParticularExpensePaymentMethod(loggedInUserId, id);
    }

    @Test
    void checkIfExpensePaymentMethodsStoredTest() {
        ExpensePaymentMethodDTO expensePaymentMethodDTO = mock(ExpensePaymentMethodDTO.class);
        Long id = expensePaymentMethodDTO.id();

        expenseService.checkIfExpensePaymentMethodsStored(expensePaymentMethodDTO);

        verify(expenseRepository, times(1)).findRelatedExpenseByPaymentMethodId(id);
    }

    @Test
    void deleteUserExpensesTest() {
        expenseService.deleteUserExpenses(USER_ID);

        verify(expenseRepository, times(1)).deleteAllExpensesByUserId(USER_ID);
    }

    @Test
    void deleteUserExpenseCategoriesTest() {
        expenseService.deleteUserExpenseCategories(USER_ID);

        verify(expenseCategoryRepository, times(1)).deleteExpenseCategoriesByUserId(USER_ID);
    }

    @Test
    void deleteUserPaymentMethodsTest() {
        expenseService.deleteUserPaymentMethods(USER_ID);

        verify(paymentMethodRepository, times(1)).deletePaymentMethodsByUserId(USER_ID);
    }

    public void authenticateUser() {
        String EMAIL = "test@example.com";
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        UserDetails userDetails = Mockito.mock(UserDetails.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn(EMAIL);
        when(userRepository.findIdByEmail(EMAIL)).thenReturn(Optional.of(USER_ID));

        SecurityContextHolder.setContext(securityContext);
    }

    public Long getCurrentLoggedInUserId() {
       authenticateUser();
       return userService.getCurrentLoggedInUserId();
    }
}