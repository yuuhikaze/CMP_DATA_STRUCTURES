package com.yuuhikaze.ed202510.EI;

public class EI02_Task_Main {

    public static void main(String[] args) {
        TaskEntity task1 = new TaskEntity("Complete project proposal");
        TaskEntity task2 = new TaskEntity("Review budget reports");
        TaskEntity task3 = new TaskEntity("Prepare for team meeting");
        TaskEntity task4 = new TaskEntity("Submit quarterly performance review");

        TaskController taskStack = new TaskController();
        taskStack.addTask(task1);
        taskStack.addTask(task2);
        taskStack.addTask(task3);
        taskStack.addTask(task4);

        System.out.println(taskStack);

        taskStack.removeTask();
        System.out.println("After removing one task:\n" + taskStack);
    }
}

