package com.yuuhikaze.ed202510.CP;

import java.util.HashMap;

enum Gender {
    Male, Female
}


class Citizen {
    private String ID;
    private String name;
    private Gender gender;
    private String address;

    public Citizen(String iD, String name, Gender gender, String address) {
        ID = iD;
        this.name = name;
        this.gender = gender;
        this.address = address;
    }

    public String getID() {
        return ID;
    }

    @Override
    public String toString() {
        return "Citizen [ID=" + ID + ", name=" + name + ", gender=" + gender + ", address=" + address + "]";
    }
}


class CitizenController {
    HashMap<String, Citizen> citizens;

    public CitizenController() {
        citizens = new HashMap<>();
    }

    public void addCitizen(Citizen citizen) {
        citizens.put(computeKey(citizen), citizen);
    }

    public void printCitizenSheet(Citizen citizen) {
        System.out.println(citizens.get(computeKey(citizen)));
    }

    private String computeKey(Citizen citizen) {
        int key_len = citizen.getID().length();
        return citizen.getID().substring(key_len - 3, key_len);
    }
}


public class CP06_EJ02 {
    public static void main(String[] args) {
        CitizenController cc = new CitizenController();
        cc.addCitizen(new Citizen("1327182359", "John", Gender.Male, ""));
        cc.addCitizen(new Citizen("1327182352", "Alice", Gender.Female, ""));
        cc.addCitizen(new Citizen("1327182353", "Bob", Gender.Male, ""));
        cc.addCitizen(new Citizen("1327182356", "Jack", Gender.Male, ""));
    }
}
