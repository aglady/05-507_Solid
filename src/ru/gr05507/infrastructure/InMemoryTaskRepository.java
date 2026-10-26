package ru.gr05507.infrastructure;

import ru.gr05507.domain.Task;
import ru.gr05507.domain.TaskRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryTaskRepository implements TaskRepository {
    private final List<Task> tasks = new ArrayList<>();

    @Override
    public void save(Task task) { tasks.add(task); }

    @Override
    public List<Task> findAll() { return List.copyOf(tasks); }

    @Override
    public void delete(Task task) {   // I-1: заглушка, удаления в программе нет
        throw new UnsupportedOperationException("Удаление пока не реализовано");
    }
}