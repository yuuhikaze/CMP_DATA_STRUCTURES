package com.yuuhikaze.ed202510.EI.EI05;

public class Enrollment_Main {
    public static void main(String[] args) {
        System.out.println("=== STUDENT ENROLLMENT SYSTEM (DoubleHashMap - Double Hashing) ===\n");

        EnrollmentController enrollment = new EnrollmentController();

        enrollment.registerStudent(new Student("S001", "Alice Johnson", "Computer Science", 3.85));
        enrollment.registerStudent(new Student("S002", "Bob Martinez", "Mathematics", 3.92));
        enrollment.registerStudent(new Student("S003", "Carol White", "Physics", 3.67));
        enrollment.registerStudent(new Student("S004", "David Kim", "Computer Science", 3.78));
        enrollment.registerStudent(new Student("S005", "Eve Chen", "Engineering", 3.95));

        enrollment.displayAllStudents();

        System.out.println("\n--- Finding specific students ---");
        Student student = enrollment.findStudent("S002");
        if (student != null) {
            System.out.println("Found: " + student);
        }

        System.out.println("\n--- Updating GPAs ---");
        enrollment.updateGpa("S001", 3.90);
        enrollment.updateGpa("S003", 3.75);

        enrollment.displayAllStudents();

        System.out.println("\n--- Removing a student ---");
        enrollment.removeStudent("S004");

        enrollment.displayAllStudents();
    }
}
