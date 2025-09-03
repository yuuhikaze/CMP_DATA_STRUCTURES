package com.yuuhikaze.ed202510.EI;

import com.yuuhikaze.ed202510.TDA.LinkedQueue;

class CustomerController {
    private LinkedQueue<CustomerEntity> customerQueue;

    public CustomerController() {
        this.customerQueue = new LinkedQueue<>();
    }

    public void addCustomer(CustomerEntity customer) {
        customerQueue.enqueue(customer);
    }

    public CustomerEntity serveCustomer() {
        return customerQueue.dequeue();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Customers in queue:\n");
        LinkedQueue<CustomerEntity> tempQueue = new LinkedQueue<>();

        while (!customerQueue.isEmpty()) {
            CustomerEntity customer = customerQueue.dequeue();
            result.append(customer).append("\n");
            tempQueue.enqueue(customer);
        }

        while (!tempQueue.isEmpty()) {
            customerQueue.enqueue(tempQueue.dequeue());
        }

        return result.toString();
    }
}

