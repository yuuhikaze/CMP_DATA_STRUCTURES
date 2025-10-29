package com.yuuhikaze.ed202510.EI.EI05;

public class DNSCache_Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== DNS CACHE SYSTEM (ProbeHashMap - Linear Probing) ===\n");

        DNSCacheController dnsCache = new DNSCacheController();

        dnsCache.addRecord(new DNSRecord("google.com", "142.250.185.46", 300));
        dnsCache.addRecord(new DNSRecord("github.com", "140.82.121.4", 300));
        dnsCache.addRecord(new DNSRecord("stackoverflow.com", "151.101.1.69", 300));
        dnsCache.addRecord(new DNSRecord("localhost", "127.0.0.1", 2));

        dnsCache.displayCache();

        System.out.println("\n--- Resolving domains ---");
        dnsCache.resolve("google.com");
        dnsCache.resolve("github.com");
        dnsCache.resolve("unknown.com");

        System.out.println("\n--- Waiting for localhost entry to expire (2.5 seconds) ---");
        Thread.sleep(2500);

        dnsCache.displayCache();

        System.out.println("\n--- Attempting to resolve expired entry ---");
        dnsCache.resolve("localhost");

        dnsCache.displayCache();

        System.out.println("\n--- Refreshing cache entry ---");
        dnsCache.refreshRecord("google.com");
    }
}
