package com.yuuhikaze.ed202510.E;

import com.yuuhikaze.ed202510.TDA.SLLQueue;
import com.yuuhikaze.ed202510.utils.ANSICodes;
import java.util.Random;

class CallCenter {
    private Operator[] operatorsPool;
    private SLLQueue<Client> clientCalls;

    public CallCenter(Operator[] operatorsPool) {
        this.operatorsPool = operatorsPool;
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

    private Operator getRandomOperator() {
        Random random = new Random();
        int randomOperatorIndex = random.nextInt() * (this.operatorsPool.length - 1) + 1;
        if (!this.operatorsPool[randomOperatorIndex].isAvailable()) {
            System.out.println(
                    "Operator [↓] is not available, forwarding call to another member of the call"
                            + " center");
            System.out.println(this.operatorsPool[randomOperatorIndex]);
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
        System.out.println(client);
    }
    
    public void printAttendedClients() {
        System.out.println("Hi! I'm " + ANSICodes.BOLD + this.name + ANSICodes.RESET + " it was a pleasure attending the following clients");
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

    @Override
    public String toString() {
        return ANSICodes.DIM + "name=" + name + ", reason=" + reason + ", phoneNumber=" + phoneNumber + ANSICodes.RESET;
    }
}


public class E01 {

    public static void main(String[] args) {}
}
