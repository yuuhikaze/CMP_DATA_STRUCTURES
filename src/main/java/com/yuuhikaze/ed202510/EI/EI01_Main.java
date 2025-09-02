package com.yuuhikaze.ed202510.EI;

public class EI01_Main {

    public static void main(String[] args) {
        Lion lion = new Lion();
        Zebra zebra = new Zebra();
        Frog frog = new Frog();
        Horse horse = new Horse();
        ZooController<Object> zoo = new ZooController<Object>();
        zoo.addAnimalFirst(lion);
        zoo.addAnimalFirst(zebra);
        zoo.addAnimalLast(frog);
        zoo.addAnimalLast(horse);
        System.out.print(zoo);
    }
}
