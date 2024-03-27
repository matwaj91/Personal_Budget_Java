package PersonalBudget.common.util;

import java.time.LocalDate;
import java.time.YearMonth;

public class Date {

    public static LocalDate getFirstDayCurrentMonth() {
        LocalDate localDate = LocalDate.now();
        return localDate.withDayOfMonth(1);
    }

    public static LocalDate getLastDayCurrentMonth() {
        return YearMonth.now().atEndOfMonth();
    }

    public static LocalDate getFirstDayPreviousMonth() {
        LocalDate localDate = LocalDate.now();
        return localDate.minusMonths(1).withDayOfMonth(1);
    }

    public static LocalDate getLastDayPreviousMonth() {
        return YearMonth.now().minusMonths(1).atEndOfMonth();
    }
}
