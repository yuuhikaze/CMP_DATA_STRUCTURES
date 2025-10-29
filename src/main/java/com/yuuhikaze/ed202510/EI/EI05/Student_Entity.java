package com.yuuhikaze.ed202510.EI.EI05;

class Student {
    private String studentId;
    private String name;
    private String major;
    private double gpa;

    public Student(String studentId, String name, String major, double gpa) {
        this.studentId = studentId;
        this.name = name;
        this.major = major;
        this.gpa = gpa;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public String getMajor() {
        return major;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    @Override
    public String toString() {
        return name + " (" + studentId + ") - " + major + " [GPA: " + String.format("%.2f", gpa) + "]";
    }
}
