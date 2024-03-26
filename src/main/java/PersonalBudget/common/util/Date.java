package PersonalBudget.common.util;

import java.time.LocalDate;

public class Date {

    private static final LocalDate currentDate = LocalDate.now();

    public static String getCurrentMonth() {
        String month = String.valueOf(currentDate.getMonthValue());
        if (month.length() == 1) {
            month = "0" + month;
        }
        return month;
    }

    public static String getCurrentYear() {
        return String.valueOf(currentDate.getYear());
    }

    public static String getLastDayCurrentMonth() {
        return String.valueOf(currentDate.lengthOfMonth());
    }
}
