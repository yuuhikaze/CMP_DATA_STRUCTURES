package com.yuuhikaze.ed202510.EI;

import com.yuuhikaze.ed202510.TDA.SLLQueue;

class CustomerController {
    private SLLQueue<CustomerEntity> customerQueue;

    public CustomerController() {
        this.customerQueue = new SLLQueue<>();
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
        SLLQueue<CustomerEntity> tempQueue = new SLLQueue<>();

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

