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
        boolean isTop = true;
        for (TaskEntity task : taskStack) {
            if (isTop) {
                result.append("[TOP/LAST]  ").append(task).append("\n");
                isTop = false;
            } else {
                result.append("            ").append(task).append("\n");
            }
        }
        if (isTop) {
            result.append("(Stack is empty)\n");
        }
        return result.toString();
    }
}
