package com.yuuhikaze.ed202510.CP;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import com.yuuhikaze.ed202510.TDA.DLLDeque;
import com.yuuhikaze.ed202510.utils.ANSICodes;

class Hospital {
    private final String name;
    private final String address;
    private final int capacity;
    private DLLDeque<Patient> patients;

    public Hospital(String name, String address, int capacity) {
        this.name = name;
        this.address = address;
        this.capacity = capacity;
        this.patients = new DLLDeque<Patient>();
    }

    public void addPatient(Patient patient) {
        patients.addFirst(patient);
    }

    public void attendPatients() {
        Stream<Patient> stream = StreamSupport.stream(patients.spliterator(), false);
        Stream<Patient> poleoPatients = stream.filter(p -> p.getIssueKind().equals(IssueKind.Poleo));
        poleoPatients.forEach(patient -> {
            System.out.println(patient);
        });
        Stream<Patient> cardiovascularPressurePatients = stream.filter(p -> p.getIssueKind().equals(IssueKind.CardiovascularPressure));
        cardiovascularPressurePatients.forEach(patient -> {
            System.out.println(patient);
        });
    }
    
    public void removePatient(Patient patient) {
        if (patients.contains(patient))
            return;
        patients.removeElement(patient);
    }
}

enum Gender {
    Male,
    Female
}

enum IssueKind {
    Poleo,
    CardiovascularPressure,
    Default,
}

class Patient {
    private final String name;
    private final long ID;
    private final int age;
    private final Gender gender;
    private final IssueKind issueKind;

    public Patient(String name, long iD, int age, Gender gender) {
        this.name = name;
        ID = iD;
        this.age = age;
        this.gender = gender;
        if (this.age <= 10) {
            this.issueKind = IssueKind.Poleo;
        } else if (this.age >= 60) {
            this.issueKind = IssueKind.CardiovascularPressure;
        } else {
            this.issueKind = IssueKind.Default;
        }
    }

    public IssueKind getIssueKind() {
        return issueKind;
    }

    @Override
    public String toString() {
        return "Patient attended!\n" + ANSICodes.DIM + "\nname=" + name + ", age=" + age + ", gender=" + gender + ", issueKind=" + issueKind + ANSICodes.RESET;
    }
}

public class CP03_EJ01 {

    public static void main(String[] args) {
        // TODO
    }
}
