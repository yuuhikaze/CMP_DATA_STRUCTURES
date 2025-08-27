package com.yuuhikaze.ed202510.EI;

import com.yuuhikaze.ed202510.TDA.SinglyLinkedList;

class ZooController<E> {
    SinglyLinkedList SSL;

    public ZooController() {
        this.SSL = new SinglyLinkedList<>();
    }

    public SinglyLinkedList getSSL() {
        return SSL;
    }

    public void addAnimalFirst(E animal) {
        SSL.addFirst(animal);
    }
    
    public void addAnimalLast(E animal) {
        SSL.addLast(animal);
    }
    
    /**
     * List animals stored inside SSL collection
     * 
     * Quite the inefficient implementation must I say
     */
    @Override
    public String toString() {
        var SSLClone = this.SSL;
        String result = "Welcome to anon's zoo, we have: \n";
        while (!SSLClone.isEmpty()) {
            result += SSLClone.removeFirst() + "\n";
        }
        return result;
    }
}
