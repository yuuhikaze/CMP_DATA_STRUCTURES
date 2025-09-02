package com.yuuhikaze.ed202510.CP;

class Group {
    private Student[] students;

    public Group(Student[] students) {
        this.students = students;
    }

    public float getAverage() {
        float result = 0;
        for (Student student : students)
            result += student.getGrade();
        return result / students.length;
    }

    public String getBestStudent() {
        Student best_student = this.students[0];
        float best_grade = best_student.getGrade();
        for (int i = 1; i < this.students.length; i++) {
            if (this.students[i].getGrade() > best_grade) {
                best_grade = this.students[i].getGrade();
                best_student = this.students[i];
            }
        }
        return best_student.getName();
    }
}

class Student {
    private String name;

    @SuppressWarnings("unused")
    private int age;

    private float grade;

    public Student(String name, int age, float grade) {
        this.name = name;
        this.age = age;
        this.grade = grade;
    }

    public float getGrade() {
        return grade;
    }

    public String getName() {
        return name;
    }
}

public class CP01_EJ01 {

    public static void main(String[] args) {
        Student[] students = {
                new Student("Alice", 20, 85.5f),
                new Student("Bob", 22, 90.0f),
                new Student("Charlie", 21, 78.0f) };
        Group group = new Group(students);
        System.out.println("Average grade: " + group.getAverage()); // O(n)
        System.out.println("Best student: " + group.getBestStudent()); // O(n)
    }
}
