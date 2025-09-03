package com.yuuhikaze.ed202510.EI;

class CustomerEntity {
    private String name;
    private int ticketNumber;

    public CustomerEntity(String name, int ticketNumber) {
        this.name = name;
        this.ticketNumber = ticketNumber;
    }

    @Override
    public String toString() {
        return "Customer: " + name + ", Ticket: " + ticketNumber;
    }
}

