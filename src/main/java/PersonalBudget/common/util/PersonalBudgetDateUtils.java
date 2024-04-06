package PersonalBudget.common.util;

import java.time.LocalDate;
import java.time.YearMonth;

import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;

public class PersonalBudgetDateUtils {

    public static LocalDate getNowLocalDate() {
        return LocalDate.now();
    }

    public static LocalDate getFirstDayCurrentMonth() {
        return getNowLocalDate().withDayOfMonth(1);
    }

    public static LocalDate getLastDayCurrentMonth() {
        return YearMonth.now().atEndOfMonth();
    }

    public static LocalDate getFirstDayPreviousMonth() {
        return getNowLocalDate().minusMonths(1).withDayOfMonth(1);
    }

    public static LocalDate getLastDayPreviousMonth() {
        return YearMonth.now().minusMonths(1).atEndOfMonth();
    }

    public static LocalDate getFirstDayCurrentYear() {
        return getNowLocalDate().with(firstDayOfYear());
    }

    public static LocalDate getLastDayCurrentYear() {
        return getNowLocalDate().with(lastDayOfYear());
    }
}
