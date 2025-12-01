package com.yuuhikaze.ed202510.CP.CP08;

import com.yuuhikaze.ed202510.enums.Gender;

class Citizen {
    private String cedula;
    private String fullName;
    private Gender gender;
    private String fullAddress;

    public Citizen(String cedula, String fullName, Gender gender, String fullAddress) {
        this.cedula = cedula;
        this.fullName = fullName;
        this.gender = gender;
        this.fullAddress = fullAddress;
    }

    public String getCedula() {
        return cedula;
    }

    public String getFullName() {
        return fullName;
    }

    public Gender getGender() {
        return gender;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    @Override
    public String toString() {
        return String.format("""
                +--------------------------------------------------+
                | CITIZEN RECORD                                   |
                +--------------------------------------------------+
                | Cedula: %-40s |
                | Name: %-42s |
                | Gender: %-40s |
                | Address: %-38s |
                +--------------------------------------------------+
                """, cedula, fullName, gender, fullAddress);
    }
}
