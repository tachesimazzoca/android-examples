package com.github.tachesimazzoca.android.example.database;

public class Task {
    public static final int LABEL_NONE = -1;

    public final Long id;
    public final Integer labelId;
    public final String content;
    public final Long dueDate;

    public Task(Long id, Integer labelId, String content, Long dueDate) {
        this.id = id;
        this.labelId = labelId;
        this.content = content;
        this.dueDate = dueDate;
    }
}
