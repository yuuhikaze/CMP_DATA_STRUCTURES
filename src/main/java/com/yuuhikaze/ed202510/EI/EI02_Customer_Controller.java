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
        boolean isFirst = true;
        for (CustomerEntity customer : customerQueue) {
            if (isFirst) {
                result.append("[FRONT/FIRST] ").append(customer).append("\n");
                isFirst = false;
            } else {
                result.append("              ").append(customer).append("\n");
            }
        }
        if (isFirst) {
            result.append("(Queue is empty)\n");
        }
        return result.toString();
    }
}
