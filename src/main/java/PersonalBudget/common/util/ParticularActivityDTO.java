package PersonalBudget.common.util;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ParticularActivityDTO(

    BigDecimal amount,
    LocalDate date,
    String name
) {}
