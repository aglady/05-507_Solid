package ru.gr05507.domain;

public class ArchivedTask extends Task {
    // L-1: подкласс запрещает унаследованное поведение, которое базовый класс обещает
    @Override
    public void setDone(boolean done) {
        if (done) throw new UnsupportedOperationException("Архивную задачу нельзя отметить выполненной");
        super.setDone(done);
    }
}