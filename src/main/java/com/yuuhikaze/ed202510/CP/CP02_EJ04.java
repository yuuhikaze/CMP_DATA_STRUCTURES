package com.yuuhikaze.ed202510.CP;

import com.yuuhikaze.ed202510.TDA.LinkedQueue;
import com.yuuhikaze.ed202510.utils.ANSICodes;

enum ClientType {
    VIP, Peasant,
}


class Reservation {
    private long ID;
    private String name;
    private String hotel;
    private int room;
    private ClientType clientType;
    private long time;

    public Reservation(
            long ID, String name, String hotel, int room, ClientType clientType, long time) {
        this.ID = ID;
        this.name = name;
        this.hotel = hotel;
        this.room = room;
        this.clientType = clientType;
        this.time = time;
    }

    public ClientType getClientType() {
        return clientType;
    }

    @Override
    public String toString() {
        return "Reservation attended!\n" + ANSICodes.DIM + "ID=" + ID + "\nname=" + name + "\nhotel=" + hotel + "\nroom=" + room + "\nclientType="
                + clientType + "\ntime=" + time + ANSICodes.RESET;
    }
}


class ReservationSystem {
    LinkedQueue<Reservation> peasants;
    LinkedQueue<Reservation> landlords;

    public ReservationSystem() {
        this.peasants = new LinkedQueue<>();
        this.landlords = new LinkedQueue<>();
    }

    public void addReservation(Reservation reservation) {
        switch (reservation.getClientType()) {
            case ClientType.VIP:
                landlords.enqueue(reservation);
                break;
            case ClientType.Peasant:
                peasants.enqueue(reservation);
                break;
        }
    }

    public void attendReservations() {
        while (!this.landlords.isEmpty()) {
            System.out.println(this.landlords.dequeue());
        }
        while (!this.peasants.isEmpty()) {
            System.out.println(this.peasants.dequeue());
        }
    }
}


public class CP02_EJ04 {

    public static void main(String[] args) {
        System.out.println("Welcome to Hilton Hotel Chain!");
        System.out.println("Reservations here ↓");

        ReservationSystem system = new ReservationSystem();

        Reservation res1 =
                new Reservation(1L, "Alice", "Hilton Garden", 101, ClientType.VIP, 163800L);
        Reservation res2 =
                new Reservation(2L, "Bob", "Hilton Grand", 202, ClientType.Peasant, 16400L);
        Reservation res3 =
                new Reservation(3L, "Charlie", "Hilton Resort", 303, ClientType.VIP, 20000L);
        Reservation res4 =
                new Reservation(4L, "Diana", "Hilton Plaza", 404, ClientType.Peasant, 33023L);
        Reservation res5 = new Reservation(5L, "Eve", "Hilton Suites", 505, ClientType.VIP, 27200L);
        Reservation res6 =
                new Reservation(6L, "Frank", "Hilton Inn", 606, ClientType.Peasant, 30800L);

        system.addReservation(res1);
        system.addReservation(res2);
        system.addReservation(res3);
        system.addReservation(res4);
        system.addReservation(res5);
        system.addReservation(res6);

        system.attendReservations();
    }
}
