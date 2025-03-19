package PersonalBudget.business.expense.domain.service;

import PersonalBudget.business.expense.dto.ExpenseCategoryDTO;
import PersonalBudget.business.expense.dto.ExpensePaymentMethodDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SpringBootTest
@AutoConfigureMockMvc
class ExpenseTemplateServiceTest {

    @Mock
    private Model model;

    @Mock
    private ExpenseService expenseService;

    @InjectMocks
    private ExpenseTemplateService expenseTemplateService;

    @BeforeEach
    void setUp() {
        expenseTemplateService = new ExpenseTemplateService(expenseService);
        model = mock(Model.class);
    }

    @Test
    void addExpenseCategoriesAttributeTest() {
        List<ExpenseCategoryDTO> expenseCategories = expenseService.getUserExpenseCategories();
        expenseTemplateService.addExpenseCategoriesAttribute(model);

        verify(model).addAttribute("expenseCategories", expenseCategories);
    }

    @Test
    void addExpenseSuccessAttributeTest() {
        expenseTemplateService.addExpenseSuccessAttribute(model);

        verify(model).addAttribute("addedExpense", true);
    }

    @Test
    void addExpenseCategoriesSuccessAttributeTest() {
        expenseTemplateService.addExpenseCategoriesSuccessAttribute(model);

        verify(model).addAttribute("addedExpenseCategory", true);
    }

    @Test
    void addExpenseCategoriesFailureAttributeTest() {
        expenseTemplateService.addExpenseCategoriesFailureAttribute(model);

        verify(model).addAttribute("additionFailureExpenseCategory", true);
    }

    @Test
    void addExpenseCategoriesDeletionSuccessAttributeTest() {
        expenseTemplateService.addExpenseCategoriesDeletionSuccessAttribute(model);

        verify(model).addAttribute("deletionExpenseCategory", true);
    }

    @Test
    void addExpenseCategoriesDeletionFailureAttributeTest() {
        expenseTemplateService.addExpenseCategoriesDeletionFailureAttribute(model);

        verify(model).addAttribute("deletionFailureExpenseCategory", true);
    }

    @Test
    void addSettingLimitSuccessAttributeTest() {
        expenseTemplateService.addSettingLimitSuccessAttribute(model);

        verify(model).addAttribute("settingLimitCategory", true);
    }

    @Test
    void addPaymentMethodsAttributeTest() {
        List<ExpensePaymentMethodDTO> expensePaymentMethods = expenseService.getUserPaymentMethods();
        expenseTemplateService.addPaymentMethodsAttribute(model);

        verify(model).addAttribute("expensePaymentMethods", expensePaymentMethods);

    }

    @Test
    void addExpensePaymentMethodSuccessAttributeTest() {
        expenseTemplateService.addExpensePaymentMethodSuccessAttribute(model);

        verify(model).addAttribute("addedExpensePaymentMethod", true);
    }

    @Test
    void addExpensePaymentMethodFailureAttributeTest() {
        expenseTemplateService.addExpensePaymentMethodFailureAttribute(model);

        verify(model).addAttribute("additionFailureExpensePaymentMethod", true);
    }

    @Test
    void addExpensePaymentMethodDeletionSuccessAttributeTest() {
        expenseTemplateService.addExpensePaymentMethodDeletionSuccessAttribute(model);

        verify(model).addAttribute("deletionExpensePaymentMethod", true);
    }

    @Test
    void addExpensePaymentMethodDeletionFailureAttributeTest() {
        expenseTemplateService.addExpensePaymentMethodDeletionFailureAttribute(model);

        verify(model).addAttribute("deletionFailureExpensePaymentMethod", true);
    }
}