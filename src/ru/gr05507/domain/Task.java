package ru.gr05507.domain;

import java.time.LocalDate;

// ANEMIC: класс только хранит поля, правил внутри нет
public class Task {
    private String title;
    private LocalDate deadline;
    private Priority priority;   // PRIMITIVE: приоритет — строка, опечатка не будет замечена
    private boolean done;

    public String getTitle()                 { return title; }
    public void setTitle(String title)       { this.title = title; }
    public LocalDate getDeadline()           { return deadline; }
    public void setDeadline(LocalDate d)     { this.deadline = d; }
    public Priority getPriority()              { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }
    public boolean isDone()                  { return done; }
    public void setDone(boolean done)        { this.done = done; }
}