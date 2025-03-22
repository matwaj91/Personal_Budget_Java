package PersonalBudget.business.income.domain.service;

import PersonalBudget.business.income.dto.IncomeCategoryDTO;
import PersonalBudget.business.income.dto.IncomeNewCategoryDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class IncomeCategoryPageHandlerTest {

    private final IncomeNewCategoryDTO incomeNewCategoryDTO = new IncomeNewCategoryDTO("casino");
    private final IncomeCategoryDTO incomeCategoryDTO = new IncomeCategoryDTO(1L, "casino");
    private static final String INCOME_CATEGORIES_PAGE = "menu/incomeCategories";
    private static final String NEW_INCOME_CATEGORY_SUCCESS_PAGE  = "addition/success";
    private static final String REDIRECT_NEW_INCOME_CATEGORY_SUCCESS_PAGE  = "redirect:" + NEW_INCOME_CATEGORY_SUCCESS_PAGE;
    private static final String INCOME_CATEGORY_DELETION_SUCCESS_PAGE  = "deletion/success";
    private static final String REDIRECT_INCOME_CATEGORY_DELETION_SUCCESS_PAGE = "redirect:" + INCOME_CATEGORY_DELETION_SUCCESS_PAGE;
    private static final String INCOME_CATEGORY_DELETION_FAILURE_PAGE  = "deletion/failure";
    private static final String REDIRECT_INCOME_CATEGORY_DELETION_FAILURE_PAGE  = "redirect:" + INCOME_CATEGORY_DELETION_FAILURE_PAGE;
    private static final String INCOME_CATEGORY_FAILURE_PAGE  = "addition/failure";
    private static final String REDIRECT_INCOME_CATEGORY_FAILURE_PAGE  = "redirect:" + INCOME_CATEGORY_FAILURE_PAGE;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private IncomeService incomeService;

    @Mock
    private IncomeTemplateService incomeTemplateService;

    @InjectMocks
    private IncomeCategoryPageHandler incomeCategoryPageHandler;

    @Test
    void handleIncomeCategoriesPageTest() {
        String result = incomeCategoryPageHandler.handleIncomeCategoriesPage(model);

        assertEquals(INCOME_CATEGORIES_PAGE, result);
    }

    @Test
    void handleIncomeCategoriesPageHasErrors() {
        when(bindingResult.hasErrors()).thenReturn(true);

        String result = incomeCategoryPageHandler.handleIncomeCategoriesPageAfterSubmit(bindingResult, model, incomeNewCategoryDTO);

        assertEquals(INCOME_CATEGORIES_PAGE, result);
    }

    @Test
    void handleIncomeCategoriesPageNameAlreadyExists() {
        when(incomeService.checkIfCategoryNameAlreadyExists(incomeNewCategoryDTO)).thenReturn(true);

        String result = incomeCategoryPageHandler.handleIncomeCategoriesPageAfterSubmit(bindingResult, model, incomeNewCategoryDTO);

        assertTrue(result.contains(REDIRECT_INCOME_CATEGORY_FAILURE_PAGE));
    }

    @Test
    void handleIncomeCategoriesPageAfterSubmitTest() {
        String result = incomeCategoryPageHandler.handleIncomeCategoriesPageAfterSubmit(bindingResult, model, incomeNewCategoryDTO);

        assertEquals(REDIRECT_NEW_INCOME_CATEGORY_SUCCESS_PAGE, result);
    }

    @Test
    void handleIncomeCategoriesSuccessPageTest() {
        String result = incomeCategoryPageHandler.handleIncomeCategoriesSuccessPage(model);

        assertEquals(INCOME_CATEGORIES_PAGE, result);
    }

    @Test
    void handleIncomeDeletionCategoriesPageHasErrors() {
        when(bindingResult.hasErrors()).thenReturn(true);

        String result = incomeCategoryPageHandler.handleIncomeDeletionCategoriesPageAfterSubmit(bindingResult, model, incomeCategoryDTO);

        assertEquals(INCOME_CATEGORIES_PAGE, result);
    }

    @Test
    void handleIncomeDeletionCategoriesPageAlreadyExists() {
        when(incomeService.checkIfIncomeCategoriesStored(incomeCategoryDTO)).thenReturn(true);

        String result = incomeCategoryPageHandler.handleIncomeDeletionCategoriesPageAfterSubmit(bindingResult, model, incomeCategoryDTO);

        assertTrue(result.contains(REDIRECT_INCOME_CATEGORY_DELETION_FAILURE_PAGE));
    }

    @Test
    void handleIncomeDeletionCategoriesPageAfterSubmitTest() {
        String result = incomeCategoryPageHandler.handleIncomeDeletionCategoriesPageAfterSubmit(bindingResult, model, incomeCategoryDTO);

        assertEquals(REDIRECT_INCOME_CATEGORY_DELETION_SUCCESS_PAGE, result);
    }

    @Test
    void handleIncomeCategoriesDeletionSuccessPage() {
        String result = incomeCategoryPageHandler.handleIncomeCategoriesDeletionSuccessPage(model);

        assertEquals(INCOME_CATEGORIES_PAGE, result);
    }

    @Test
    void handleIncomeCategoriesDeletionFailurePage() {
        String result = incomeCategoryPageHandler.handleIncomeCategoriesDeletionFailurePage(model);

        assertEquals(INCOME_CATEGORIES_PAGE, result);
    }

    @Test
    void handleIncomeCategoriesFailurePage() {
        String result = incomeCategoryPageHandler.handleIncomeCategoriesFailurePage(model);

        assertEquals(INCOME_CATEGORIES_PAGE, result);
    }
}