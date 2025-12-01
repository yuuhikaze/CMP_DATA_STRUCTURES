package com.yuuhikaze.ed202510.E.E03._01;

enum Gender {
    F, M, ND
}


class ClientEntity {
    private String ID; // cédula
    private String name;
    private Gender gender;
    private String address;

    public String getID() {
        return ID;
    }

    public void setID(String iD) {
        ID = iD;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "ClientEntity [ID=" + ID + ", name=" + name + ", gender=" + gender + ", address=" + address + "]";
    }
}
