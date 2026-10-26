package ru.gr05507.domain;

import java.util.List;

public interface TaskRepository {
    void save(Task task);
    List<Task> findAll();
    void delete(Task task);   // I-1: метод нужен не всем клиентам
}