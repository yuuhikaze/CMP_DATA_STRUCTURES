package com.yuuhikaze.ed202510.E.E03._02;

class MountainPoint {
    private String name; // can be control point (prefix 'C') or campament (prefix 'PC')
    private String averageTemperature; // here the user can specify ºF or ºC, that's why String (in reality refactoring this would be pain)
    private int altitude; // it is assumed we work in meters

    public MountainPoint(String name, String averageTemperature, int altitude) {
        this.name = name;
        this.averageTemperature = averageTemperature;
        this.altitude = altitude;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return averageTemperature;
    }

    public int getCapacity() {
        return altitude;
    }

    @Override
    public String toString() {
        return name + " (" + averageTemperature + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MountainPoint room = (MountainPoint) obj;
        return name.equals(room.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
