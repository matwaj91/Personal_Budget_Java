package PersonalBudget.business.income.domain.controller;

import PersonalBudget.business.income.domain.service.IncomeCategoryPageHandler;
import PersonalBudget.business.income.domain.service.IncomePageHandler;
import PersonalBudget.business.income.dto.IncomeCategoryDTO;
import PersonalBudget.business.income.dto.IncomeDTO;
import PersonalBudget.business.income.dto.IncomeNewCategoryDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class IncomeControllerTest {

    private final String INCOME_CATEGORY = "testCategory";
    private final Long INCOME_CATEGORY_ID = 2L;
    private final String INCOME_COMMENT = " ";
    private final LocalDate INCOME_DATE = LocalDate.of(2015, 10, 10);
    private final BigDecimal AMOUNT = new BigDecimal(100);
    private static final String INCOME_PAGE = "menu/income";
    private static final String INCOME_SUCCESS_PAGE = "income/success";
    private static final String INCOME_CATEGORIES_PAGE = "menu/incomeCategories";
    private static final String REDIRECT_INCOME_SUCCESS_PAGE  = "redirect:" + INCOME_SUCCESS_PAGE;
    private static final String NEW_INCOME_CATEGORY_SUCCESS_PAGE  = "addition/success";
    private static final String REDIRECT_NEW_INCOME_CATEGORY_SUCCESS_PAGE  = "redirect:" +
            NEW_INCOME_CATEGORY_SUCCESS_PAGE;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private IncomePageHandler incomePageHandler;

    @Mock
    private IncomeCategoryPageHandler incomeCategoryPageHandler;

    @InjectMocks
    private IncomeController incomeController;

    @Test
    void getIncomePageTest() {
        when(incomePageHandler.handleIncomePage(model)).thenReturn(INCOME_PAGE);

        String result = incomeController.getIncomePage(model);

        assertEquals(INCOME_PAGE, result);
    }

    @Test
    void incomeDTOTest() {
        IncomeDTO result = incomeController.incomeDTO(AMOUNT, INCOME_DATE, INCOME_CATEGORY_ID, INCOME_COMMENT);

        assertEquals(AMOUNT, result.amount());
    }

    @Test
    void getProperPageAfterAddingIncomeTest() {
        IncomeDTO incomeDTO = new IncomeDTO(AMOUNT, INCOME_DATE, INCOME_CATEGORY_ID, INCOME_COMMENT);

        when(incomePageHandler.handleIncomePageAfterSubmit(bindingResult, model, incomeDTO)).
                thenReturn(REDIRECT_INCOME_SUCCESS_PAGE);

        String result = incomeController.getProperPageAfterAddingIncome(incomeDTO, bindingResult, model);

        assertEquals(REDIRECT_INCOME_SUCCESS_PAGE, result);
    }

    @Test
    void getIncomeSuccessPageTest() {
        when(incomePageHandler.handleIncomeSuccessPage(model)).thenReturn(INCOME_PAGE);

        String result = incomeController.getIncomeSuccessPage(model);

        assertEquals(INCOME_PAGE, result);
    }

    @Test
    void getIncomeCategoriesPageTest() {
        when(incomeCategoryPageHandler.handleIncomeCategoriesPage(model)).thenReturn(INCOME_PAGE);

        String result = incomeController.getIncomeCategoriesPage(model);

        assertEquals(INCOME_PAGE, result);
    }

    @Test
    void incomeNewCategoryDTOTest() {
        IncomeNewCategoryDTO result = incomeController.incomeNewCategoryDTO(INCOME_CATEGORY);

        assertEquals(INCOME_CATEGORY, result.name());
    }

    @Test
    void getProperPageAfterAddingNewIncomeCategoryTest() {
        IncomeNewCategoryDTO incomeNewCategoryDTO = new IncomeNewCategoryDTO(INCOME_CATEGORY);

        when(incomeCategoryPageHandler.handleIncomeCategoriesPageAfterSubmit(
                bindingResult, model, incomeNewCategoryDTO)).thenReturn(REDIRECT_NEW_INCOME_CATEGORY_SUCCESS_PAGE);

        String result = incomeController.getProperPageAfterAddingNewIncomeCategory(
                incomeNewCategoryDTO, bindingResult, model);

        assertEquals(REDIRECT_NEW_INCOME_CATEGORY_SUCCESS_PAGE, result);
    }

    @Test
    void getIncomeCategorySuccessPageTest() {
        when(incomeCategoryPageHandler.handleIncomeCategoriesSuccessPage(model)).thenReturn(INCOME_CATEGORIES_PAGE);

        String result = incomeController.getIncomeCategorySuccessPage(model);

        assertEquals(INCOME_CATEGORIES_PAGE, result);
    }

    @Test
    void getIncomeCategoryFailurePageTest() {
        when(incomeCategoryPageHandler.handleIncomeCategoriesFailurePage(model)).thenReturn(INCOME_CATEGORIES_PAGE);

        String result = incomeController.getIncomeCategoryFailurePage(model);

        assertEquals(INCOME_CATEGORIES_PAGE, result);
    }

    @Test
    void testIncomeNewCategoryDTOTest() {
        IncomeCategoryDTO result = incomeController.incomeNewCategoryDTO(INCOME_CATEGORY_ID, INCOME_CATEGORY);

        assertEquals(INCOME_CATEGORY, result.incomeCategory());
    }

    @Test
    void getProperPageAfterDeletingIncomeCategoryTest() {
        IncomeCategoryDTO incomeCategoryDTO = new IncomeCategoryDTO(INCOME_CATEGORY_ID, INCOME_CATEGORY);

        when(incomeCategoryPageHandler.handleIncomeDeletionCategoriesPageAfterSubmit(
                bindingResult, model, incomeCategoryDTO)).thenReturn(REDIRECT_NEW_INCOME_CATEGORY_SUCCESS_PAGE);

        String result = incomeController.getProperPageAfterDeletingIncomeCategory(incomeCategoryDTO, bindingResult, model);

        assertEquals(REDIRECT_NEW_INCOME_CATEGORY_SUCCESS_PAGE, result);
    }

    @Test
    void getIncomeCategoryDeletionSuccessPageTest() {
        when(incomeCategoryPageHandler.handleIncomeCategoriesDeletionSuccessPage(model)).thenReturn(INCOME_CATEGORIES_PAGE);

        String result = incomeController.getIncomeCategoryDeletionSuccessPage(model);

        assertEquals(INCOME_CATEGORIES_PAGE, result);
    }

    @Test
    void getIncomeCategoryDeletionFailurePageTest() {
        when(incomeCategoryPageHandler.handleIncomeCategoriesDeletionFailurePage(model)).thenReturn(INCOME_CATEGORIES_PAGE);

        String result = incomeController.getIncomeCategoryDeletionFailurePage(model);

        assertEquals(INCOME_CATEGORIES_PAGE, result);
    }
}