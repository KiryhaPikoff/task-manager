package com.mediasoft.tm.task.model.stmach;

public enum TaskState {
    /**
     * Задача создана, но ни кем не взята.
     */
    NEW,

    /**
     * Задача взята и находится в процессе выполнения.
     */
    IN_PROCESS,

    /**
     * Задача ожидает проверки.
     */
    IN_CHECK,

    /**
     * Задача выполнена.
     */
    FINISHED
}
