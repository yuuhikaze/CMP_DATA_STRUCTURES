package com.yuuhikaze.ed202510.EI;

public class EI02_Customer_Main {

    public static void main(String[] args) {
        CustomerEntity customer1 = new CustomerEntity("Alice", 101);
        CustomerEntity customer2 = new CustomerEntity("Bob", 102);
        CustomerEntity customer3 = new CustomerEntity("Charlie", 103);
        CustomerEntity customer4 = new CustomerEntity("Diana", 104);

        CustomerController customerQueue = new CustomerController();
        customerQueue.addCustomer(customer1);
        customerQueue.addCustomer(customer2);
        customerQueue.addCustomer(customer3);
        customerQueue.addCustomer(customer4);

        System.out.println(customerQueue);

        customerQueue.serveCustomer();
        System.out.println("After serving one customer:\n" + customerQueue);
    }
}

