package com.yuuhikaze.ed202510.EI.EI06;

class Server {
    private String id;
    private String location;
    private String type;

    public Server(String id, String location, String type) {
        this.id = id;
        this.location = location;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return id + " (" + type + " in " + location + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Server server = (Server) obj;
        return id.equals(server.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
