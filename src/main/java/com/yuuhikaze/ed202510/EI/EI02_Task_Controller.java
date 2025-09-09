package com.yuuhikaze.ed202510.EI;

import com.yuuhikaze.ed202510.TDA.SLLStack;

class TaskController {
    private SLLStack<TaskEntity> taskStack;

    public TaskController() {
        this.taskStack = new SLLStack<>();
    }

    public void addTask(TaskEntity task) {
        taskStack.push(task);
    }

    public TaskEntity removeTask() {
        return taskStack.pop();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Tasks in stack:\n");
        SLLStack<TaskEntity> tempStack = new SLLStack<>();

        while (!taskStack.isEmpty()) {
            TaskEntity task = taskStack.pop();
            result.append(task).append("\n");
            tempStack.push(task);
        }

        while (!tempStack.isEmpty()) {
            taskStack.push(tempStack.pop());
        }

        return result.toString();
    }
}

