package ru.gr05507.ui;

import ru.gr05507.domain.Task;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class TaskWindow extends JFrame implements TaskView {

    private final TaskPresenter presenter;

    private final JTextField titleField = new JTextField(20);
    private final JTextField deadlineField = new JTextField(10);
    private final JComboBox<String> priorityBox = new JComboBox<>(new String[]{"LOW", "MEDIUM", "HIGH"});   // PRIMITIVE
    private final JCheckBox archivedBox = new JCheckBox("В архив");
    private final JComboBox<String> filterBox = new JComboBox<>(
            new String[]{"Все", "Активные", "Просроченные", "Высокий приоритет"});
    private final JButton saveButton = new JButton("Добавить");
    private final JButton completeButton = new JButton("Выполнить");
    private final DefaultListModel<Task> listModel = new DefaultListModel<>();
    private final JList<Task> taskList = new JList<>(listModel);
    private final JLabel statisticsLabel = new JLabel(" ");

    public TaskWindow() {
        this.presenter = new TaskPresenter(this);

        setTitle("Список задач");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        buildLayout();
        setSize(520, 440);
        setLocationRelativeTo(null);
        presenter.start();
    }

    private void buildLayout() {
        JPanel form = new JPanel(new GridLayout(4, 2, 5, 5));
        form.add(new JLabel("Название:"));
        form.add(titleField);
        form.add(new JLabel("Дедлайн yyyy-MM-dd:"));
        form.add(deadlineField);
        form.add(new JLabel("Приоритет:"));
        form.add(priorityBox);
        form.add(new JLabel("Архивная:"));
        form.add(archivedBox);

        JPanel buttons = new JPanel(new FlowLayout());
        buttons.add(saveButton);
        buttons.add(completeButton);

        JPanel top = new JPanel(new BorderLayout(5, 5));
        top.add(form, BorderLayout.CENTER);
        top.add(buttons, BorderLayout.SOUTH);

        JPanel bottom = new JPanel(new BorderLayout(5, 5));
        bottom.add(filterBox, BorderLayout.NORTH);
        bottom.add(new JScrollPane(taskList), BorderLayout.CENTER);
        bottom.add(statisticsLabel, BorderLayout.SOUTH);

        add(top, BorderLayout.NORTH);
        add(bottom, BorderLayout.CENTER);

        taskList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean selected,
                                                          boolean hasFocus) {
                super.getListCellRendererComponent(list, value, index, selected, hasFocus);
                Task task = (Task) value;
                String text = (task.isDone() ? "[x] " : "[ ] ") + task.getTitle()
                        + " | " + task.getDeadline() + " | " + task.getPriority();
                // SHOTGUN: третья копия правила «просрочено»
                if (!task.isDone() && task.getDeadline().isBefore(LocalDate.now())) {
                    text += " (просрочено)";
                }
                setText(text);
                setForeground(colorFor(task.getPriority()));
                return this;
            }
        });

        saveButton.addActionListener(e -> presenter.saveClicked());
        completeButton.addActionListener(e -> presenter.completeSelected());
        filterBox.addActionListener(e -> presenter.filterChanged());
    }

    private Color colorFor(String priority) {
        if (priority.equals("HIGH")) return Color.RED;
        if (priority.equals("MEDIUM")) return Color.ORANGE;
        return Color.GRAY;
    }

    @Override public String getTaskTitle()   { return titleField.getText(); }
    @Override public String getDeadlineText(){ return deadlineField.getText(); }
    @Override public String getPriority()    { return (String) priorityBox.getSelectedItem(); }
    @Override public boolean isArchived()    { return archivedBox.isSelected(); }
    @Override public String getFilter()      { return (String) filterBox.getSelectedItem(); }
    @Override public Task getSelectedTask()  { return taskList.getSelectedValue(); }

    @Override
    public void clearForm() {
        titleField.setText("");
        deadlineField.setText("");
        archivedBox.setSelected(false);
    }

    @Override
    public void showTasks(List<Task> tasks) {
        listModel.clear();
        for (Task t : tasks) listModel.addElement(t);
    }

    @Override
    public void showStatistics(String text) {
        statisticsLabel.setText(text);
    }

    @Override
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}