package ru.gr05507.ui;

import ru.gr05507.domain.Priority;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class TaskInputParser {

    public LocalDate parseDeadline(String text) {
        if (text == null || text.isBlank())
            throw new IllegalArgumentException("Дата выполнения не может быть пустой");
        try {
            return LocalDate.parse(text.trim());
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Дата должна быть в формате yyyy-MM-dd");
        }
    }

    public Priority parsePriority(String priority) {
        if (priority == null || priority.isBlank())
            throw new IllegalArgumentException("Приоритет не может быть пустым");
        try {
            return Priority.valueOf(priority.trim());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Неверный вариант");
        }
    }
}
