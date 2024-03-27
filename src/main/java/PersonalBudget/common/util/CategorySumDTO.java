package PersonalBudget.common.util;

import java.math.BigDecimal;

public record CategorySumDTO(

    String name,
    BigDecimal amount
) {}
