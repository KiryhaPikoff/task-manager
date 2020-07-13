package com.mediasoft.tm.task.model.stmach;

public enum TaskEvent {
    /**
     * Взять задачу на выполнение.
     */
    TAKE_IN_PROCESS,

    /**
     * Отправить задачу на проверку.
     */
    TO_CHECK,

    /**
     * Принять выполненную задачу.
     */
    APPROVE,

    /**
     * Не принять задачу.
     */
    DISAPPROVE,
}
