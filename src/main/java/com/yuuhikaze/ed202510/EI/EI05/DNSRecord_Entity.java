package com.yuuhikaze.ed202510.EI.EI05;

class DNSRecord {
    private String domain;
    private String ipAddress;
    private long timestamp;
    private int ttl;

    public DNSRecord(String domain, String ipAddress, int ttl) {
        this.domain = domain;
        this.ipAddress = ipAddress;
        this.ttl = ttl;
        this.timestamp = System.currentTimeMillis();
    }

    public String getDomain() {
        return domain;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public boolean isExpired() {
        long currentTime = System.currentTimeMillis();
        return (currentTime - timestamp) > (ttl * 1000L);
    }

    public void refresh() {
        this.timestamp = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        String status = isExpired() ? "EXPIRED" : "VALID";
        return domain + " -> " + ipAddress + " (TTL: " + ttl + "s) [" + status + "]";
    }
}
