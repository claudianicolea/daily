package util;

import java.sql.Date;
import java.time.LocalDate;

public class DateUtils {
    private LocalDate date;

    public DateUtils(int day, int month, int year) {
        this.date = LocalDate.of(year, month, day);
    }

    public DateUtils(Date sqlDate) {
        this.date = sqlDate.toLocalDate();
    }

    public int getDay() {
        return date.getDayOfMonth();
    }

    public int getMonth() {
        return date.getMonthValue();
    }

    public int getYear() {
        return date.getYear();
    }

    public Date toSqlDate() {
        return Date.valueOf(date);
    }

    public LocalDate toLocalDate() {
        return date;
    }

    @Override
    public String toString() {
        return date.toString(); // YYYY-MM-DD
    }
}
