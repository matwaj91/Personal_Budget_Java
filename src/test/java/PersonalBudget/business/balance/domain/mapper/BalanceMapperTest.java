package PersonalBudget.business.balance.domain.mapper;

import PersonalBudget.common.util.ParticularActivityDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class BalanceMapperTest {

    @Test
    void mapParticularActivityDTOToNameAndTotalAmountListTest() {
        List<ParticularActivityDTO> particularActivities = new ArrayList<>();
        particularActivities.add(new ParticularActivityDTO(new BigDecimal(200), LocalDate.of(2015, Month.JANUARY, 20), "food"));
        particularActivities.add(new ParticularActivityDTO(new BigDecimal(300), LocalDate.of(2015, Month.JANUARY, 25), "transport"));

        List<List<Object>> testList = new BalanceMapper().mapParticularActivityDTOToNameAndTotalAmountList(particularActivities);

        List<Object> firstInnerList = testList.get(0);
        List<Object> secondInnerList = testList.get(1);

        assertEquals("transport", firstInnerList.get(0));
        assertEquals("food", secondInnerList.get(0));
    }
}