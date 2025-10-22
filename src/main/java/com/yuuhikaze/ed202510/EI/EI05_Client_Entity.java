package com.yuuhikaze.ed202510.EI;

/**
 * Entity class representing a client in the Supermaxi supermarket
 */
public class EI05_Client_Entity {
    private String name;
    private int age;
    private String id;

    public EI05_Client_Entity(String id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Client{id='" + id + "', name='" + name + "', age=" + age + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        EI05_Client_Entity client = (EI05_Client_Entity) obj;
        return id.equals(client.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
