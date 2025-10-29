package com.yuuhikaze.ed202510.EI.EI05;

import com.yuuhikaze.ed202510.TDA.ProbeHashMap;

class DNSCacheController {
    private ProbeHashMap<String, DNSRecord> cache;

    public DNSCacheController() {
        this.cache = new ProbeHashMap<>();
    }

    public void addRecord(DNSRecord record) {
        cache.put(record.getDomain(), record);
    }

    public String resolve(String domain) {
        DNSRecord record = cache.get(domain);
        if (record == null) {
            return null;
        }

        if (record.isExpired()) {
            System.out.println("Cache entry expired for: " + domain);
            cache.remove(domain);
            return null;
        }

        System.out.println("Cache hit: " + domain + " -> " + record.getIpAddress());
        return record.getIpAddress();
    }

    public void refreshRecord(String domain) {
        DNSRecord record = cache.get(domain);
        if (record != null) {
            record.refresh();
            System.out.println("Refreshed cache entry for: " + domain);
        } else {
            System.out.println("No cache entry found for: " + domain);
        }
    }

    public void displayCache() {
        System.out.println("\n=== DNS Cache Contents ===");
        int count = 0;
        for (DNSRecord record : cache.values()) {
            System.out.println(++count + ". " + record);
        }
        if (count == 0) {
            System.out.println("(Cache is empty)");
        }
    }
}
