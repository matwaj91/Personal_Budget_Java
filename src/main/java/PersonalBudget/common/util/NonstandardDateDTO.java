package PersonalBudget.common.util;

import java.time.LocalDate;

public record NonstandardDateDTO(

    LocalDate dateFrom,
    LocalDate dateTo
) {}
