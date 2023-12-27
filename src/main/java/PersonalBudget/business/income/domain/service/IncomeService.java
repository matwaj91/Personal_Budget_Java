package PersonalBudget.business.income.domain.service;

import PersonalBudget.business.core.BaseService;
import PersonalBudget.business.income.domain.mapper.IncomeMapper;
import PersonalBudget.business.income.domain.model.IncomeEntity;
import PersonalBudget.business.income.domain.repository.IncomeCategoryRepository;
import PersonalBudget.business.income.domain.repository.IncomeRepository;
import PersonalBudget.business.income.dto.IncomeDTO;
import PersonalBudget.business.user.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncomeService extends BaseService{

    private final IncomeCategoryRepository incomeCategoryRepository;
    private final IncomeRepository incomeRepository;
    private final IncomeMapper incomeMapper;

    public IncomeService(UserRepository userRepository, IncomeCategoryRepository incomeCategoryRepository,
                         IncomeRepository incomeRepository, IncomeMapper incomeMapper) {
        super(userRepository);
        this.incomeRepository = incomeRepository;
        this.incomeCategoryRepository = incomeCategoryRepository;
        this.incomeMapper = incomeMapper;
    }

    public List<String> getCategoriesAssignedToUser() {
        return incomeCategoryRepository.getAllIncomeCategories(getCurrentLoggedInUserId());
    }

    public void addIncome(IncomeDTO incomeDTO) {
        Long userId = getCurrentLoggedInUserId();
        Long userIncomeCategory = incomeCategoryRepository.getIdOfParticularCategory(userId, incomeDTO.category());
        IncomeEntity incomeEntity = incomeMapper.mapIncomeDTOToIncomeEntity(incomeDTO, userId, userIncomeCategory);
        incomeRepository.save(incomeEntity);
    }
}
