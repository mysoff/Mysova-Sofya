package org.example.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtils {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    // Преобразование строки в LocalDate
    public static LocalDate parseDate(String dateStr) throws DateTimeParseException {
        return LocalDate.parse(dateStr, DATE_FORMATTER);
    }

    // Проверка, что дата еще не наступила
    public static boolean isDateValid(LocalDate date) {
        return !date.isBefore(LocalDate.now());
    }

    // Форматирование даты в строку
    public static String formatDate(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }

    // Валидация формата даты
    public static boolean isValidFormat(String dateStr) {
        try {
            parseDate(dateStr);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
