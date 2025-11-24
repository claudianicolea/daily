package util;

import java.time.LocalDate;

public class Date {
    private int day, month, year;

    public Date() {
    }

    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public void setDay(int day) {
        this.day = day;
    }
    public int getDay() {
        return day;
    }
    public void setMonth(int month) {
        this.month = month;
    }
    public int getMonth() {
        return month;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public int getYear() {
        return year;
    }

    public void setToToday() {
        LocalDate today = LocalDate.now();
        day = today.getDayOfMonth();
        month = today.getMonthValue();
        year = today.getYear();
    }

    public static Date getTodayDate() {
        LocalDate today = LocalDate.now();
        return new Date(today.getDayOfMonth(), today.getMonthValue(), today.getYear());
    }

    public void setToTomorrow() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        day = tomorrow.getDayOfMonth() ;
        month = tomorrow.getMonthValue();
        year = tomorrow.getYear();
    }

    public static Date getTomorrowDate() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        return new Date(tomorrow.getDayOfMonth(), tomorrow.getMonthValue(), tomorrow.getYear());
    }
}