package com.yuuhikaze.ed202510.EI.EI05;

import com.yuuhikaze.ed202510.TDA.DoubleHashMap;

class EnrollmentController {
    private DoubleHashMap<String, Student> studentRegistry;

    public EnrollmentController() {
        this.studentRegistry = new DoubleHashMap<>();
    }

    public void registerStudent(Student student) {
        studentRegistry.put(student.getStudentId(), student);
        System.out.println("Registered: " + student.getName());
    }

    public Student findStudent(String studentId) {
        return studentRegistry.get(studentId);
    }

    public void updateGpa(String studentId, double newGpa) {
        Student student = studentRegistry.get(studentId);
        if (student != null) {
            double oldGpa = student.getGpa();
            student.setGpa(newGpa);
            System.out.println("Updated GPA for " + student.getName() + ": " +
                             String.format("%.2f", oldGpa) + " -> " + String.format("%.2f", newGpa));
        } else {
            System.out.println("Student not found: " + studentId);
        }
    }

    public void removeStudent(String studentId) {
        Student student = studentRegistry.remove(studentId);
        if (student != null) {
            System.out.println("Removed: " + student.getName());
        } else {
            System.out.println("Student not found: " + studentId);
        }
    }

    public void displayAllStudents() {
        System.out.println("\n=== Student Registry ===");
        int count = 0;
        for (Student student : studentRegistry.values()) {
            System.out.println(++count + ". " + student);
        }
        if (count == 0) {
            System.out.println("(No students registered)");
        }
    }
}
