package PersonalBudget.common.util;

import java.time.LocalDate;

public record TimePeriodDTO(

    LocalDate dateFrom,
    LocalDate dateTo
) {}
