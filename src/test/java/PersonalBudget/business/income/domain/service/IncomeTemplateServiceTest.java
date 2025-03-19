package PersonalBudget.business.income.domain.service;

import PersonalBudget.business.income.dto.IncomeCategoryDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SpringBootTest
@AutoConfigureMockMvc
class IncomeTemplateServiceTest {

    @Mock
    private Model model;

    @Mock
    private IncomeService incomeService;

    @Mock
    private IncomeTemplateService incomeTemplateService;

    @BeforeEach
    public void setUp() {
        incomeTemplateService = new IncomeTemplateService(incomeService);
        model = mock(Model.class);
    }

    @Test
    void addIncomeCategoriesAttributeTest() {
        List<IncomeCategoryDTO> incomeCategories = incomeService.getUserIncomeCategories();
        incomeTemplateService.addIncomeCategoriesAttribute(model);

        verify(model).addAttribute("incomeCategories", incomeCategories);
    }

    @Test
    void addIncomeSuccessAttributeTest() {
        incomeTemplateService.addIncomeSuccessAttribute(model);

        verify(model).addAttribute("addedIncome", true);
    }

    @Test
    void addIncomeCategoriesSuccessAttributeTest() {
        incomeTemplateService.addIncomeCategoriesSuccessAttribute(model);

        verify(model).addAttribute("addedIncomeCategory", true);
    }

    @Test
    void addIncomeCategoriesFailureAttributeTest() {
        incomeTemplateService.addIncomeCategoriesFailureAttribute(model);

        verify(model).addAttribute("additionFailureIncomeCategory", true);
    }

    @Test
    void addIncomeCategoriesDeletionSuccessAttributeTest() {
        incomeTemplateService.addIncomeCategoriesDeletionSuccessAttribute(model);

        verify(model).addAttribute("deletionIncomeCategory", true);
    }

    @Test
    void addIncomeCategoriesDeletionFailureAttributeTest() {
        incomeTemplateService.addIncomeCategoriesDeletionFailureAttribute(model);

        verify(model).addAttribute("deletionFailureIncomeCategory", true);
    }
}