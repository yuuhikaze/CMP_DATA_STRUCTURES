package com.yuuhikaze.ed202510.EI;

import com.yuuhikaze.ed202510.TDA.SinglyLinkedList;

class ZooController<E> {
    SinglyLinkedList<Object> SSL;

    public ZooController() {
        this.SSL = new SinglyLinkedList<>();
    }

    public SinglyLinkedList<Object> getSSL() {
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
     */
    @Override
    public String toString() {
        String result = "Welcome to anon's zoo, we have: \n";
        for (var elem : SSL) {
            result += elem;
        }
        return result;
    }
}
