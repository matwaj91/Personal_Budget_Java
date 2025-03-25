package PersonalBudget.business.income.domain.service;

import PersonalBudget.business.income.domain.mapper.IncomeCategoryMapper;
import PersonalBudget.business.income.domain.mapper.IncomeMapper;
import PersonalBudget.business.income.domain.model.IncomeCategoryEntity;
import PersonalBudget.business.income.domain.model.IncomeDefaultCategory;
import PersonalBudget.business.income.domain.model.IncomeEntity;
import PersonalBudget.business.income.domain.repository.IncomeCategoryRepository;
import PersonalBudget.business.income.domain.repository.IncomeRepository;
import PersonalBudget.business.income.dto.IncomeCategoryDTO;
import PersonalBudget.business.income.dto.IncomeDTO;
import PersonalBudget.business.income.dto.IncomeNewCategoryDTO;
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
class IncomeServiceTest {

    private final Long USER_ID = 1L;

    @Mock
    private IncomeCategoryRepository incomeCategoryRepository;

    @Mock
    private IncomeRepository incomeRepository;

    @Mock
    private IncomeMapper incomeMapper;

    @Mock
    private IncomeCategoryMapper incomeCategoryMapper;

    @Mock
    private UserFacade userFacade;

    @Mock
    UserService userService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private IncomeService incomeService;

    @Test
    void getUserIncomeCategoriesTest() {
        Long loggedInUserId = getCurrentLoggedInUserId();
        incomeService.getUserIncomeCategories();

        verify(incomeCategoryRepository, times(1)).findAllIncomesCategoriesByUserId(loggedInUserId);
    }

    @Test
    void addIncomeTest() {
        IncomeDTO incomeDTO = mock(IncomeDTO.class);
        Long loggedInUserId = getCurrentLoggedInUserId();

        incomeService.addIncome(incomeDTO);

        when(incomeMapper.mapIncomeDTOToIncomeEntity(incomeDTO, loggedInUserId)).thenReturn(new IncomeEntity());
    }

    @Test
    void buildDefaultCategoriesTest() {
        List<IncomeCategoryEntity> result = incomeService.buildDefaultCategories(USER_ID);

        assertNotNull(result);
        assertEquals(IncomeDefaultCategory.values().length, result.size());
    }

    @Test
    void addDefaultIncomeCategoriesToUserAccountTest() {
        List<IncomeCategoryEntity> defaultIncomeCategories = incomeService.buildDefaultCategories(USER_ID);

        incomeService.addDefaultIncomeCategoriesToUserAccount(USER_ID);

        incomeCategoryRepository.saveAll(defaultIncomeCategories);
    }

    @Test
    void getUserParticularsIncomeCategoryTest() {
        Long loggedInUserId = getCurrentLoggedInUserId();
        LocalDate dateFrom = getFirstDayCurrentMonth();
        LocalDate dateTo = getLastDayCurrentMonth();

        incomeService.getUserParticularsIncomeCategory(dateFrom, dateTo);

        verify(incomeRepository, times(1)).findAllUserParticularIncomesEachCategory(loggedInUserId, dateFrom, dateTo);
    }

    @Test
    void addIncomeCategory() {
        IncomeNewCategoryDTO incomeNewCategoryDTO = new IncomeNewCategoryDTO("testCategory");
        Long loggedInUserId = getCurrentLoggedInUserId();

        incomeService.addIncomeCategory(incomeNewCategoryDTO);
        IncomeCategoryEntity incomeCategoryEntity = incomeCategoryMapper.mapIncomeCategoryDTOToIncomeCategoryEntity(incomeNewCategoryDTO, loggedInUserId);

        verify(incomeCategoryRepository, times(1)).save(incomeCategoryEntity);
    }

    @Test
    void deleteIncomeCategoryTest() {
        IncomeCategoryDTO incomeCategoryDTO = mock(IncomeCategoryDTO.class);
        Long loggedInUserId = getCurrentLoggedInUserId();
        Long id = incomeCategoryDTO.id();

        incomeService.deleteIncomeCategory(incomeCategoryDTO);

        verify(incomeCategoryRepository, times(1)).deleteParticularIncomeCategory(loggedInUserId, id);
    }

    @Test
    void checkIfIncomeCategoriesStoredTest() {
        IncomeCategoryDTO incomeCategoryDTO = mock(IncomeCategoryDTO.class);
        when(incomeService.checkIfIncomeCategoriesStored(incomeCategoryDTO)).thenReturn(true);
    }

    @Test
    void checkIfCategoryNameAlreadyExistsTest() {
        IncomeNewCategoryDTO expenseNewCategoryDTO = mock(IncomeNewCategoryDTO.class);

        Boolean result = incomeService.checkIfCategoryNameAlreadyExists(expenseNewCategoryDTO);

        assertNotNull(result);
    }

    @Test
    void deleteUserIncomesTest() {
        incomeService.deleteUserIncomes(USER_ID);

        verify(incomeRepository, times(1)).deleteAllIncomesByUserId(USER_ID);
    }

    @Test
    void deleteUserIncomeCategoriesTest() {
        incomeService.deleteUserIncomeCategories(USER_ID);

        verify(incomeCategoryRepository, times(1)).deleteIncomeCategoriesById(USER_ID);
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