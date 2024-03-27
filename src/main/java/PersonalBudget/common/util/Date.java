package PersonalBudget.common.util;

import java.time.LocalDate;
import java.time.YearMonth;

import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;

public class Date {

    private static final LocalDate localDate = LocalDate.now();

    public static LocalDate getFirstDayCurrentMonth() {
        return localDate.withDayOfMonth(1);
    }

    public static LocalDate getLastDayCurrentMonth() {
        return YearMonth.now().atEndOfMonth();
    }

    public static LocalDate getFirstDayPreviousMonth() {
        return localDate.minusMonths(1).withDayOfMonth(1);
    }

    public static LocalDate getLastDayPreviousMonth() {
        return YearMonth.now().minusMonths(1).atEndOfMonth();
    }

    public static LocalDate getFirstDayCurrentYear() {
        return localDate.with(firstDayOfYear());
    }

    public static LocalDate getLastDayCurrentYear() {
        return localDate.with(lastDayOfYear());
    }
}
