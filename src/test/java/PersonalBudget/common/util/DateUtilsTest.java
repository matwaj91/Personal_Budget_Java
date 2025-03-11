package PersonalBudget.common.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.YearMonth;

import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;
import static org.junit.jupiter.api.Assertions.*;

class DateUtilsTest {

    @Test
    void getNowLocalDateTimeTest() {
        LocalDate expectedDate = LocalDate.now();
        LocalDate actualDate = DateUtils.getNowLocalDate();
        assertEquals(expectedDate, actualDate);
    }
    @Test
    void getFirstDayCurrentMonthTest() {
        LocalDate expectedDate = LocalDate.now().withDayOfMonth(1);
        LocalDate actualDate = DateUtils.getFirstDayCurrentMonth();
        assertEquals(expectedDate, actualDate);
    }

    @Test
    void getLastDayCurrentMonthTest() {
        LocalDate expectedDate = YearMonth.now().atEndOfMonth();
        LocalDate actualDate = DateUtils.getLastDayCurrentMonth();
        assertEquals(expectedDate, actualDate);
    }

    @Test
    void getFirstDayPreviousMonthTest() {
        LocalDate expectedDate = LocalDate.now().minusMonths(1).withDayOfMonth(1);
        LocalDate actualDate = DateUtils.getFirstDayPreviousMonth();
        assertEquals(expectedDate, actualDate);
    }

    @Test
    void getLastDayPreviousMonthTest() {
        LocalDate expectedDate = YearMonth.now().minusMonths(1).atEndOfMonth();
        LocalDate actualDate = DateUtils.getLastDayPreviousMonth();
        assertEquals(expectedDate, actualDate);
    }

    @Test
    void getFirstDayCurrentYearTest() {
        LocalDate expectedDate = LocalDate.now().with(firstDayOfYear());
        LocalDate actualDate = DateUtils.getFirstDayCurrentYear();
        assertEquals(expectedDate, actualDate);
    }

    @Test
    void getLastDayCurrentYearTest() {
        LocalDate expectedDate = LocalDate.now().with(lastDayOfYear());
        LocalDate actualDate = DateUtils.getLastDayCurrentYear();
        assertEquals(expectedDate, actualDate);
    }
}