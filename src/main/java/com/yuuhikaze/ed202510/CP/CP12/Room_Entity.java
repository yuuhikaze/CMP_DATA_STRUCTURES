package com.yuuhikaze.ed202510.CP.CP12;

class Room {
    private String name;
    private String department;
    private int capacity;

    public Room(String name, String department, int capacity) {
        this.name = name;
        this.department = department;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public String toString() {
        return name + " (" + department + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Room room = (Room) obj;
        return name.equals(room.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
