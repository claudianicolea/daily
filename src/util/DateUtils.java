package util;

import java.sql.Date;
import java.time.LocalDate;

public class DateUtils {
    private LocalDate date;

    public DateUtils() {
        this.date = LocalDate.now();
    }

    public DateUtils(int day, int month, int year) {
        this.date = LocalDate.of(year, month, day);
    }

    public DateUtils(LocalDate date) {
        this.date = date;
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

    public void setDay(int day) {
        this.date = LocalDate.of(date.getYear(), date.getMonthValue(), day);
    }

    public void setMonth(int month) {
        this.date = LocalDate.of(date.getYear(), month, date.getDayOfMonth());
    }

    public void setYear(int year) {
        this.date = LocalDate.of(year, date.getMonthValue(), date.getDayOfMonth());
    }

    public LocalDate toLocalDate() {
        return date;
    }

    public Date toSqlDate() {
        return Date.valueOf(date);
    }

    public void setToToday() {
        this.date = LocalDate.now();
    }

    public static DateUtils getTodayDate() {
        return new DateUtils(LocalDate.now());
    }

    public void setToTomorrow() {
        this.date = LocalDate.now().plusDays(1);
    }

    public static DateUtils getTomorrowDate() {
        return new DateUtils(LocalDate.now().plusDays(1));
    }

    @Override
    public String toString() {
        return date.toString(); // YYYY-MM-DD
    }
}
