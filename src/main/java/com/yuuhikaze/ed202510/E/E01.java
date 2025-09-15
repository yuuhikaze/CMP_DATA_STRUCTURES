package com.yuuhikaze.ed202510.E;

import com.yuuhikaze.ed202510.TDA.SLLQueue;
import com.yuuhikaze.ed202510.utils.ANSICodes;
import java.util.Random;

class CallCenter {
    private Operator[] operatorsPool;
    private SLLQueue<Client> clientCalls;

    public CallCenter(Operator[] operatorsPool) {
        this.operatorsPool = operatorsPool;
        this.clientCalls = new SLLQueue<Client>();
    }

    public void addClient(Client client) {
        this.clientCalls.enqueue(client);
    }

    public void attendClients() {
        while (this.clientCalls.size() != 0) {
            Operator currentOperator = getRandomOperator();
            currentOperator.attendClient(this.clientCalls.dequeue());
        }
    }
    
    public void printReport() {
        for (Operator operator : operatorsPool) {
            operator.printAttendedClients();
        }
    }

    private Operator getRandomOperator() {
        Random random = new Random();
        int randomOperatorIndex = Math.abs(random.nextInt()) % this.operatorsPool.length;
        if (!this.operatorsPool[randomOperatorIndex].isAvailable()) {
            /* System.out.println(
                    "Operator [↓] is not available, forwarding call to another member of the call"
                            + " center");
            System.out.println(this.operatorsPool[randomOperatorIndex]); */
            // too much spam
            return getRandomOperator();
        }
        return this.operatorsPool[randomOperatorIndex];
    }
}


enum Reason {
    TechnicalSupport, BillPayment, ServicePlanChange,
}


class Operator {
    private String name;
    private long ID;
    private boolean available; // can the operator attend requests
    private SLLQueue<Client> clientsAttended;

    public Operator(String name, long iD, boolean available) {
        this.name = name;
        ID = iD;
        this.available = available;
        this.clientsAttended = new SLLQueue<Client>();
    }

    public boolean isAvailable() {
        return available;
    }

    public void attendClient(Client client) {
        clientsAttended.enqueue(client);
    }

    public void printAttendedClients() {
        if (this.clientsAttended.size() == 0)
            return;
        System.out.println(
                "Hi! I'm " + ANSICodes.BOLD + this.name + ANSICodes.RESET + " it was a pleasure attending the following clients");
        for (Client client : clientsAttended) {
            System.out.println(client);
        }
    }

    @Override
    public String toString() {
        return ANSICodes.DIM + "name=" + name + ", ID=" + ID + ANSICodes.RESET;
    }
}


class Client {
    private String name;
    private Reason reason;
    private String phoneNumber;

    public Client(String name, Reason reason, String phoneNumber) {
        this.name = name;
        this.reason = reason;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return ANSICodes.DIM + "name=" + name + ", reason=" + reason + ", phoneNumber=" + phoneNumber + ANSICodes.RESET;
    }
}


public class E01 {

    public static void main(String[] args) {
        // Generate a pool of operators
        Operator[] operatorsPool = new Operator[] {
            new Operator("Oscar", 128091820, true),
            new Operator("Alice", 724091123, false),
            new Operator("Charlie", 538291810, true),
        };
        // Instantiate call center
        CallCenter callCenter = new CallCenter(operatorsPool);
        // Submit requests
        callCenter.addClient(new Client("Bob", Reason.ServicePlanChange, "+593 99 819 2189"));
        callCenter.addClient(new Client("John", Reason.TechnicalSupport, "+593 99 859 3181"));
        callCenter.addClient(new Client("Diana", Reason.ServicePlanChange, "+593 99 219 4209"));
        callCenter.addClient(new Client("Frank", Reason.BillPayment, "+593 99 536 0909"));
        callCenter.addClient(new Client("Max", Reason.BillPayment, "+593 99 596 2581"));
        callCenter.addClient(new Client("Bruno", Reason.TechnicalSupport, "+593 99 214 7863"));
        // Attend requests
        callCenter.attendClients(); // O(n)
        callCenter.printReport();
    }
}
