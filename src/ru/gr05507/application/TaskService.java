package ru.gr05507.application;

import ru.gr05507.domain.ArchivedTask;
import ru.gr05507.domain.Priority;
import ru.gr05507.domain.Task;
import ru.gr05507.domain.TaskRepository;
import ru.gr05507.infrastructure.InMemoryTaskRepository;

import java.time.LocalDate;
import java.util.List;

public class TaskService {
    // SINGLETON: глобальный единственный экземпляр
    private static final TaskService INSTANCE = new TaskService();

    // D-1: сервис сам создаёт конкретное хранилище
    private final TaskRepository repository = new InMemoryTaskRepository();

    private TaskService() { }

    public static TaskService getInstance() { return INSTANCE; }

    // ANEMIC: все проверки здесь, а сама задача ничего не проверяет
    public void createTask(String title, LocalDate deadline, Priority priority, boolean archived) {
        if (title == null || title.isBlank())
            throw new IllegalArgumentException("Название задачи не может быть пустым");
        if (deadline == null)
            throw new IllegalArgumentException("Дата обязательна");
        Task task = archived ? new ArchivedTask() : new Task();
        task.setTitle(title.trim());
        task.setDeadline(deadline);
        task.setPriority(priority);
        repository.save(task);
    }

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    // S-1: сервис формирует готовый текст для экрана
    public String buildStatisticsText() {
        List<Task> tasks = repository.findAll();
        int done = 0;
        int overdue = 0;
        // SHOTGUN: правило «просрочено» продублировано в трёх местах (сервис, презентер, окно)
        for (Task t : tasks) {
            if (t.isDone()) done++;
            if (!t.isDone() && t.getDeadline().isBefore(LocalDate.now())) overdue++;
        }
        return "Всего: " + tasks.size() + ", выполнено: " + done + ", просрочено: " + overdue;
    }
}