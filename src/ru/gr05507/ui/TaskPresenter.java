package ru.gr05507.ui;

import ru.gr05507.application.TaskService;
import ru.gr05507.domain.Priority;
import ru.gr05507.domain.Task;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

// GOD OBJECT: форма, разбор дат, фильтры, выполнение задач и статистика в одном классе
public class TaskPresenter {
    private final TaskView view;
    // SINGLETON: зависимость скрыта внутри класса, в конструкторе её не видно
    private final TaskService service = TaskService.getInstance();
    private  final TaskInputParser inputParser;

    public TaskPresenter(TaskView view,TaskInputParser inputParser) {
        this.view = view;
        this.inputParser=inputParser;
    }

    public void start() {
        refresh("Все");
    }

    public void saveClicked() {
        try {
            LocalDate deadline = inputParser.parseDeadline(view.getDeadlineText());
            Priority priority = inputParser.parsePriority(view.getPriority());
            service.createTask(view.getTaskTitle(), deadline, priority, view.isArchived());
            view.clearForm();
            refresh(view.getFilter());
        } catch (IllegalArgumentException ex) {
            view.showError(ex.getMessage());
        }
    }

    public void completeSelected() {
        Task task = view.getSelectedTask();
        if (task == null) {
            view.showError("Выберите задачу в списке");
            return;
        }
        task.setDone(true);   // ANEMIC: правило выполнения живёт снаружи задачи
        refresh(view.getFilter());
    }

    public void filterChanged() {
        refresh(view.getFilter());
    }

    // O-1: новый фильтр требует новой ветки if
    private void refresh(String filterName) {
        List<Task> result = new ArrayList<>();
        for (Task t : service.getAllTasks()) {
            if (filterName.equals("Все")) {
                result.add(t);
            } else if (filterName.equals("Активные")) {
                if (!t.isDone()) result.add(t);
            } else if (filterName.equals("Просроченные")) {
                // SHOTGUN: та же проверка просрочки, что в сервисе и в окне
                if (!t.isDone() && t.getDeadline().isBefore(LocalDate.now())) result.add(t);
            } else if (filterName.equals("Высокий приоритет")) {
                // PRIMITIVE: сравнение строки с "HIGH"
                if (t.getPriority().equals(Priority.HIGH)) result.add(t);
            }
        }
        view.showTasks(result);
        view.showStatistics(service.buildStatisticsText());
    }


}