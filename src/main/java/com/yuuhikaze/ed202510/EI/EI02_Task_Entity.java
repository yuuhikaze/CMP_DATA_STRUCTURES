package com.yuuhikaze.ed202510.EI;

class TaskEntity {
    private String description;

    public TaskEntity(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Task: " + description;
    }
}

